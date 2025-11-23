package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.MenuPerfilDAO;
import modelo.Perfil;
import modelo.PerfilDAO;

public class GerenciarMenuPerfil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idPerfilStr = request.getParameter("idPerfil");

        try {
            PerfilDAO pDAO = new PerfilDAO();
            MenuPerfilDAO mpDAO = new MenuPerfilDAO();

            if ("gerenciar".equals(acao)) {

                if (idPerfilStr == null || idPerfilStr.trim().isEmpty()) {
                    exibirMensagem("idPerfil não informado", false, "", response);
                    return;
                }

                int idPerfil = Integer.parseInt(idPerfilStr);
                Perfil p = pDAO.getCarregaPorID(idPerfil);

                if (p != null && p.getIdPerfil() > 0) {

                    p.setMenus(mpDAO.getCarregaMenusPorPerfil(idPerfil));

                    request.setAttribute("perfil", p);

                    RequestDispatcher disp = getServletContext()
                            .getRequestDispatcher("/form_menu_perfil.jsp");

                    disp.forward(request, response);
                    return;

                } else {
                    exibirMensagem("Perfil não encontrado", false, "", response);
                    return;
                }
            }

            if ("desvincular".equals(acao)) {

                String idMenuStr = request.getParameter("idMenu");

                if (idMenuStr == null || idMenuStr.isEmpty()
                        || idPerfilStr == null || idPerfilStr.isEmpty()) {

                    exibirMensagem("idMenu ou idPerfil inválidos", false, "", response);
                    return;
                }

                int idMenu = Integer.parseInt(idMenuStr);
                int idPerfil = Integer.parseInt(idPerfilStr);

                boolean ok = mpDAO.desvincular(idMenu, idPerfil);

                if (ok) {
                    exibirMensagem("Desvinculado com sucesso", true,
                            "gerenciar_menu_perfil.do?acao=gerenciar&idPerfil=" + idPerfil, response);
                } else {
                    exibirMensagem("Erro ao desvincular", false, "", response);
                }

                return;
            }

            exibirMensagem("Ação não informada", false, "", response);

        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
            exibirMensagem("Erro ao acessar o banco", false, "", response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String idMenuStr = request.getParameter("idMenu");
        String idPerfilStr = request.getParameter("idPerfil");

        try {

            if (idMenuStr == null || idMenuStr.isEmpty()
                    || idPerfilStr == null || idPerfilStr.isEmpty()) {

                exibirMensagem("Campos obrigatórios devem ser preenchidos", false, "", response);
                return;
            }

            MenuPerfilDAO mpDAO = new MenuPerfilDAO();

            boolean ok = mpDAO.vincular(
                    Integer.parseInt(idMenuStr),
                    Integer.parseInt(idPerfilStr)
            );

            if (ok) {
                exibirMensagem("Vinculado com sucesso", true,
                        "gerenciar_menu_perfil.do?acao=gerenciar&idPerfil=" + idPerfilStr, response);
            } else {
                exibirMensagem("Erro ao vincular", false, "", response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print(e);
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
}
