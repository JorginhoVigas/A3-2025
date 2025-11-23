<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

        <link rel="stylesheet" href="css/estilo.css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"/>

        <title>Gerenciar Venda</title>
        <style>
            .box-produtos {
                background-color: #f9f9f9;
                border: 1px solid #ddd;
                padding: 15px;
                border-radius: 5px;
                margin-top: 20px;
            }
            .status-badge {
                font-size: 0.6em;
                padding: 5px 10px;
                border-radius: 4px;
                vertical-align: middle;
                color: white;
            }
            .status-pendente { background-color: #f39c12; }  /* Laranja */
            .status-realizada { background-color: #27ae60; } /* Verde */
            .status-cancelada { background-color: #c0392b; } /* Vermelho */
        </style>

        <script>
            // Função para SALVAR alterações sem mudar status (mantém pendente)
            function salvarRascunho() {
                document.getElementById('input_status').value = '0'; // Garante 0 = Pendente
                document.getElementById('formVenda').submit();
            }

            // Função para CANCELAR a venda
            function cancelarVenda() {
                if (confirm('Tem certeza que deseja cancelar esta venda?')) {
                    document.getElementById('input_status').value = '2'; // 2 = Cancelada
                    document.getElementById('formVenda').submit();
                }
            }

            // Função para IR AO PAGAMENTO (Redireciona, NÃO submete este form)
            function irParaPagamento() {
                // Redireciona via GET para a ação 'verCarrinho' no Controller
                window.location.href = "gerenciar_venda.do?acao=verCarrinho&id_venda=${venda.idVenda}";
            }
        </script>
    </head>
    <body>
        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>
        <%@include file="menu.jsp" %>

        <div class="content container">
            <h2>
                <c:if test="${empty venda.idVenda or venda.idVenda == 0}">Nova Venda</c:if>
                <c:if test="${not empty venda.idVenda and venda.idVenda > 0}">
                    Venda #${venda.idVenda} 
                    <span class="status-badge ${venda.status == 1 ? 'status-realizada' : (venda.status == 2 ? 'status-cancelada' : 'status-pendente')}">
                        <c:choose>
                            <c:when test="${venda.status == 1}">REALIZADA</c:when>
                            <c:when test="${venda.status == 2}">CANCELADA</c:when>
                            <c:otherwise>PENDENTE</c:otherwise>
                        </c:choose>
                    </span>
                </c:if>
            </h2>

            <div class="form-container">
                <form id="formVenda" method="POST" action="gerenciar_venda.do" accept-charset="ISO-8859-1">
                    <input type="hidden" name="acao" value="gravar"/>
                    <input type="hidden" name="id_venda" value="${venda.idVenda}"/>

                    <!-- Status padrão é o que veio do banco, ou 0 se for nova -->
                    <!-- IMPORTANTE: O valor aqui só muda se o JS alterar antes do submit -->
                    <input type="hidden" id="input_status" name="status" value="${empty venda.status ? 0 : venda.status}"/>

                    <legend>Dados da Venda</legend>

                    <!-- ... (SEUS CAMPOS DE CLIENTE E USUÁRIO IGUAIS AO ANTERIOR) ... -->
                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label for="usuario_id">Vendedor / Atendente</label>
                            <select name="usuario_id" id="usuario_id" class="form-control" required 
                                    ${venda.status == 1 or venda.status == 2 ? 'disabled' : ''}>
                                <option value="">Selecione...</option>
                                <c:forEach var="u" items="${listaUsuarios}">
                                    <option value="${u.idUsuario}" 
                                            ${venda.usuario.idUsuario eq u.idUsuario ? 'selected' : ''}>
                                        ${u.nome}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-6 form-group">
                            <label for="cliente_id">Cliente</label>
                            <select name="cliente_id" id="cliente_id" class="form-control" required
                                    ${venda.status == 1 or venda.status == 2 ? 'disabled' : ''}>
                                <option value="">Selecione...</option>
                                <c:forEach var="c" items="${listaClientes}">
                                    <option value="${c.idCliente}" 
                                            ${venda.cliente.idCliente eq c.idCliente ? 'selected' : ''}>
                                        ${c.nome} (Doc: ${c.cpfCnpj})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-4 form-group">
                            <label>Data da Venda</label>
                            <input type="text" class="form-control" readonly
                                   value="<fmt:formatDate value='${venda.dataVenda}' pattern='dd/MM/yyyy'/>" 
                                   placeholder="Data atual"/>
                        </div>

                        <div class="col-md-4 form-group">
                            <label for="valor_total">Total (R$)</label>
                            <fmt:formatNumber value="${venda.valorTotal}" pattern="0.00" var="totalFormatado"/>
                            <input type="text" class="form-control" readonly 
                                   value="${totalFormatado}" style="font-weight: bold; background-color: #e9ecef;"/> 
                        </div>

                        <div class="col-md-4 form-group">
                            <label for="desconto">Desconto (R$)</label>
                            <fmt:formatNumber value="${venda.desconto}" pattern="0.00" var="descontoFormatado"/>
                            <input type="number" step="0.01" name="desconto" id="desconto" 
                                   value="${descontoFormatado.replace(',','.')}" 
                                   class="form-control"
                                   ${venda.status == 1 or venda.status == 2 ? 'readonly' : ''}/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="observacao">Observação</label>
                        <textarea name="observacao" id="observacao" rows="2" class="form-control"
                                  ${venda.status == 1 or venda.status == 2 ? 'readonly' : ''}>${venda.observacao}</textarea>
                    </div>

                    <!-- ÁREA DE PRODUTOS -->
                    <c:if test="${venda.idVenda > 0}">
                        <div class="box-produtos">
                            <h4>📦 Itens da Venda</h4>

                            <c:choose>
                                <c:when test="${not empty listarVendaProdutos}">
                                    <table class="table table-condensed table-bordered" style="background: #fff;">
                                        <thead>
                                            <tr class="active">
                                                <th>Produto</th>
                                                <th class="text-center">Qtd</th>
                                                <th class="text-right">Total Item</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="vp" items="${listarVendaProdutos}">
                                                <tr>
                                                    <td>${vp.produto.descricao}</td>
                                                    <td class="text-center">${vp.quantidade}</td>
                                                    <td class="text-right">R$ <fmt:formatNumber value="${vp.valorTotal}" pattern="#,##0.00"/></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <p class="text-muted">Nenhum produto adicionado ainda.</p>
                                </c:otherwise>
                            </c:choose>

                            <div class="text-right">
                                <!-- Só habilita edição de produtos se estiver PENDENTE (0) -->
                                <c:if test="${venda.status == 0}">
                                    <a href="gerenciar_venda_produto.do?acao=listar&id_venda=${venda.idVenda}" 
                                       class="btn btn-info btn-sm">
                                        <i class="glyphicon glyphicon-pencil"></i> Gerenciar Produtos
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </c:if>

                    <hr/>

                    <!-- BOTÕES DE AÇÃO CORRIGIDOS -->
                    <div class="form-group text-center" style="margin-top: 30px; margin-bottom: 30px;">

                        <!-- CENÁRIO 1: NOVA VENDA -->
                        <c:if test="${empty venda.idVenda or venda.idVenda == 0}">
                            <!-- Botão Iniciar: Submete o form normal, status será 0 -->
                            <button type="button" onclick="salvarRascunho()" class="btn btn-primary btn-lg">
                                Iniciar Venda
                            </button>
                        </c:if>

                        <!-- CENÁRIO 2: VENDA EXISTENTE -->
                        <c:if test="${venda.idVenda > 0}">

                            <!-- Se estiver PENDENTE (0) -->
                            <c:if test="${venda.status == 0}">
                                <!-- Botão Salvar: Apenas atualiza dados básicos (cliente, obs, desconto) -->
                                <button type="button" onclick="salvarRascunho()" class="btn btn-default">
                                    Salvar Alterações
                                </button>

                                <!-- CORREÇÃO: Botão Ir para Pagamento chama JS que Redireciona (NÃO submete) -->
                                <button type="button" onclick="irParaPagamento()" class="btn btn-success btn-lg" style="margin-left: 10px;">
                                    <i class="glyphicon glyphicon-credit-card"></i> Ir para Pagamento
                                </button>

                                <button type="button" onclick="cancelarVenda()" class="btn btn-danger" style="margin-left: 10px;">
                                    Cancelar
                                </button>
                            </c:if>

                            <!-- Se estiver REALIZADA ou CANCELADA -->
                            <c:if test="${venda.status != 0}">
                                <div class="alert alert-warning text-center">
                                    Esta venda já está finalizada e não pode ser alterada.
                                </div>
                            </c:if>

                        </c:if>

                        <br><br>
                        <a href="gerenciar_venda.do?acao=listarTodos" class="btn btn-link">
                            Voltar para Lista de Vendas
                        </a>
                    </div>

                </form>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>