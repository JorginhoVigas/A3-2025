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
        <title>Cadastrar Produto</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>
        <%@include file="menu.jsp" %>

        <div class="content">
            <h2>Cadastrar Produto</h2>
            <div class="form-container">
                <form method="POST" action="gerenciar_produto.do">
                    <legend>Formulário de Produto</legend>
                    <input type="hidden" name="id_produto" id="id_produto" value="${produto.idProduto}"/>

                    <label for="descricao">Descrição</label>
                    <input type="text" name="descricao" id="descricao" required=""
                           maxlength="255" value="${produto.descricao}"/>

                    <label for="custo">Custo (R$)</label>
                    <input type="number" step="0.01" name="custo" id="custo" required=""
                           value="${produto.custo}"/>

                    <label for="fornecedor_id">Fornecedor (ID)</label>
                    <input type="number" name="fornecedor_id" id="fornecedor_id" required=""
                           value="${produto.fornecedorId}" placeholder="Ex: 1"/>

                    <label for="codigo_barras">Código de Barras</label>
                    <input type="text" name="codigo_barras" id="codigo_barras" maxlength="100"
                           value="${produto.codigoBarras}"/>

                    <label for="marca">Marca</label>
                    <input type="text" name="marca" id="marca" maxlength="100"
                           value="${produto.marca}"/>

                    <label for="unidade_medida">Unidade de Medida</label>
                    <input type="text" name="unidade_medida" id="unidade_medida" maxlength="50"
                           value="${produto.unidadeMedida}"/>

                    <label for="data_aquisicao">Data de Aquisição</label>
                    <c:if test="${not empty produto.dataAquisicao}">
                        <fmt:formatDate value="${produto.dataAquisicao}" pattern="yyyy-MM-dd" var="dataFormatada"/>
                    </c:if>
                    <input type="date" name="data_aquisicao" id="data_aquisicao"
                           value="${dataFormatada}"/>

                    <label for="quantidade_estoque">Quantidade em Estoque</label>
                    <input type="number" name="quantidade_estoque" id="quantidade_estoque" required=""
                           value="${produto.quantidadeEstoque}"/>

                    <label for="estoque_minimo">Estoque Mínimo</label>
                    <input type="number" name="estoque_minimo" id="estoque_minimo" required=""
                           value="${produto.estoqueMinimo}"/>

                    <label for="valor_venda">Valor de Venda (R$)</label>
                    <input type="number" step="0.01" name="valor_venda" id="valor_venda" required=""
                           value="${produto.valorVenda}"/>

                    <label for="categoria_id">Categoria (ID)</label>
                    <input type="number" name="categoria_id" id="categoria_id" required=""
                           value="${produto.categoriaId}" placeholder="Ex: 1"/>

                    <label for="status">Status</label>
                    <select name="status" id="status" required="">
                        <c:choose>
                            <c:when test="${produto.status == 1}">
                                <option value="1" selected="">Ativo</option>
                                <option value="2">Inativo</option>
                            </c:when>
                            <c:when test="${produto.status == 2}">
                                <option value="1">Ativo</option>
                                <option value="2" selected="">Inativo</option>
                            </c:when>
                            <c:otherwise>
                                <option value="0" selected="">Escolha uma opção</option>
                                <option value="1">Ativo</option>
                                <option value="2">Inativo</option>
                            </c:otherwise>
                        </c:choose>
                    </select>

                    <div class="form button form">
                        <button type="submit" class="btn btn-success">Gravar</button>
                        <a href="gerenciar_produto.do?acao=listarTodos"
                           class="btn btn-warning">Voltar</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
