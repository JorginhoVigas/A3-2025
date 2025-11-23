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
            function confirmarDesativar(idCliente, nome) {
                if (confirm('Deseja realmente desativar o cliente ' + nome + '?')) {
                    window.open("gerenciar_cliente.do?acao=desativar&idCliente=" + idCliente, "_self");
                }
            }
        </script>    
        <title>Listar Cliente</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>

        <%@include file="menu.jsp" %>

        <div class="content">
            <h2>Listar Cliente</h2>
            <a href="form_cliente.jsp" class="btn btn-primary">Novo Cadastro</a>
            <table class="table table-hover table-striped table-bordered display" id="listarCliente">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome / Razão Social</th>
                        <th>Tipo Doc</th>
                        <th>CPF/CNPJ</th>
                        <th>Telefone</th>
                        <th>Status</th>
                        <th>Opções</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>ID</th>
                        <th>Nome / Razão Social</th>
                        <th>Tipo Doc</th>
                        <th>CPF/CNPJ</th>
                        <th>Telefone</th>
                        <th>Status</th>
                        <th>Opções</th>
                    </tr>
                </tfoot>
                <tbody>
                    <c:forEach var="c" items="${listarTodos}">
                        <tr>
                            <td>${c.idCliente}</td> 
                            <td>${c.nome}</td>
                            <td>
                                <c:if test="${c.tipoDocumento==1}">CPF</c:if>
                                <c:if test="${c.tipoDocumento==2}">CNPJ</c:if>
                                </td>
                                <td>${c.cpfCnpj}</td>
                            <td>${c.telefone}</td>
                            <td>
                                <c:if test="${c.status==1}">Ativo</c:if>
                                <c:if test="${c.status==2}">Inativo</c:if>
                                </td>
                                <td>
                                    <a class="btn btn-primary" 
                                       href="gerenciar_cliente.do?acao=alterar&idCliente=${c.idCliente}">
                                    <i class="glyphicon glyphicon-pencil"></i>
                                </a>
                                <button class="btn btn-danger" 
                                        onclick="confirmarDesativar(${c.idCliente}, '${c.nome}')">
                                    <i class="glyphicon glyphicon-trash"></i>
                                </button>
                                <a href="gerenciar_venda.do?acao=novaVenda&idCliente=${c.idCliente}" 
                                   class="btn btn-success">  
                                    <i class="glyphicon glyphicon-shopping-cart"></i> Realizar Venda
                                </a>   
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
                                                $("#listarCliente").DataTable({
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
