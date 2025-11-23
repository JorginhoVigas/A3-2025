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
        <link rel="stylesheet" href="datatables/jquery.dataTables.min.css"/>
        <script type="text/javascript">
            function confirmarDesativar(id, descricao) {
                if (confirm('Deseja realmente desativar o produto ' + descricao + '?')) {
                    window.open("gerenciar_produto.do?acao=desativar&id_produto=" + id, "_self");
                }
            }
        </script>
        <title>Listar Produto</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>
        <%@include file="menu.jsp" %>

        <div class="content">
            <h2>Listar Produtos</h2>
            <a href="form_produto.jsp" class="btn btn-primary">Novo Produto</a>
            <table class="table table-hover table-striped table-bordered display" id="listarProduto">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Descrição</th>
                        <th>Custo (R$)</th>
                        <th>Fornecedor</th>
                        <th>Código de Barras</th>
                        <th>Marca</th>
                        <th>Unidade</th>
                        <th>Data Aquisição</th>
                        <th>Valor Venda (R$)</th>
                        <th>Qtd Estoque</th>
                        <th>Estoque Mínimo</th>
                        <th>Categoria</th>
                        <th>Status</th>
                        <th>Opções</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>ID</th>
                        <th>Descrição</th>
                        <th>Custo (R$)</th>
                        <th>Fornecedor</th>
                        <th>Código de Barras</th>
                        <th>Marca</th>
                        <th>Unidade</th>
                        <th>Data Aquisição</th>
                        <th>Valor Venda (R$)</th>
                        <th>Qtd Estoque</th>
                        <th>Estoque Mínimo</th>
                        <th>Categoria</th>
                        <th>Status</th>
                        <th>Opções</th>
                    </tr>
                </tfoot>
                <tbody>
                    <c:forEach var="p" items="${listarTodos}">
                        <tr>
                            <td>${p.idProduto}</td>
                            <td>${p.descricao}</td>
                            <td><fmt:formatNumber value="${p.custo}" type="currency"/></td>
                            <td>${p.fornecedorId}</td>
                            <td>${p.codigoBarras}</td>
                            <td>${p.marca}</td>
                            <td>${p.unidadeMedida}</td>
                            <td><fmt:formatDate value="${p.dataAquisicao}" pattern="dd/MM/yyyy"/></td>
                            <td><fmt:formatNumber value="${p.valorVenda}" type="currency"/></td>
                            <td>${p.quantidadeEstoque}</td>
                            <td>${p.estoqueMinimo}</td>
                            <td>${p.categoriaId}</td>
                            <td>
                                <c:if test="${p.status==1}">Ativo</c:if>
                                <c:if test="${p.status==2}">Inativo</c:if>
                                </td>
                                <td>
                                    <a class="btn btn-primary" href="gerenciar_produto.do?acao=alterar&id_produto=${p.idProduto}">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                </a>
                                <button class="btn btn-danger" onclick="confirmarDesativar(${p.idProduto}, '${p.descricao}')">
                                    <i class="glyphicon glyphicon-trash"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script type="text/javascript" src="datatables/jquery.js"></script>
        <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>
        <script type="text/javascript">
                                    $(document).ready(function () {
                                        $("#listarProduto").DataTable({
                                            "language": {
                                                "url": "datatables/portugues.json"
                                            }
                                        });
                                    });

                                    function toggleMenu() {
                                        var menu = document.getElementById("nav-links");
                                        menu.classList.toggle("show");
                                    }
        </script>
    </body>
</html>
