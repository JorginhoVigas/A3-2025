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
            function confirmarCancelar(id, cliente) {
                if (confirm('Deseja realmente cancelar a venda do cliente ' + cliente + '?')) {
                    window.open("gerenciar_venda.do?acao=excluir&id_venda=" + id, "_self");
                }
            }
        </script>

        <title>Listar Vendas</title>
    </head>
    <body>

        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>

        <%@include file="menu.jsp" %>

        <div class="content container mt-4">
            <h2>Listagem de Vendas</h2>

            <div class="text-end mb-3">
                <a href="gerenciar_venda.do?acao=novaVenda" class="btn btn-success">
                    + Nova Venda
                </a>
            </div>

            <table class="table table-hover table-striped table-bordered display" id="listarVenda">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Cliente</th>
                        <th>Usuário</th>
                        <th>Valor Total (R$)</th>
                        <th>Desconto (R$)</th>
                        <th>Status</th>
                        <th>Data da Venda</th>
                        <th>Ações</th>
                    </tr>
                </thead>

                <tfoot>
                    <tr>
                        <th>ID</th>
                        <th>Cliente</th>
                        <th>Usuário</th>
                        <th>Valor Total (R$)</th>
                        <th>Desconto (R$)</th>
                        <th>Status</th>
                        <th>Data da Venda</th>
                        <th>Ações</th>
                    </tr>
                </tfoot>

                <tbody>
                    <c:forEach var="v" items="${listaVendas}">
                        <tr>
                            <td>${v.idVenda}</td>
                            <td>${v.cliente.nome}</td>
                            <td>${v.usuario.nome}</td>

                            <td>
                                <fmt:formatNumber value="${v.valorTotal}" type="currency"/>
                            </td>

                            <td>
                                <fmt:formatNumber value="${v.desconto}" type="currency"/>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${v.status == 1}"><span class="label label-success">Realizada</span></c:when>
                                    <c:when test="${v.status == 2}"><span class="label label-danger">Cancelada</span></c:when>
                                    <c:when test="${v.status == 0}"><span class="label label-warning">Pendente</span></c:when>
                                    <c:otherwise>Desconhecido</c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <fmt:formatDate value="${v.dataVenda}" pattern="dd/MM/yyyy HH:mm:ss"/>
                            </td>

                            <td>
                                <c:if test="${v.status == 0}">
                                    <a class="btn btn-primary btn-sm" 
                                       href="gerenciar_venda.do?acao=alterar&id_venda=${v.idVenda}" title="Editar / Continuar Venda">
                                        <i class="glyphicon glyphicon-pencil"></i> Continuar
                                    </a>
                                </c:if>
                                <c:if test="${v.status != 0}">
                                    <a class="btn btn-default btn-sm" 
                                       href="gerenciar_venda.do?acao=alterar&id_venda=${v.idVenda}" title="Ver Detalhes">
                                        <i class="glyphicon glyphicon-eye-open"></i> Ver
                                    </a>
                                </c:if>

                                <!-- BOTÃO IMPRIMIR -->
                                <a class="btn btn-info btn-sm" 
                                   href="imprimir_venda.jsp?idvenda=${v.idVenda}" 
                                   target="_blank" 
                                   title="Imprimir Cupom">
                                    <i class="glyphicon glyphicon-print"></i>
                                </a>

                                <button class="btn btn-danger btn-sm" 
                                        onclick="confirmarCancelar(${v.idVenda}, '${v.cliente.nome}')" title="Cancelar/Excluir">
                                    <i class="glyphicon glyphicon-trash"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script type="text/javascript">
                                            $(document).ready(function () {
                                                $("#listarVenda").DataTable({
                                                    "language": {
                                                        "url": "https://cdn.datatables.net/plug-ins/1.13.6/i18n/pt-BR.json"
                                                    },
                                                    "order": [[0, "desc"]] // Ordena pelo ID decrescente
                                                });
                                            });
        </script>

    </body>
</html>