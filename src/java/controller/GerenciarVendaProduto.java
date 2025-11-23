package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.ProdutoDAO;
import modelo.Venda;
import modelo.VendaDAO;
import modelo.VendaProduto;
import modelo.VendaProdutoDAO;

public class GerenciarVendaProduto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idVendaStr = request.getParameter("id_venda");
        String idVendaProdutoStr = request.getParameter("id_venda_produto");

        try {
            VendaDAO vDAO = new VendaDAO();
            VendaProdutoDAO vpDAO = new VendaProdutoDAO();
            ProdutoDAO pDAO = new ProdutoDAO();

            int idVenda = 0;
            if (idVendaStr != null && !idVendaStr.isEmpty()) {
                try {
                    idVenda = Integer.parseInt(idVendaStr);
                } catch (NumberFormatException e) {
                    idVenda = 0;
                }
            }

            // =====================================
            // LISTAR ITENS DA VENDA (COM CÁLCULO AUTOMÁTICO)
            // =====================================
            if ("listar".equals(acao)) {
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    if (idVenda > 0) {
                        Venda venda = vDAO.getCarregaPorID(idVenda);
                        ArrayList<VendaProduto> listaProdutos = vpDAO.getListaPorVenda(idVenda);

                        // --- A MÁGICA ACONTECE AQUI ---
                        // 1. Calcula a soma real dos itens
                        double somaTotal = 0;
                        for (VendaProduto vp : listaProdutos) {
                            somaTotal += vp.getValorTotal();
                        }

                        // 2. Atualiza o objeto venda na memória para mostrar na tela
                        venda.setValorTotal(somaTotal);

                        // 3. Atualiza o banco de dados para corrigir a lista geral
                        try {
                            // Se o seu DAO tiver um método específico atualizarTotalVenda, use ele.
                            // Senão, o gravar(venda) funciona pois é um UPDATE quando tem ID.
                            vDAO.gravar(venda);
                        } catch (Exception ex) {
                            System.out.println("Erro ao sincronizar total: " + ex.getMessage());
                        }
                        // ----------------------------------------------

                        request.setAttribute("venda", venda);
                        request.setAttribute("listarVendaProdutos", listaProdutos);

                        RequestDispatcher disp = getServletContext().getRequestDispatcher("/listar_venda_produto.jsp");
                        disp.forward(request, response);
                    } else {
                        exibirMensagem(response, "Venda não informada!", "gerenciar_venda.do?acao=listarTodos");
                    }
                }
            } // =====================================
            // ABRIR FORMULÁRIO
            // =====================================
            else if ("adicionar".equals(acao) || "adicionarProduto".equals(acao) || "alterar".equals(acao)) {

                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    Venda venda = vDAO.getCarregaPorID(idVenda);
                    VendaProduto vp = new VendaProduto();

                    if ("alterar".equals(acao) && idVendaProdutoStr != null) {
                        try {
                            int idVendaProduto = Integer.parseInt(idVendaProdutoStr);
                            vp = vpDAO.getCarregaPorID(idVendaProduto);
                        } catch (Exception e) {
                        }
                    }

                    request.setAttribute("venda", venda);
                    request.setAttribute("vendaProduto", vp);
                    request.setAttribute("listaProdutos", pDAO.getLista());

                    RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_venda_produto.jsp");
                    disp.forward(request, response);
                }
            } // =====================================
            // REMOVER ITEM
            // =====================================
            else if ("remover".equals(acao)) {
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    try {
                        int idVendaProduto = Integer.parseInt(idVendaProdutoStr);

                        // 1. Carrega o item antes de excluir para saber qual é a venda (ID)
                        VendaProduto vpTemp = vpDAO.getCarregaPorID(idVendaProduto);

                        if (vpTemp != null) {
                            int idVendaParaRetorno = vpTemp.getVenda().getIdVenda();

                            // 2. Exclui o item
                            if (vpDAO.excluir(idVendaProduto)) {

                                // 3. Verifica de onde veio o pedido (Opcional) ou define o padrão
                                // SE VOCÊ QUER VOLTAR PARA O CARRINHO (Tela de Pagamento):
                                response.sendRedirect("gerenciar_venda.do?acao=verCarrinho&id_venda=" + idVendaParaRetorno);

                                // SE VOCÊ QUISESSE VOLTAR PARA A LISTA SIMPLES DE ITENS (o original):
                                // response.sendRedirect("gerenciar_venda_produto.do?acao=listar&id_venda=" + idVendaParaRetorno);
                            } else {
                                exibirMensagem(response, "Erro ao excluir produto", "history.back()");
                            }
                        } else {
                            exibirMensagem(response, "Item não encontrado", "history.back()");
                        }
                    } catch (Exception e) {
                        exibirMensagem(response, "Erro técnico na exclusão: " + e.getMessage(), "history.back()");
                    }
                }
            }

        } catch (Exception e) {
            exibirMensagem(response, "Erro técnico: " + e.getMessage(), "history.back()");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String idVendaStr = request.getParameter("id_venda");
        String idVendaProdutoStr = request.getParameter("id_venda_produto");
        String idProdutoStr = request.getParameter("produto_id");
        String quantidadeStr = request.getParameter("quantidade");
        String valorUnitarioStr = request.getParameter("valor_unitario");

        try {
            int idVenda = 0;
            int idVendaProduto = 0;
            int idProduto = 0;
            int quantidade = 0;
            double valorUnitario = 0.0;

            if (idVendaStr != null && !idVendaStr.isEmpty()) {
                idVenda = Integer.parseInt(idVendaStr);
            }
            if (idVendaProdutoStr != null && !idVendaProdutoStr.isEmpty()) {
                idVendaProduto = Integer.parseInt(idVendaProdutoStr);
            }
            if (idProdutoStr != null && !idProdutoStr.isEmpty()) {
                idProduto = Integer.parseInt(idProdutoStr);
            }
            if (quantidadeStr != null && !quantidadeStr.isEmpty()) {
                quantidade = Integer.parseInt(quantidadeStr);
            }

            if (valorUnitarioStr != null && !valorUnitarioStr.isEmpty()) {
                // Troca vírgula por ponto para evitar erro de conversão
                valorUnitario = Double.parseDouble(valorUnitarioStr.replace(",", "."));
            }

            if (idVenda == 0 || idProduto == 0) {
                exibirMensagem(response, "Erro: Venda ou Produto não identificados.", "history.back()");
                return;
            }
            if (quantidade <= 0) {
                exibirMensagem(response, "A quantidade deve ser maior que zero.", "history.back()");
                return;
            }

            VendaProduto vp = new VendaProduto();
            vp.setIdVendaProduto(idVendaProduto);

            Venda v = new Venda();
            v.setIdVenda(idVenda);
            vp.setVenda(v);

            modelo.Produto p = new modelo.Produto();
            p.setIdProduto(idProduto);
            vp.setProduto(p);

            vp.setQuantidade(quantidade);
            vp.setValorUnitario(valorUnitario);
            // Calcula o total do item
            vp.setValorTotal(quantidade * valorUnitario);

            VendaProdutoDAO dao = new VendaProdutoDAO();
            boolean sucesso = dao.gravar(vp);

            if (sucesso) {
                // Redireciona para listar. Lá o Servlet vai somar esse novo item ao total da venda
                response.sendRedirect("gerenciar_venda_produto.do?acao=listar&id_venda=" + idVenda);
            } else {
                exibirMensagem(response, "Erro ao gravar item no banco.", "history.back()");
            }

        } catch (Exception e) {
            e.printStackTrace();
            exibirMensagem(response, "Erro inesperado: " + e.getMessage(), "history.back()");
        }
    }

    private void exibirMensagem(HttpServletResponse response, String msg, String link) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('" + msg + "');");
        if (link.equals("history.back()")) {
            out.println("history.back();");
        } else {
            out.println("location.href='" + link + "';");
        }
        out.println("</script>");
        out.close();
    }
}
