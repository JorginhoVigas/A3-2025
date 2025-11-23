package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Menu;
import modelo.MenuDAO;
import modelo.Perfil;
import modelo.UsuarioDAO;
import modelo.Usuario;

public class GerenciarUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idUsuarioStr = request.getParameter("idUsuario");
        Usuario u = new Usuario();
        try {
            UsuarioDAO uDAO = new UsuarioDAO();
            int idUsuario = 0;
            if (acao.equals("alterar")) {
                // CORREÇÃO AQUI
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    try {
                        idUsuario = Integer.parseInt(idUsuarioStr);
                        u = uDAO.getCarregaPorID(idUsuario);
                        if (u.getIdUsuario() > 0) {
                            RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_usuario.jsp");
                            request.setAttribute("usuario", u);
                            disp.forward(request, response);
                        } else {
                            exibirMensagem("Usuario não encontrado", false, "", response);
                        }

                    } catch (NumberFormatException e) {
                        exibirMensagem("ID de usuário inválido", false, "", response);
                    }
                }
            } else if (acao.equals("desativar")) {
                // CORREÇÃO AQUI
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    try {
                        idUsuario = Integer.parseInt(idUsuarioStr);
                        if (idUsuario != 0) {
                            u.setIdUsuario(idUsuario);
                            if (uDAO.desativar(u)) {
                                exibirMensagem("Usuario desativado com sucesso", true, "gerenciar_usuario.do?acao=listarTodos", response);
                            } else {
                                exibirMensagem("Erro ao desativar", false, "", response);
                            }
                        }
                    } catch (NumberFormatException e) {
                        exibirMensagem("id de usuario invalido", false, "", response);
                    }
                }
            } else if (acao.equals("listarTodos")) {
                // CORREÇÃO AQUI
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    ArrayList<Usuario> listarTodos = new ArrayList<>();
                    listarTodos = uDAO.getLista();

                    RequestDispatcher disp = getServletContext().getRequestDispatcher("/listar_usuario.jsp");
                    request.setAttribute("listarTodos", listarTodos);
                    disp.forward(request, response);
                }
            }

        } catch (Exception e) {
            out.println(e);
            exibirMensagem("Erro ao acessar o banco", false, "", response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        response.setContentType("text/html");
        ArrayList<String> erros = new ArrayList<String>();

        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String dataNascStr = request.getParameter("dataNasc");
        String statusStr = request.getParameter("status");
        String idUsuarioStr = request.getParameter("idUsuario");
        String idPerfilStr = request.getParameter("idPerfil");

        try {

            int status = 0;
            int idUsuario = 0;
            int idPerfil = 0;

            if (idUsuarioStr != null && !idUsuarioStr.isEmpty()) {
                try {
                    idUsuario = Integer.parseInt(idUsuarioStr);
                } catch (NumberFormatException e) {
                    erros.add("ID de usuario inválido");
                }
            }
            if (statusStr != null && !statusStr.isEmpty()) {
                try {
                    status = Integer.parseInt(statusStr);
                } catch (NumberFormatException e) {
                    erros.add("status do usuario inválido");
                }
            }
            if (idPerfilStr != null && !idPerfilStr.isEmpty()) {
                try {
                    idPerfil = Integer.parseInt(idPerfilStr);
                } catch (NumberFormatException e) {
                    erros.add("idPerfil do usuario inválido");
                }
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Usuario u = new Usuario();
            try {
                if (dataNascStr != null && !dataNascStr.isEmpty()) {
                    u.setDataNasc(df.parse(dataNascStr));
                }
            } catch (ParseException pe) {
                pe.printStackTrace();
            }

            if (nome == null || nome.trim().isEmpty()) {
                erros.add("O campo nome é obrigatório");
            }

            if (login == null || login.trim().isEmpty()) {
                erros.add("O campo login é obrigatório");
            }

            if (senha == null || senha.trim().isEmpty()) {
                erros.add("O campo senha é obrigatório");
            }

            if (erros.size() > 0) {
                String campos = "";
                for (String erro : erros) {
                    campos += "\\n - " + erro;
                }
                exibirMensagem("Preencha o (s) campo(s)" + campos, false, "", response);
            } else {

                u.setIdUsuario(idUsuario);
                u.setNome(nome);
                u.setLogin(login);
                u.setSenha(senha);
                u.setStatus(status);
                Perfil p = new Perfil();
                p.setIdPerfil(idPerfil);
                u.setPerfil(p);

                UsuarioDAO dao = new UsuarioDAO();
                boolean sucesso = dao.gravar(u);

                if (sucesso) {
                    exibirMensagem("Usuario gravado com sucesso", true, "gerenciar_usuario.do?acao=listarTodos", response);
                } else {
                    exibirMensagem("Erro ao gravar os dados do usuario", false, "", response);
                }
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GerenciarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void exibirMensagem(String mensagem, boolean resposta, String link, HttpServletResponse response) {

        try {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('" + mensagem + "')");
            if (resposta) {
                out.println("location.href='" + link + "'");
            } else {
                out.println("history.back();");
            }
            out.println("</script>");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
