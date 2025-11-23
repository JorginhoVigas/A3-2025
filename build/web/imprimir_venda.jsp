<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Venda"%>
<%@page import="modelo.VendaDAO"%>
<%@page import="modelo.VendaProduto"%>
<%@page import="modelo.VendaProdutoDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Imprimir Venda</title>
        <style>
            body {
                font-family: 'Courier New', Courier, monospace;
                font-size: 14px;
                max-width: 800px;
                margin: 0 auto;
                padding: 20px;
            }
            .header {
                text-align: center;
                border-bottom: 2px solid #000;
                padding-bottom: 10px;
                margin-bottom: 20px;
            }
            .info-empresa h1 { margin: 0; font-size: 24px; }
            .info-empresa p { margin: 2px 0; }
            .dados-venda {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
                border: 1px solid #ddd;
                padding: 10px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }
            th, td {
                border: 1px solid #000;
                padding: 8px;
                text-align: left;
            }
            th { background-color: #f2f2f2; }
            .total-box {
                text-align: right;
                font-size: 18px;
                font-weight: bold;
                border-top: 2px solid #000;
                padding-top: 10px;
            }
            .btn-print {
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                border: none;
                cursor: pointer;
                font-size: 16px;
                margin-bottom: 20px;
                display: block;
            }
            @media print {
                .btn-print { display: none; }
                body { padding: 0; margin: 0; }
            }
        </style>
    </head>
    <body>

        <%
            // 1. PEGAR O ID DA URL
            String idStr = request.getParameter("idvenda");
            int idVenda = 0;

            if (idStr != null && !idStr.isEmpty()) {
                try {
                    idVenda = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    out.print("<h3>Erro: ID inválido.</h3>");
                    return;
                }
            } else {
                out.print("<h3>Erro: Venda não identificada (ID nulo).</h3>");
                return;
            }

            // 2. CARREGAR A VENDA
            VendaDAO vdao = new VendaDAO();
            Venda venda = vdao.getCarregaPorID(idVenda);

            if (venda == null) {
                out.print("<h3>Erro: Venda #" + idVenda + " não encontrada no banco.</h3>");
                return;
            }

            // 3. CARREGAR OS ITENS
            VendaProdutoDAO pdao = new VendaProdutoDAO();
            ArrayList<VendaProduto> listaItens = pdao.getListaPorVenda(idVenda);

            // Formatadores
            DecimalFormat df = new DecimalFormat("R$ #,##0.00");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        %>

        <!-- Botão de imprimir -->
        <button class="btn-print" onclick="window.print()">🖨️ Imprimir Agora</button>

        <div class="header">
            <div class="info-empresa">
                <h1>APEX SISTEMAS</h1>
                <p>Rua da Tecnologia, 123 - Centro</p>
                <p>CNPJ: 00.000.000/0001-99</p>
            </div>
        </div>

        <div class="dados-venda">
            <div class="cliente">
                <strong>DADOS DO CLIENTE:</strong><br>
                <!-- Verifica se cliente existe para não dar erro -->
                Nome: <%= (venda.getCliente() != null) ? venda.getCliente().getNome() : "---"%><br>
                <!-- CORREÇÃO: Usando getCpfCnpj() -->
                CPF/CNPJ: <%= (venda.getCliente() != null) ? venda.getCliente().getCpfCnpj() : "---"%>
            </div>
            <div class="detalhes">
                <strong>DETALHES DA VENDA:</strong><br>
                Número: <strong><%= venda.getIdVenda()%></strong><br>
                Data: <%= (venda.getDataVenda() != null) ? sdf.format(venda.getDataVenda()) : "---"%>
            </div>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Produto</th>
                    <th style="width: 80px; text-align: center;">Qtd</th>
                    <th style="width: 100px; text-align: right;">V. Unit</th>
                    <th style="width: 120px; text-align: right;">Total Item</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (listaItens != null && !listaItens.isEmpty()) {
                        for (VendaProduto item : listaItens) {
                %>
                <tr>
                    <!-- CORREÇÃO: Usando getDescricao() -->
                    <td><%= (item.getProduto() != null) ? item.getProduto().getDescricao() : "Prod. ID " + item.getProduto().getIdProduto()%></td>

                    <td style="text-align: center;"><%= item.getQuantidade()%></td>

                    <td style="text-align: right;"><%= df.format(item.getValorUnitario())%></td>

                    <td style="text-align: right;"><%= df.format(item.getValorTotal())%></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="4" style="text-align: center;">Nenhum item encontrado para esta venda.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <div class="total-box">
            TOTAL A PAGAR: <%= df.format(venda.getValorTotal())%>
        </div>

        <div style="margin-top: 50px; text-align: center; border-top: 1px dashed #000; padding-top: 10px; font-size: 12px;">
            Obrigado pela preferência!
        </div>

    </body>
</html>