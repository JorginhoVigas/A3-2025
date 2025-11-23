<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Relatório de Fornecedores</title>
        <style>
            body { font-family: Arial, sans-serif; font-size: 12px; margin: 20px; }
            .cabecalho { text-align: center; margin-bottom: 20px; border-bottom: 2px solid #333; padding-bottom: 10px; }
            table { width: 100%; border-collapse: collapse; }
            th, td { border: 1px solid #999; padding: 6px; text-align: left; }
            th { background-color: #f2f2f2; }
            .no-print { margin-bottom: 20px; text-align: right; }
            @media print { .no-print { display: none; } }
        </style>
    </head>
    <body>
        <div class="no-print">
            <button onclick="window.print()" style="padding: 8px 15px; cursor: pointer;">🖨️ Imprimir</button>
        </div>

        <div class="cabecalho">
            <h2>RELATÓRIO DE FORNECEDORES</h2>
        </div>

        <table>
            <thead>
                <tr>
                    <th style="width: 40px;">ID</th>
                    <th>Razão Social</th>
                    <th>Nome Fantasia</th>
                    <th>CNPJ</th>
                    <th>Telefone</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="f" items="${listaFornecedores}">
                <tr>
                    <td>${f.idFornecedor}</td>
                    <td>${f.razaoSocial}</td>
                    <td>${f.nomeFantasia}</td>
                    <td>${f.cnpj}</td>
                    <td>${f.telefone}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p>Total de registros: ${listaFornecedores.size()}</p>
</body>
</html>