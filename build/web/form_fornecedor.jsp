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
        <title>Cadastrar Fornecedor</title>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>
        <%@include file="menu.jsp" %>

        <div class="content">
            <h2>Cadastrar Fornecedor</h2>
            <div class="form-container">
                <form method="POST" action="gerenciar_fornecedor.do">
                    <legend>Formulário de Fornecedor</legend>
                    <input type="hidden" name="id_fornecedor" id="id_fornecedor" value="${fornecedor.idFornecedor}"/>

                    <label for="razao_social">Razão Social</label>
                    <input type="text" name="razao_social" id="razao_social" required=""
                           maxlength="150" value="${fornecedor.razaoSocial}"/>

                    <label for="nome_fantasia">Nome Fantasia</label>
                    <input type="text" name="nome_fantasia" id="nome_fantasia" required=""
                           maxlength="150" value="${fornecedor.nomeFantasia}"/>

                    <label for="cnpj">CNPJ</label>
                    <input type="text" name="cnpj" id="cnpj" maxlength="18" placeholder="00.000.000/0000-00"
                           value="${fornecedor.cnpj}"/>

                    <label for="inscricao_estadual">Inscrição Estadual</label>
                    <input type="text" name="inscricao_estadual" id="inscricao_estadual" maxlength="20"
                           value="${fornecedor.inscricaoEstadual}"/>

                    <label for="data_cadastro">Data de Cadastro</label>
                    <c:if test="${not empty fornecedor.dataCadastro}">
                        <fmt:formatDate value="${fornecedor.dataCadastro}" pattern="yyyy-MM-dd" var="dataFormatada"/>
                    </c:if>
                    <input type="date" name="data_cadastro" id="data_cadastro"
                           value="${dataFormatada}"/>

                    <label for="telefone">Telefone</label>
                    <input type="text" name="telefone" id="telefone" maxlength="20" placeholder="(00) 00000-0000"
                           value="${fornecedor.telefone}"/>

                    <label for="endereco_id">Endereço (ID)</label>
                    <input type="number" name="endereco_id" id="endereco_id" required=""
                           value="${fornecedor.enderecoId}" placeholder="Ex: 1"/>

                    <label for="status">Status</label>
                    <select name="status" id="status" required="">
                        <c:choose>
                            <c:when test="${fornecedor.status == 1}">
                                <option value="1" selected="">Ativo</option>
                                <option value="2">Inativo</option>
                            </c:when>
                            <c:when test="${fornecedor.status == 2}">
                                <option value="1">Ativo</option>
                                <option value="2" selected="">Inativo</option>
                            </c:when>
                            <c:otherwise>
                                <option value="0" selected="">Escolha uma opção</option>
                                <option value="1">Ativo</option>
                                <option value="2">Inativo</option>
                            </c:otherwise>
                        </c:choose>
                    </select>

                    <div class="form button form">
                        <button type="submit" class="btn btn-success">Gravar</button>
                        <a href="gerenciar_fornecedor.do?acao=listarTodos"
                           class="btn btn-warning">Voltar</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
