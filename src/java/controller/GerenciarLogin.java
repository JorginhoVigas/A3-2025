package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Menu;
import modelo.Usuario;
import modelo.UsuarioDAO;

public class GerenciarLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Logout
        request.getSession().removeAttribute("ulogado");
        response.sendRedirect("form_login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        ArrayList<String> erros = new ArrayList<>();
        if (login == null || login.trim().isEmpty()) {
            erros.add("Preencha o login");
        }
        if (senha == null || senha.trim().isEmpty()) {
            erros.add("Preencha a senha");
        }

        if (erros.size() > 0) {
            String campos = "";
            for (String erro : erros) {
                campos += "\\n - " + erro;
            }
            exibirMensagem(response, "Preencha o(s) campo(s)" + campos);
        } else {
            try {
                UsuarioDAO uDAO = new UsuarioDAO();

                // --- MUDANÇA PRINCIPAL AQUI (MD5) ---
                // Passamos login E senha. O DAO criptografa a senha e verifica no banco.
                Usuario u = uDAO.getRecuperarUsuario(login, senha);

                // Agora só verificamos se o objeto retornou válido (não precisa comparar senha de novo)
                if (u != null && u.getIdUsuario() > 0) {
                    HttpSession sessao = request.getSession();
                    sessao.setAttribute("ulogado", u);
                    response.sendRedirect("index.jsp");
                } else {
                    exibirMensagem(response, "Login ou senha inválidos!");
                }
            } catch (Exception e) {
                exibirMensagem(response, "Erro ao acessar o banco: " + e.getMessage());
            }
        }
    }

    private static void exibirMensagem(HttpServletResponse response, String mensagem) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('" + mensagem + "');");
            out.println("history.back();");
            out.println("</script>");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Usuario verificarAcesso(HttpServletRequest request, HttpServletResponse response) {
        Usuario u = null;
        try {
            HttpSession sessao = request.getSession();

            if (sessao.getAttribute("ulogado") == null) {
                response.sendRedirect("form_login.jsp");
                return null;
            }

            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            if (queryString != null) {
                uri += "?" + queryString;
            }

            u = (Usuario) sessao.getAttribute("ulogado");

            if (u == null) {
                sessao.setAttribute("mensagem", "Você não está autenticado");
                response.sendRedirect("form_login.jsp");
                return null;
            }

            boolean possuiAcesso = false;

            // 1. Verifica nos menus do banco de dados
            if (u.getPerfil() != null && u.getPerfil().getMenus() != null) {
                for (Menu m : u.getPerfil().getMenus().getMenusVinculados()) {
                    if (uri.contains(m.getLink())) {
                        possuiAcesso = true;
                        break;
                    }
                }
            }

            // 2. EXCEÇÕES: Libera rotas internas que não são menus
            if (!possuiAcesso) {
                if (uri.contains("gerenciar_venda_produto.do")
                        || uri.contains("listar_venda_produto.jsp")
                        || uri.contains("form_venda_produto.jsp")
                        || uri.contains("gerenciar_venda.do")
                        || uri.contains("form_venda.jsp")
                        // --- ADICIONEI AS EXCEÇÕES DE RELATÓRIO AQUI ---
                        // Sem isso, o sistema bloquearia a geração do PDF
                        || uri.contains("gerenciar_relatorio.do")
                        || uri.contains("listar_relatorio_") // libera os JSPs de resultado
                        || uri.contains("imprimir_venda.jsp") // libera o cupom fiscal
                        ) {

                    possuiAcesso = true;
                }
            }

            if (!possuiAcesso) {
                exibirMensagem(response, "Acesso Negado: Você não tem permissão para esta ação.");
                return null;
            }

        } catch (Exception e) {
            try {
                exibirMensagem(response, "Erro ao verificar acesso: " + e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return u;
    }
}
