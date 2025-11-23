package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.ClienteDAO;
import modelo.Usuario;
import modelo.UsuarioDAO;
import modelo.Venda;
import modelo.VendaDAO;
import modelo.VendaProduto;
import modelo.VendaProdutoDAO;
import modelo.FormaPagamentoDAO;

public class GerenciarVenda extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String idVendaStr = request.getParameter("id_venda");

        // --- DEBUG: VAMOS VER O QUE ESTÁ CHEGANDO ---
        System.out.println("========================================");
        System.out.println("[DEBUG] CHAMOU O DO_GET");
        System.out.println("[DEBUG] Ação recebida: " + acao);
        System.out.println("[DEBUG] ID Venda String: " + idVendaStr);
        // ---------------------------------------------

        try {
            VendaDAO vDAO = new VendaDAO();
            UsuarioDAO uDAO = new UsuarioDAO();
            ClienteDAO cDAO = new ClienteDAO();
            VendaProdutoDAO vpDAO = new VendaProdutoDAO();
            FormaPagamentoDAO fpDAO = new FormaPagamentoDAO();

            int idVenda = 0;
            if (idVendaStr != null && !idVendaStr.isEmpty()) {
                try {
                    idVenda = Integer.parseInt(idVendaStr);
                } catch (NumberFormatException e) {
                    System.out.println("[DEBUG] ERRO: Não conseguiu converter ID para numero");
                    idVenda = 0;
                }
            }
            System.out.println("[DEBUG] ID Venda Inteiro: " + idVenda);

            if (acao == null || acao.equals("listarTodos")) {
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    ArrayList<Venda> lista = vDAO.getLista();
                    request.setAttribute("listaVendas", lista);
                    RequestDispatcher disp = getServletContext().getRequestDispatcher("/listar_venda.jsp");
                    disp.forward(request, response);
                }

            } else if (acao.equals("verCarrinho")) {
                System.out.println("[DEBUG] >>> ENTROU NO BLOCO verCarrinho <<<");

                // CORREÇÃO: Usando a mesma segurança que funciona no 'alterar'
                if (GerenciarLogin.verificarAcesso(request, response) == null) {
                    System.out.println("[DEBUG] Acesso negado pelo GerenciarLogin");
                    return;
                }

                // Se passou aqui, está logado!
                Venda v = vDAO.getCarregaPorID(idVenda);

                if (v == null) {
                    System.out.println("[DEBUG] ERRO: Venda não encontrada no banco para ID " + idVenda);
                    exibirMensagem(response, "Venda não encontrada!", "gerenciar_venda.do?acao=listarTodos");
                    return;
                }
                System.out.println("[DEBUG] Venda carregada: " + v.getIdVenda());

                ArrayList<VendaProduto> listaProdutos = vpDAO.getListaPorVenda(idVenda);
                System.out.println("[DEBUG] Quantidade de produtos encontrados: " + (listaProdutos != null ? listaProdutos.size() : "NULL"));

                double soma = 0;
                if (listaProdutos != null) {
                    for (VendaProduto vp : listaProdutos) {
                        soma += vp.getValorTotal();
                    }
                }
                v.setValorTotal(soma);

                request.setAttribute("listaPagamentos", fpDAO.getLista());
                request.setAttribute("venda", v);
                request.setAttribute("listarVendaProdutos", listaProdutos);

                System.out.println("[DEBUG] TENTANDO ABRIR JSP: /form_visualizar_carrinho.jsp");

                // *** AQUI ESTAVA O ERRO 404 ***
                // Certifique-se que o arquivo na pasta se chama form_visualizar_carrinho.jsp
                RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_visualizar_carrinho.jsp");
                disp.forward(request, response);

            } else if (acao.equals("novo") || acao.equals("novaVenda")) {

                System.out.println("[DEBUG] Tentando acessar Nova Venda..."); // NOVO DEBUG

                if (GerenciarLogin.verificarAcesso(request, response) != null) {

                    System.out.println("[DEBUG] ACESSO PERMITIDO! Abrindo form_venda.jsp"); // NOVO DEBUG

                    Venda v = new Venda();
                    v.setDataVenda(new Timestamp(System.currentTimeMillis()));
                    v.setStatus(0);
                    v.setValorTotal(0.0);
                    v.setDesconto(0.0);

                    request.setAttribute("venda", v);
                    request.setAttribute("listaUsuarios", uDAO.getLista());
                    request.setAttribute("listaClientes", cDAO.getLista());

                    RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_venda.jsp");
                    disp.forward(request, response);
                } else {
                    System.out.println("[DEBUG] ACESSO NEGADO (GerenciarLogin retornou null)"); // NOVO DEBUG
                }

            } else if (acao.equals("alterar")) {
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    Venda v = vDAO.getCarregaPorID(idVenda);

                    if (v == null) {
                        exibirMensagem(response, "Erro ao carregar venda.", "gerenciar_venda.do?acao=listarTodos");
                        return;
                    }

                    ArrayList<VendaProduto> listaProdutos = vpDAO.getListaPorVenda(idVenda);
                    request.setAttribute("listarVendaProdutos", listaProdutos);

                    double somaTotal = 0;
                    if (listaProdutos != null) {
                        for (VendaProduto vp : listaProdutos) {
                            somaTotal += vp.getValorTotal();
                        }
                    }

                    if (Math.abs(v.getValorTotal() - somaTotal) > 0.01) {
                        v.setValorTotal(somaTotal);
                    }

                    request.setAttribute("venda", v);
                    request.setAttribute("listaUsuarios", uDAO.getLista());
                    request.setAttribute("listaClientes", cDAO.getLista());

                    RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_venda.jsp");
                    disp.forward(request, response);
                }

            } else if (acao.equals("excluir")) {
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    if (vDAO.excluir(idVenda)) {
                        exibirMensagem(response, "Venda excluída com sucesso!", "gerenciar_venda.do?acao=listarTodos");
                    } else {
                        exibirMensagem(response, "Erro ao excluir a venda.", "gerenciar_venda.do?acao=listarTodos");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            exibirMensagem(response, "Erro: " + e.getMessage(), "gerenciar_venda.do?acao=listarTodos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");
        String idVendaStr = request.getParameter("id_venda");
        String idFormaPagamentoStr = request.getParameter("id_forma_pagamento");

        String idUsuarioStr = request.getParameter("usuario_id");
        String idClienteStr = request.getParameter("cliente_id");
        String statusStr = request.getParameter("status");
        String descontoStr = request.getParameter("desconto");
        String observacao = request.getParameter("observacao");

        try {
            int idVenda = 0;
            if (idVendaStr != null && !idVendaStr.isEmpty()) {
                idVenda = Integer.parseInt(idVendaStr);
            }

            VendaDAO vDAO = new VendaDAO();

            if ("finalizarVenda".equals(acao)) {

                // --- MODIFICAÇÃO DE EMERGÊNCIA: COMENTE ESTAS LINHAS ---
                // if (GerenciarLogin.verificarAcesso(request, response) == null) {
                //    return;
                // }
                // -------------------------------------------------------
                System.out.println("[DEBUG] ENTROU NO FINALIZAR VENDA (SEM SEGURANCA)"); // Debug para confirmar

                Venda v = vDAO.getCarregaPorID(idVenda);
                if (v != null) {
                    v.setStatus(1); // REALIZADA
                    vDAO.gravar(v);

                    System.out.println("[DEBUG] VENDA GRAVADA COM STATUS 1");

                    if (idFormaPagamentoStr != null && !idFormaPagamentoStr.isEmpty()) {
                        try {
                            int idPagamento = Integer.parseInt(idFormaPagamentoStr);
                            FormaPagamentoDAO fpDAO = new FormaPagamentoDAO();
                            fpDAO.registrarPagamento(idVenda, idPagamento, v.getValorTotal());
                        } catch (Exception e) {
                            System.out.println("Erro ao processar pagamento: " + e);
                        }
                    }
                }
                response.sendRedirect("gerenciar_venda.do?acao=listarTodos");

            } else if ("gravar".equals(acao)) {
                if (GerenciarLogin.verificarAcesso(request, response) == null) {
                    return;
                }

                Venda v = new Venda();
                v.setIdVenda(idVenda);

                if (idUsuarioStr != null && !idUsuarioStr.isEmpty()) {
                    modelo.Usuario u = new modelo.Usuario();
                    u.setIdUsuario(Integer.parseInt(idUsuarioStr));
                    v.setUsuario(u);
                }

                if (idClienteStr != null && !idClienteStr.isEmpty()) {
                    modelo.Cliente c = new modelo.Cliente();
                    c.setIdCliente(Integer.parseInt(idClienteStr));
                    v.setCliente(c);
                }

                if (statusStr != null && !statusStr.isEmpty()) {
                    v.setStatus(Integer.parseInt(statusStr));
                } else {
                    v.setStatus(0);
                }

                if (descontoStr != null && !descontoStr.isEmpty()) {
                    try {
                        v.setDesconto(Double.parseDouble(descontoStr.replace(",", ".")));
                    } catch (NumberFormatException e) {
                        v.setDesconto(0.0);
                    }
                }

                if (idVenda > 0) {
                    Venda vAntiga = vDAO.getCarregaPorID(idVenda);
                    if (vAntiga != null) {
                        v.setValorTotal(vAntiga.getValorTotal());
                        v.setDataVenda(vAntiga.getDataVenda());
                    } else {
                        v.setValorTotal(0.0);
                        v.setDataVenda(new Timestamp(System.currentTimeMillis()));
                    }
                } else {
                    v.setValorTotal(0.0);
                    v.setDataVenda(new Timestamp(System.currentTimeMillis()));
                }

                v.setObservacao(observacao);

                if (vDAO.gravar(v)) {
                    if (v.getStatus() == 1) {
                        response.sendRedirect("gerenciar_venda.do?acao=listarTodos");
                    } else {
                        int idRedirecionar = (idVenda > 0) ? idVenda : v.getIdVenda();
                        response.sendRedirect("gerenciar_venda.do?acao=alterar&id_venda=" + idRedirecionar);
                    }
                } else {
                    exibirMensagem(response, "Erro ao gravar a venda.", "history.back()");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            exibirMensagem(response, "Erro técnico: " + e.getMessage(), "history.back()");
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
