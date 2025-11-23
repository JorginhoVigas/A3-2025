package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Perfil;
import modelo.PerfilDAO;

public class GerenciarPerfil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idPerfilStr = request.getParameter("idPerfil");
        Perfil p = new Perfil();
        try {
            PerfilDAO pDAO = new PerfilDAO();
            int idPerfil = 0;
            if (acao.equals("alterar")) {
                // CORREÇÃO AQUI
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    try {
                        idPerfil = Integer.parseInt(idPerfilStr);
                        p = pDAO.getCarregaPorID(idPerfil);
                        if (p.getIdPerfil() > 0) {
                            RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_perfil.jsp");
                            request.setAttribute("perfil", p);
                            disp.forward(request, response);
                        } else {
                            exibirMensagem("Perfil não encontrado", false, "", response);
                        }

                    } catch (NumberFormatException e) {
                        exibirMensagem("ID de perfil inválido", false, "", response);
                    }
                }
            } else if (acao.equals("desativar")) {
                // CORREÇÃO AQUI
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    try {
                        idPerfil = Integer.parseInt(idPerfilStr);
                        if (idPerfil != 0) {
                            p.setIdPerfil(idPerfil);
                            if (pDAO.desativar(p)) {
                                exibirMensagem("Perfil desativado com sucesso", true, "gerenciar_perfil.do?acao=listarTodos", response);
                            } else {
                                exibirMensagem("Erro ao desativar", false, "", response);
                            }
                        }
                    } catch (NumberFormatException e) {
                        exibirMensagem("id de perfil invalido", false, "", response);
                    }
                }
            } else if (acao.equals("listarTodos")) {
                // CORREÇÃO AQUI
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    ArrayList<Perfil> listarTodos = new ArrayList<>();
                    listarTodos = pDAO.getLista();
                    if (listarTodos.size() > 0) {
                        RequestDispatcher disp = getServletContext().getRequestDispatcher("/listar_perfil.jsp");
                        request.setAttribute("listarTodos", listarTodos);
                        disp.forward(request, response);
                    } else {
                        exibirMensagem("Lista vazia", true, "form_perfil.jsp", response);
                    }
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
        String statusStr = request.getParameter("status");
        String idPerfilStr = request.getParameter("idPerfil");

        try {

            int status = 0;
            int idPerfil = 0;

            if (idPerfilStr != null && !idPerfilStr.isEmpty()) {
                try {
                    idPerfil = Integer.parseInt(idPerfilStr);
                } catch (NumberFormatException e) {
                    erros.add("ID de perfil inválido");
                }
            }
            if (statusStr != null && !statusStr.isEmpty()) {
                try {
                    status = Integer.parseInt(statusStr);
                } catch (NumberFormatException e) {
                    erros.add("status do perfil inválido");
                }
            }

            if (nome == null || nome.trim().isEmpty()) {
                erros.add("O campo nome é obrigatório");
            }

            if (erros.size() > 0) {
                String campos = "";
                for (String erro : erros) {
                    campos += "\\n - " + erro;
                }
                exibirMensagem("Preencha o (s) campo(s)" + campos, false, "", response);
            } else {

                Perfil p = new Perfil();
                p.setIdPerfil(idPerfil);
                p.setNome(nome);
                p.setStatus(status);
                PerfilDAO dao = new PerfilDAO();
                boolean sucesso = dao.gravar(p);

                if (sucesso) {
                    exibirMensagem("Perfil gravado com sucesso", true, "gerenciar_perfil.do?acao=listarTodos", response);
                } else {
                    exibirMensagem("Erro ao gravar os dados do perfil", false, "", response);
                }
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GerenciarPerfil.class.getName()).log(Level.SEVERE, null, ex);
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
