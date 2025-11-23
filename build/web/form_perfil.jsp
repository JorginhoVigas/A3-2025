<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/estilo.css"/>
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css"/>
        <link rel="stylesheet" href="datatables/jquery.dataTables.min.css"/>
        <title>Cadastrar Perfil</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>

        <%@include file="menu.jsp" %>

        <div class="content">
            <h2>Cadastrar Perfil</h2>
            <div class="form-container">
                <form method="POST" action="gerenciar_perfil.do">
                    <legend>Formulário de Perfil</legend>

                    <input type="hidden" name="idPerfil" id="idPerfil" value="${perfil.idPerfil}"/>


                    <label for="nome">Nome do Perfil</label>
                    <input type="text" name="nome" id="nome" required="" 
                           maxlength="100" value="${perfil.nome}"/>

                    <label for="status">Status</label>
                    <select name="status" required="">
                        <c:if test="${perfil.status==null}">  
                            <option value="0">Escolha uma opção</option>
                            <option value="1">Ativo</option>
                            <option value="2">Inativo</option>
                        </c:if>
                        <c:if test="${perfil.status==1}">
                            <option value="1" selected="">Ativo</option>
                            <option value="2">Inativo</option>
                        </c:if> 
                        <c:if test="${perfil.status==2}">
                            <option value="1">Ativo</option>
                            <option value="2" selected="">Inativo</option>
                        </c:if>    
                    </select>

                    <div class="form button form">
                        <button type="submit" class="btn btn-success">Gravar</button>
                        <a href="gerenciar_perfil.do?acao=listarTodos" 
                           class="btn btn-warning">Voltar</a>
                    </div>

                </form>
            </div>  


        </div> 
        <script type="text/javascript" src="datatables/jquery.js"></script>
        <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#listarPerfil").DataTable({
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

