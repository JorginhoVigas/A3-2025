<%@page import="modelo.Usuario"%>
<%@page import="controller.GerenciarLogin"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Usuario ulogado = GerenciarLogin.verificarAcesso(request, response);
    request.setAttribute("ulogado", ulogado);
%>
<nav>

    <div class="menu-icon" onclick="toggleMenu()">&#9776</div>
    <ul id="nav-links">

        <!-- 1. HOME FIXA EM PRIMEIRO LUGAR -->
        <li><a href="index.jsp">Home</a></li>

        <c:if test="${ulogado!=null && ulogado.perfil!=null}">
            <c:forEach var="menu" items="${ulogado.perfil.menus.menusVinculados}">

                <!-- 2. EXIBE OS OUTROS, MAS PULA A HOME (PRA NÃO DUPLICAR) -->
                <c:if test="${menu.exibir==1 && menu.link != 'index.jsp'}">
                    <li><a href="${menu.link}">${menu.nome}</a></li>
                    </c:if>

            </c:forEach>
        </c:if>

    </ul>

</nav>
<div class="pull-right">
    Bem Vindo, <c:if test="${ulogado!=null}">${ulogado.nome}</c:if>
    <a href="gerenciar_login.do">Sair</a>
</div>