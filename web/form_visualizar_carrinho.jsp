<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/estilo.css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css"/>

        <script type="text/javascript">
            function retirarProduto(idVendaProduto, produto) {
                if (confirm('Deseja realmente remover o item ' + produto + '?')) {
                    // Redireciona para o Servlet que remove o item
                    window.location.href = "gerenciar_venda_produto.do?acao=remover&id_venda_produto=" + idVendaProduto;
                }
            }
        </script>    
        <title>Visualizar Carrinho</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>
        <%@include file="menu.jsp" %>

        <div class="content container">
            <h2>Meu Carrinho (Venda #${venda.idVenda})</h2>

            <div class="alert alert-info">
                <strong>Vendedor:</strong> ${venda.usuario.nome} <br>
                <strong>Cliente:</strong> ${venda.cliente.nome}
            </div>

            <table class="table table-hover table-striped table-bordered display" id="listarProduto">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>PRODUTO</th>
                        <th>QTD</th>
                        <th>VALOR UNIT.</th>
                        <th>TOTAL</th>
                        <th>REMOVER</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="vp" items="${listarVendaProdutos}">
                        <tr>
                            <td align="center">${vp.idVendaProduto}</td> 
                            <td>${vp.produto.descricao}</td>
                            <td>${vp.quantidade}</td>
                            <td><fmt:formatNumber value="${vp.valorUnitario}" type="currency"/></td>
                            <td><fmt:formatNumber value="${vp.valorTotal}" type="currency"/></td>
                            <td align="center">
                                <button class="btn btn-danger btn-sm" onclick="retirarProduto(${vp.idVendaProduto}, '${vp.produto.descricao}')">
                                    <i class="glyphicon glyphicon-trash"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="text-right" style="margin-top: 20px; font-size: 1.5em;">
                <strong>Valor Total: <fmt:formatNumber value="${venda.valorTotal}" type="currency"/></strong>
            </div>

            <hr>

            <div class="panel panel-default" style="background-color: #f7f7f7;">
                <div class="panel-body">
                    <form action="gerenciar_venda.do" method="POST">
                        <input type="hidden" name="acao" value="finalizarVenda"/>
                        <input type="hidden" name="id_venda" value="${venda.idVenda}"/>

                        <div class="row">
                            <div class="col-md-4 col-md-offset-4">
                                <div class="form-group text-center">
                                    <label for="id_forma_pagamento" style="font-size: 1.2em;">Forma de Pagamento</label>
                                    <select name="id_forma_pagamento" id="id_forma_pagamento" class="form-control input-lg" required>
                                        <option value="">-- Selecione --</option>
                                        <c:forEach var="fp" items="${listaPagamentos}">
                                            <option value="${fp.idFormaPagamento}">${fp.nome}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="text-center" style="margin-top: 20px;">
                            <a href="gerenciar_venda.do?acao=listarTodos" class="btn btn-danger">
                                <i class="glyphicon glyphicon-remove"></i> Sair / Cancelar
                            </a>

                            <a href="gerenciar_venda.do?acao=alterar&id_venda=${venda.idVenda}" class="btn btn-warning">
                                <i class="glyphicon glyphicon-plus"></i> Adicionar Mais Itens
                            </a>

                            <button type="submit" class="btn btn-success btn-lg" style="margin-left: 15px;">
                                <i class="glyphicon glyphicon-ok"></i> Confirmar e Finalizar
                            </button>     
                        </div>
                    </form>
                </div>
            </div>
            <br><br>
        </div>

        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script type="text/javascript">
                                    $(document).ready(function () {
                                        $("#listarProduto").DataTable({
                                            "language": {"url": "https://cdn.datatables.net/plug-ins/1.13.6/i18n/pt-BR.json"}
                                        });
                                    });
        </script>    
    </body>
</html>