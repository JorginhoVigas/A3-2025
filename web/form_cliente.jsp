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
        <title>Cadastrar Cliente</title>
        <script>
            function toggleDocumento() {
                var tipo = document.getElementById("tipo_documento").value;
                var label = document.getElementById("labelDoc");
                var input = document.getElementById("cpf_cnpj");
                if (tipo == "1") {
                    label.innerText = "CPF:";
                    input.placeholder = "000.000.000-00";
                } else if (tipo == "2") {
                    label.innerText = "CNPJ:";
                    input.placeholder = "00.000.000/0000-00";
                } else {
                    label.innerText = "CPF/CNPJ:";
                    input.placeholder = "";
                }
            }
        </script>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>
        <%@include file="menu.jsp" %>

        <div class="content">
            <h2>Cadastrar Cliente</h2>
            <div class="form-container">
                <form method="POST" action="gerenciar_cliente.do">
                    <legend>Formulário de Cliente</legend>

                    <input type="hidden" name="idCliente" id="idCliente" value="${cliente.idCliente}"/>

                    <label for="nome">Nome / Razão Social</label>

                    <input type="text" name="nome" id="nome" required=""
                           maxlength="100" value="${cliente.nome}"/>

                    <label for="telefone">Telefone</label>
                    <input type="text" name="telefone" id="telefone" required=""
                           maxlength="20" value="${cliente.telefone}"/>

                    <label for="tipo_documento">Tipo de Documento</label>
                    <select name="tipo_documento" id="tipo_documento" required="" onchange="toggleDocumento()">
                        <option value="">Selecione</option>
                        <option value="1" <c:if test="${cliente.tipoDocumento==1}">selected</c:if>>CPF</option>
                        <option value="2" <c:if test="${cliente.tipoDocumento==2}">selected</c:if>>CNPJ</option>
                        </select>

                        <label id="labelDoc" for="cpf_cnpj">CPF / CNPJ</label>
                        <input type="text" name="cpf_cnpj" id="cpf_cnpj" maxlength="20"
                               value="${cliente.cpfCnpj}" placeholder="Informe o documento"/>

                    <label for="status">Status</label>
                    <select name="status" required="">
                        <c:if test="${cliente.status==null}">
                            <option value="0">Escolha uma opção</option>
                            <option value="1">Ativo</option>
                            <option value="2">Inativo</option>
                        </c:if>
                        <c:if test="${cliente.status==1}">
                            <option value="1" selected="">Ativo</option>
                            <option value="2">Inativo</option>
                        </c:if>
                        <c:if test="${cliente.status==2}">
                            <option value="1">Ativo</option>
                            <option value="2" selected="">Inativo</option>
                        </c:if>
                    </select>

                    <div class="form button form">
                        <button type="submit" class="btn btn-success">Gravar</button>
                        <a href="gerenciar_cliente.do?acao=listarTodos"
                           class="btn btn-warning">Voltar</a>
                    </div>
                </form>
            </div>  
        </div> 
    </body>
</html>
