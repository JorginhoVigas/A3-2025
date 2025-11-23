<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Relatório de Clientes</title>
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
            <h2>RELATÓRIO GERAL DE CLIENTES</h2>
            <small>APEX SISTEMAS</small>
        </div>

        <table>
            <thead>
                <tr>
                    <th style="width: 40px;">ID</th>
                    <th>Nome / Razão Social</th>
                    <th>CPF / CNPJ</th>
                    <th>Telefone</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${listaClientes}">
                    <tr>
                        <td>${c.idCliente}</td>
                        <td>${c.nome}</td>
                        <td>${c.cpfCnpj}</td>
                        <td>${c.telefone}</td>
                        <td>${c.status == 1 ? 'Ativo' : 'Inativo'}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p>Total de registros: ${listaClientes.size()}</p>
    </body>
</html>