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
            function confirmarDesativar(id, nome) {
                if (confirm('Deseja realmente desativar o fornecedor ' + nome + '?')) {
                    window.open("gerenciar_fornecedor.do?acao=desativar&id_fornecedor=" + id, "_self");
                }
            }
        </script>
        <title>Listar Fornecedores</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>
        <%@include file="menu.jsp" %>

        <div class="content">
            <h2>Listar Fornecedores</h2>
            <a href="form_fornecedor.jsp" class="btn btn-primary">Novo Fornecedor</a>
            <table class="table table-hover table-striped table-bordered display" id="listarFornecedor">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Razão Social</th>
                        <th>Nome Fantasia</th>
                        <th>CNPJ</th>
                        <th>Telefone</th>
                        <th>Data Cadastro</th>
                        <th>Status</th>
                        <th>Opções</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>ID</th>
                        <th>Razão Social</th>
                        <th>Nome Fantasia</th>
                        <th>CNPJ</th>
                        <th>Telefone</th>
                        <th>Data Cadastro</th>
                        <th>Status</th>
                        <th>Opções</th>
                    </tr>
                </tfoot>
                <tbody>
                    <c:forEach var="f" items="${listarTodos}">
                        <tr>
                            <td>${f.idFornecedor}</td>
                            <td>${f.razaoSocial}</td>
                            <td>${f.nomeFantasia}</td>
                            <td>${f.cnpj}</td>
                            <td>${f.telefone}</td>
                            <td><fmt:formatDate value="${f.dataCadastro}" pattern="dd/MM/yyyy"/></td>
                            <td>
                                <c:if test="${f.status==1}">Ativo</c:if>
                                <c:if test="${f.status==2}">Inativo</c:if>
                                </td>
                                <td>
                                    <a class="btn btn-primary" href="gerenciar_fornecedor.do?acao=alterar&id_fornecedor=${f.idFornecedor}">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                </a>
                                <button class="btn btn-danger" onclick="confirmarDesativar(${f.idFornecedor}, '${f.nomeFantasia}')">
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
                                        $("#listarFornecedor").DataTable({
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
