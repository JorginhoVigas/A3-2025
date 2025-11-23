<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

        <link rel="stylesheet" href="css/estilo.css"/>
        <!-- CDNs para garantir o estilo e funcionamento -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css"/>

        <script type="text/javascript">
            function confirmarRemover(idVendaProduto, produto) {
                if (confirm('Deseja realmente remover o produto ' + produto + ' da venda?')) {
                    window.open("gerenciar_venda_produto.do?acao=remover&id_venda_produto=" + idVendaProduto, "_self");
                }
            }
        </script>
        <title>Produtos da Venda</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>
        <%@include file="menu.jsp" %>

        <div class="content container mt-4">
            <h2>Produtos da Venda</h2>
            <h4>Venda nº ${venda.idVenda} - Cliente: ${venda.cliente.nome}</h4>

            <a href="gerenciar_venda_produto.do?acao=adicionarProduto&id_venda=${venda.idVenda}" 
               class="btn btn-primary">Adicionar Produto</a>

            <br><br>

            <table class="table table-hover table-striped table-bordered display" id="listarVendaProduto">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Produto</th>
                        <th>Quantidade</th>
                        <th>Valor Unitário (R$)</th>
                        <th>Subtotal (R$)</th>
                        <th>Opções</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>ID</th>
                        <th>Produto</th>
                        <th>Quantidade</th>
                        <th>Valor Unitário (R$)</th>
                        <th>Subtotal (R$)</th>
                        <th>Opções</th>
                    </tr>
                </tfoot>
                <tbody>
                    <c:forEach var="vp" items="${listarVendaProdutos}">
                        <tr>
                            <td>${vp.idVendaProduto}</td>
                            <td>${vp.produto.descricao}</td>
                            <td>${vp.quantidade}</td>
                            <td><fmt:formatNumber value="${vp.valorUnitario}" type="currency"/></td>
                            <td><fmt:formatNumber value="${vp.valorTotal}" type="currency"/></td>
                            <td>
                                <a class="btn btn-primary" 
                                   href="gerenciar_venda_produto.do?acao=alterar&id_venda_produto=${vp.idVendaProduto}">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                </a>
                                <button class="btn btn-danger" 
                                        onclick="confirmarRemover(${vp.idVendaProduto}, '${vp.produto.descricao}')">
                                    <i class="glyphicon glyphicon-trash"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="text-right">
                <h4><strong>Total da Venda: </strong>
                    <fmt:formatNumber value="${venda.valorTotal}" type="currency"/>
                </h4>
            </div>

            <!-- CORREÇÃO AQUI: Botão Voltar agora vai para a edição da venda específica -->
            <a href="gerenciar_venda.do?acao=alterar&id_venda=${venda.idVenda}" class="btn btn-warning">Voltar</a>

        </div>

        <!-- Scripts via CDN -->
        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script type="text/javascript">
                                            $(document).ready(function () {
                                                $("#listarVendaProduto").DataTable({
                                                    "language": {
                                                        "url": "https://cdn.datatables.net/plug-ins/1.13.6/i18n/pt-BR.json"
                                                    }
                                                });
                                            });
        </script>
    </body>
</html>