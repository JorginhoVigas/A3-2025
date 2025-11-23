package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Cliente;
import modelo.ClienteDAO;
import modelo.Fornecedor;
import modelo.FornecedorDAO;
import modelo.Produto;
import modelo.ProdutoDAO;
import modelo.Venda;
import modelo.VendaDAO;

@WebServlet(name = "GerenciarRelatorio", urlPatterns = {"/gerenciar_relatorio.do"})
public class GerenciarRelatorio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String acao = request.getParameter("acao");

        try {
            if (acao.equals("vendas_por_periodo")) {
                // --- RELATÓRIO DE VENDAS (COM FILTRO DE DATA) ---
                String dataIniStr = request.getParameter("data_inicio");
                String dataFimStr = request.getParameter("data_fim");

                Date dataInicio = Date.valueOf(dataIniStr);
                Date dataFim = Date.valueOf(dataFimStr);

                VendaDAO dao = new VendaDAO();
                // Certifique-se de que este método existe no VendaDAO!
                ArrayList<Venda> lista = dao.listarVendasPorPeriodo(dataInicio, dataFim);

                request.setAttribute("listaRelatorio", lista);
                request.setAttribute("dataInicio", dataIniStr);
                request.setAttribute("dataFim", dataFimStr);

                RequestDispatcher dispatcher = request.getRequestDispatcher("listar_relatorio_venda.jsp");
                dispatcher.forward(request, response);

            } else if (acao.equals("clientes_geral")) {
                // --- RELATÓRIO DE CLIENTES (GERAL) ---
                ClienteDAO dao = new ClienteDAO();
                ArrayList<Cliente> lista = dao.getLista(); // Já vem ordenado e só ativos

                request.setAttribute("listaClientes", lista);
                RequestDispatcher dispatcher = request.getRequestDispatcher("listar_relatorio_cliente.jsp");
                dispatcher.forward(request, response);

            } else if (acao.equals("produtos_geral")) {
                // --- RELATÓRIO DE PRODUTOS (GERAL) ---
                ProdutoDAO dao = new ProdutoDAO();
                ArrayList<Produto> lista = dao.getLista();

                request.setAttribute("listaProdutos", lista);
                RequestDispatcher dispatcher = request.getRequestDispatcher("listar_relatorio_produto.jsp");
                dispatcher.forward(request, response);

            } else if (acao.equals("fornecedores_geral")) {
                // --- RELATÓRIO DE FORNECEDORES (GERAL) ---
                FornecedorDAO dao = new FornecedorDAO();
                ArrayList<Fornecedor> lista = dao.getLista();

                request.setAttribute("listaFornecedores", lista);
                RequestDispatcher dispatcher = request.getRequestDispatcher("listar_relatorio_fornecedor.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("<html><body>");
            out.print("<script>alert('Erro ao gerar relatório: " + e.getMessage() + "'); history.back();</script>");
            out.print("</body></html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
