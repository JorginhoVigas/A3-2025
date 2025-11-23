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
        <title>Login</title>
    </head>
    <body>

        <div class="content">
            <%
                String mensagem = (String) request.getSession().getAttribute("mensagem");
                if (mensagem != null) {
            %>
            <div class="alert alert-info"><%=mensagem%></div>
            <%
                    request.getSession().removeAttribute("mensagem");
                }
            %>    

            <div class="form-container">
                <form method="POST" action="gerenciar_login.do">
                    <legend>Formulário de Login</legend>

                    <label for="login">Login</label>
                    <input type="text" name="login" id="login" required="" 
                           maxlength="100" value=""/>

                    <label for="senha">Senha</label>
                    <input type="password" name="senha" id="senha" required=""
                           maxlength="100" value=""/>

                    <div class="form button form">
                        <button type="submit" class="btn btn-success">Acessar</button>

                    </div>

                </form>
            </div>  


        </div> 

    </body>
</html>



