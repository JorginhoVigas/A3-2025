<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Relatório de Produtos</title>
        <style>
            body { font-family: Arial, sans-serif; font-size: 12px; margin: 20px; }
            .cabecalho { text-align: center; margin-bottom: 20px; border-bottom: 2px solid #333; padding-bottom: 10px; }
            table { width: 100%; border-collapse: collapse; }
            th, td { border: 1px solid #999; padding: 6px; text-align: left; }
            th { background-color: #f2f2f2; }
            .num { text-align: right; }
            .no-print { margin-bottom: 20px; text-align: right; }
            @media print { .no-print { display: none; } }
        </style>
    </head>
    <body>
        <div class="no-print">
            <button onclick="window.print()" style="padding: 8px 15px; cursor: pointer;">🖨️ Imprimir</button>
        </div>

        <div class="cabecalho">
            <h2>RELATÓRIO DE ESTOQUE DE PRODUTOS</h2>
        </div>

        <table>
            <thead>
                <tr>
                    <th style="width: 40px;">ID</th>
                    <th>Descrição</th>
                    <th>Marca</th>
                    <th>Un.</th>
                    <th class="num">Estoque</th>
                    <th class="num">Preço Venda</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${listaProdutos}">
                    <tr>
                        <td>${p.idProduto}</td>
                        <td>${p.descricao}</td>
                        <td>${p.marca}</td>
                        <td>${p.unidadeMedida}</td>
                        <!-- Destaque em vermelho se estoque estiver baixo -->
                        <td class="num" style="${p.quantidadeEstoque <= p.estoqueMinimo ? 'color:red; font-weight:bold;' : ''}">
                            ${p.quantidadeEstoque}
                        </td>
                        <td class="num">
                            <fmt:formatNumber value="${p.valorVenda}" type="currency"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p>Total de registros: ${listaProdutos.size()}</p>
    </body>
</html>