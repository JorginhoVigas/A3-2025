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
            function confirmarDesativar(idUsuario, nome) {

                if (confirm('Deseja realmente desativar o usuario ' + nome + '?')) {
                    window.open("gerenciar_usuario.do?acao=desativar&idUsuario=" + idUsuario, "_self");
                }

            }
        </script>    
        <title>Listar Usuario</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>

        <%@include file="menu.jsp" %>

        <div class="content">
            <h2>Listar Usuario</h2>
            <a href="form_usuario.jsp" class="btn btn-primary">Novo Cadastro</a>
            <table class="table table-hover table-striped table-bordered display" id="listarUsuario">

                <thead>
                    <tr>
                        <th>idUsuario</th>
                        <th>Nome do Usuario</th>
                        <th>Login</th>
                        <th>Data de Nascimento</th>
                        <th>Perfil</th>
                        <th>Status</th>
                        <th>Opções</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>idUsuario</th>
                        <th>Nome do Usuario</th>
                        <th>Login</th>
                        <th>Data de Nascimento</th>
                        <th>Perfil</th>
                        <th>Status</th>
                        <th>Opções</th>
                    </tr>
                </tfoot>
                <tbody>
                    <c:forEach var="u" items="${listarTodos}">
                        <tr>
                            <td>${u.idUsuario}</td> 
                            <td>${u.nome}</td>
                            <td>${u.login}</td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${u.dataNasc}"/></td>
                            <td>${u.perfil.nome}</td>
                            <td><c:if test="${u.status==1}">Ativo</c:if>
                                <c:if test="${u.status==2}">Inativo</c:if>
                                </td>
                                <td>
                                    <a class="btn btn-primary" href="gerenciar_usuario.do?acao=alterar&idUsuario=${u.idUsuario}">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                </a>
                                <button class="btn btn-danger" onclick="confirmarDesativar(${u.idUsuario}, '${u.nome}')">
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
                                        $("#listarUsuario").DataTable({
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


