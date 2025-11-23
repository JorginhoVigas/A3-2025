<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/estilo.css">
        <title>Home</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>

        <%@include file="menu.jsp" %>

        <div class="content">
            <h2>Conteudo principal</h2>
        </div>  

        <script>
            function toggleMenu() {
                var menu = document.getElementById("nav-links");
                menu.classList.toggle("show");
            }
        </script>    

    </body>
</html>
