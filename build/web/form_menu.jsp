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
        <title>Cadastrar Menu</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>

        <%@include file="menu.jsp" %>

        <div class="content">
            <h2>Cadastrar Menu</h2>
            <div class="form-container">
                <form method="POST" action="gerenciar_menu.do">
                    <legend>Formulário de Menu</legend>

                    <input type="hidden" name="idMenu" id="idMenu" value="${menu.idMenu}"/>


                    <label for="nome">Nome do Menu</label>
                    <input type="text" name="nome" id="nome" required="" 
                           maxlength="100" value="${menu.nome}"/>

                    <label for="link">Link do Menu</label>
                    <input type="text" name="link" id="link" required="" 
                           maxlength="100" value="${menu.link}"/>

                    <label for="icone">Icone do Menu</label>
                    <input type="text" name="icone" id="icone" 
                           maxlength="100" value="${menu.icone}"/>

                    <label for="exibir">Exibir</label>
                    <select name="exibir" required="">
                        <c:if test="${menu.exibir==null}">  
                            <option value="0">Escolha uma opção</option>
                            <option value="1">Sim</option>
                            <option value="2">Não</option>
                        </c:if>
                        <c:if test="${menu.exibir==1}">
                            <option value="1" selected="">Sim</option>
                            <option value="2">Não</option>
                        </c:if> 
                        <c:if test="${menu.exibir==2}">
                            <option value="1">Sim</option>
                            <option value="2" selected="">Não</option>
                        </c:if>    
                    </select>

                    <div class="form button form">
                        <button type="submit" class="btn btn-success">Gravar</button>
                        <a href="gerenciar_menu.do?acao=listarTodos" 
                           class="btn btn-warning">Voltar</a>
                    </div>

                </form>
            </div>  


        </div> 
        <script type="text/javascript" src="datatables/jquery.js"></script>
        <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#listarMenu").DataTable({
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


