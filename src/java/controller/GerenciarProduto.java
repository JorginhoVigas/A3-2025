package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Produto;
import modelo.ProdutoDAO;

public class GerenciarProduto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idProdutoStr = request.getParameter("id_produto");
        Produto p = new Produto();

        try {
            ProdutoDAO pDAO = new ProdutoDAO();
            int idProduto = 0;

            if ("alterar".equals(acao)) {
                // CORREÇÃO AQUI
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    try {
                        idProduto = Integer.parseInt(idProdutoStr);
                        p = pDAO.getCarregaPorID(idProduto);
                        if (p != null && p.getIdProduto() > 0) {
                            RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_produto.jsp");
                            request.setAttribute("produto", p);
                            disp.forward(request, response);
                        } else {
                            exibirMensagem("Produto não encontrado", false, "", response);
                        }
                    } catch (NumberFormatException e) {
                        exibirMensagem("ID de produto inválido", false, "", response);
                    }
                }
            } else if ("desativar".equals(acao)) {
                // CORREÇÃO AQUI
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    try {
                        idProduto = Integer.parseInt(idProdutoStr);
                        if (idProduto != 0) {
                            p.setIdProduto(idProduto);
                            if (pDAO.desativar(p)) {
                                exibirMensagem("Produto desativado com sucesso!", true,
                                        "gerenciar_produto.do?acao=listarTodos", response);
                            } else {
                                exibirMensagem("Erro ao desativar produto", false, "", response);
                            }
                        }
                    } catch (NumberFormatException e) {
                        exibirMensagem("ID de produto inválido", false, "", response);
                    }
                }
            } else if ("listarTodos".equals(acao)) {
                // CORREÇÃO AQUI
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    ArrayList<Produto> listarTodos = pDAO.getLista();
                    RequestDispatcher disp = getServletContext().getRequestDispatcher("/listar_produto.jsp");
                    request.setAttribute("listarTodos", listarTodos);
                    disp.forward(request, response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace(out);
            exibirMensagem("Erro ao acessar o banco de dados: " + e.getMessage(), false, "", response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        ArrayList<String> erros = new ArrayList<>();

        String idProdutoStr = request.getParameter("id_produto");
        String descricao = request.getParameter("descricao");
        String custoStr = request.getParameter("custo");
        String fornecedorIdStr = request.getParameter("fornecedor_id");
        String codigoBarras = request.getParameter("codigo_barras");
        String marca = request.getParameter("marca");
        String unidadeMedida = request.getParameter("unidade_medida");
        String dataAquisicaoStr = request.getParameter("data_aquisicao");
        String quantidadeEstoqueStr = request.getParameter("quantidade_estoque");
        String estoqueMinimoStr = request.getParameter("estoque_minimo");
        String valorVendaStr = request.getParameter("valor_venda");
        String statusStr = request.getParameter("status");
        String categoriaIdStr = request.getParameter("categoria_id");

        try {
            int idProduto = 0;
            double custo = 0.0;
            int fornecedorId = 0;
            int quantidadeEstoque = 0;
            int estoqueMinimo = 0;
            double valorVenda = 0.0;
            int status = 0;
            int categoriaId = 0;
            Date dataAquisicao = null;

            if (idProdutoStr != null && !idProdutoStr.isEmpty()) {
                try {
                    idProduto = Integer.parseInt(idProdutoStr);
                } catch (NumberFormatException e) {
                    erros.add("ID do produto inválido");
                }
            }

            if (custoStr != null && !custoStr.isEmpty()) {
                try {
                    custo = Double.parseDouble(custoStr);
                } catch (NumberFormatException e) {
                    erros.add("Custo inválido");
                }
            }

            if (fornecedorIdStr != null && !fornecedorIdStr.isEmpty()) {
                try {
                    fornecedorId = Integer.parseInt(fornecedorIdStr);
                } catch (NumberFormatException e) {
                    erros.add("ID do fornecedor inválido");
                }
            }

            if (quantidadeEstoqueStr != null && !quantidadeEstoqueStr.isEmpty()) {
                try {
                    quantidadeEstoque = Integer.parseInt(quantidadeEstoqueStr);
                } catch (NumberFormatException e) {
                    erros.add("Quantidade em estoque inválida");
                }
            }

            if (estoqueMinimoStr != null && !estoqueMinimoStr.isEmpty()) {
                try {
                    estoqueMinimo = Integer.parseInt(estoqueMinimoStr);
                } catch (NumberFormatException e) {
                    erros.add("Estoque mínimo inválido");
                }
            }

            if (valorVendaStr != null && !valorVendaStr.isEmpty()) {
                try {
                    valorVenda = Double.parseDouble(valorVendaStr);
                } catch (NumberFormatException e) {
                    erros.add("Valor de venda inválido");
                }
            }

            if (statusStr != null && !statusStr.isEmpty()) {
                try {
                    status = Integer.parseInt(statusStr);
                } catch (NumberFormatException e) {
                    erros.add("Status inválido");
                }
            }

            if (categoriaIdStr != null && !categoriaIdStr.isEmpty()) {
                try {
                    categoriaId = Integer.parseInt(categoriaIdStr);
                } catch (NumberFormatException e) {
                    erros.add("ID de categoria inválido");
                }
            }

            if (dataAquisicaoStr != null && !dataAquisicaoStr.isEmpty()) {
                try {
                    dataAquisicao = Date.valueOf(dataAquisicaoStr);
                } catch (IllegalArgumentException e) {
                    erros.add("Data de aquisição inválida");
                }
            }

            if (descricao == null || descricao.trim().isEmpty()) {
                erros.add("O campo descrição é obrigatório");
            }

            if (marca == null || marca.trim().isEmpty()) {
                erros.add("O campo marca é obrigatório");
            }

            if (!erros.isEmpty()) {
                StringBuilder campos = new StringBuilder();
                for (String erro : erros) {
                    campos.append("\\n - ").append(erro);
                }
                exibirMensagem("Preencha corretamente os seguintes campos:" + campos, false, "", response);
                return;
            }

            Produto p = new Produto();
            p.setIdProduto(idProduto);
            p.setDescricao(descricao);
            p.setCusto(custo);
            p.setFornecedorId(fornecedorId);
            p.setCodigoBarras(codigoBarras);
            p.setMarca(marca);
            p.setUnidadeMedida(unidadeMedida);
            p.setDataAquisicao(dataAquisicao);
            p.setQuantidadeEstoque(quantidadeEstoque);
            p.setEstoqueMinimo(estoqueMinimo);
            p.setValorVenda(valorVenda);
            p.setStatus(status);
            p.setCategoriaId(categoriaId);

            ProdutoDAO pDAO = new ProdutoDAO();
            boolean sucesso = pDAO.gravar(p);

            if (sucesso) {
                exibirMensagem("Produto gravado com sucesso!", true, "gerenciar_produto.do?acao=listarTodos", response);
            } else {
                exibirMensagem("Erro ao gravar o produto", false, "", response);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GerenciarProduto.class.getName()).log(Level.SEVERE, null, ex);
            exibirMensagem("Erro ao conectar ao banco de dados.", false, "", response);
        }
    }

    private static void exibirMensagem(String mensagem, boolean sucesso, String link, HttpServletResponse response) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Gerencia operações de produto";
    }
}
