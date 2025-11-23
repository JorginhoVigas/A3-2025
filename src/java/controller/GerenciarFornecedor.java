package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Fornecedor;
import modelo.FornecedorDAO;

public class GerenciarFornecedor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        String idFornecedorStr = request.getParameter("id_fornecedor");
        Fornecedor f = new Fornecedor();

        try (PrintWriter out = response.getWriter()) {
            FornecedorDAO fDAO = new FornecedorDAO();
            int idFornecedor = 0;

            if ("alterar".equals(acao)) {
                idFornecedor = Integer.parseInt(idFornecedorStr);
                f = fDAO.getCarregaPorID(idFornecedor);
                if (f != null) {
                    RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_fornecedor.jsp");
                    request.setAttribute("fornecedor", f);
                    disp.forward(request, response);
                } else {
                    exibirMensagem("Fornecedor não encontrado", false, "", response);
                }
            }

            if ("desativar".equals(acao)) {
                idFornecedor = Integer.parseInt(idFornecedorStr);
                f.setIdFornecedor(idFornecedor);
                if (fDAO.desativar(f)) {
                    exibirMensagem("Fornecedor desativado com sucesso!", true, "gerenciar_fornecedor.do?acao=listarTodos", response);
                } else {
                    exibirMensagem("Erro ao desativar fornecedor", false, "", response);
                }
            }

            if ("listarTodos".equals(acao)) {
                ArrayList<Fornecedor> listarTodos = fDAO.getLista();
                RequestDispatcher disp = getServletContext().getRequestDispatcher("/listar_fornecedor.jsp");
                request.setAttribute("listarTodos", listarTodos);
                disp.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            exibirMensagem("Erro ao acessar o banco de dados", false, "", response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idFornecedorStr = request.getParameter("id_fornecedor");
        String statusStr = request.getParameter("status");
        String razaoSocial = request.getParameter("razao_social");
        String nomeFantasia = request.getParameter("nome_fantasia");
        String cnpj = request.getParameter("cnpj");
        String inscricaoEstadual = request.getParameter("inscricao_estadual");
        String dataCadastroStr = request.getParameter("data_cadastro");
        String telefone = request.getParameter("telefone");
        String enderecoIdStr = request.getParameter("endereco_id");

        try {
            int idFornecedor = (idFornecedorStr != null && !idFornecedorStr.isEmpty()) ? Integer.parseInt(idFornecedorStr) : 0;
            int status = Integer.parseInt(statusStr);
            int enderecoId = Integer.parseInt(enderecoIdStr);
            Date dataCadastro = Date.valueOf(dataCadastroStr);

            Fornecedor f = new Fornecedor();
            f.setIdFornecedor(idFornecedor);
            f.setStatus(status);
            f.setRazaoSocial(razaoSocial);
            f.setNomeFantasia(nomeFantasia);
            f.setCnpj(cnpj);
            f.setInscricaoEstadual(inscricaoEstadual);
            f.setDataCadastro(dataCadastro);
            f.setTelefone(telefone);
            f.setEnderecoId(enderecoId);

            FornecedorDAO fDAO = new FornecedorDAO();
            boolean sucesso = fDAO.gravar(f);

            if (sucesso) {
                exibirMensagem("Fornecedor gravado com sucesso!", true, "gerenciar_fornecedor.do?acao=listarTodos", response);
            } else {
                exibirMensagem("Erro ao gravar fornecedor!", false, "", response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            exibirMensagem("Erro ao processar dados!", false, "", response);
        }
    }

    private static void exibirMensagem(String mensagem, boolean sucesso, String link, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<script type='text/javascript'>");
        out.println("alert('" + mensagem + "');");
        if (sucesso) {
            out.println("location.href='" + link + "';");
        } else {
            out.println("history.back();");
        }
        out.println("</script>");
        out.close();
    }
}
