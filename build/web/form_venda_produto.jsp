<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/estilo.css"/>
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css"/>
        <title>Adicionar Produto à Venda</title>

        <script type="text/javascript">
            // Função para preencher o valor unitário automaticamente ao selecionar o produto
            function atualizarValorUnitario() {
                var select = document.getElementById("produto_id");
                var valor = select.options[select.selectedIndex].getAttribute("data-valor");
                if (valor) {
                    document.getElementById("valor_unitario").value = valor;
                }
            }
        </script>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>
        <%@include file="menu.jsp" %>

        <div class="container content">
            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <h2 class="page-header">Gerenciar Itens da Venda #${venda.idVenda}</h2>

                    <div class="form-container panel panel-default">
                        <div class="panel-body">
                            <form method="POST" action="gerenciar_venda_produto.do" class="form-horizontal">
                                <legend>Adicionar/Alterar Produto</legend>

                                <!-- IDs Ocultos -->
                                <input type="hidden" name="id_venda_produto" id="id_venda_produto" value="${vendaProduto.idVendaProduto}"/>
                                <input type="hidden" name="id_venda" id="id_venda" value="${venda.idVenda}"/>
                                <input type="hidden" name="acao" value="gravar"/> 

                                <!-- Seleção de Produto -->
                                <div class="form-group">
                                    <label for="produto_id" class="col-sm-3 control-label">Produto:</label>
                                    <div class="col-sm-9">
                                        <select name="produto_id" id="produto_id" class="form-control" required onchange="atualizarValorUnitario()">
                                            <option value="">Selecione um produto...</option>
                                            <c:forEach var="p" items="${listaProdutos}">
                                                <option value="${p.idProduto}" data-valor="${p.valorVenda}"
                                                        <c:if test="${p.idProduto == vendaProduto.produto.idProduto}">selected</c:if> >
                                                    ${p.descricao} - (Estoque: ${p.quantidadeEstoque})
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <!-- Quantidade -->
                                <div class="form-group">
                                    <label for="quantidade" class="col-sm-3 control-label">Quantidade:</label>
                                    <div class="col-sm-4">
                                        <input type="number" name="quantidade" id="quantidade" class="form-control" required
                                               min="1" value="${vendaProduto.quantidade != null ? vendaProduto.quantidade : 1}"/>
                                    </div>
                                </div>

                                <!-- Valor Unitário -->
                                <div class="form-group">
                                    <label for="valor_unitario" class="col-sm-3 control-label">Valor Unitário (R$):</label>
                                    <div class="col-sm-4">
                                        <input type="number" step="0.01" name="valor_unitario" id="valor_unitario" class="form-control" required
                                               value="${vendaProduto.valorUnitario}"/>
                                    </div>
                                </div>

                                <!-- Botões -->
                                <div class="form-group">
                                    <div class="col-sm-offset-3 col-sm-9">
                                        <button type="submit" class="btn btn-success">
                                            <i class="glyphicon glyphicon-ok"></i> Gravar
                                        </button>
                                        <a href="gerenciar_venda_produto.do?acao=listar&id_venda=${venda.idVenda}" class="btn btn-danger">
                                            <i class="glyphicon glyphicon-remove"></i> Cancelar
                                        </a>
                                    </div>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>