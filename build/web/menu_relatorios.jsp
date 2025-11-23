<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Central de Relatórios</title>

        <link rel="stylesheet" href="css/estilo.css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
        <style>
            .panel-report { cursor: pointer; transition: 0.3s; }
            .panel-report:hover { transform: scale(1.02); box-shadow: 0 5px 15px rgba(0,0,0,0.1); }
            .icon-report { font-size: 40px; margin-bottom: 10px; display: block; text-align: center; }
        </style>
    </head>
    <body>

        <div class="banner">
            <%@include file="banner.jsp" %>
        </div>
        <%@include file="menu.jsp" %>

        <div class="container" style="margin-top: 30px;">
            <h2 class="text-center mb-4">Central de Relatórios</h2>
            <hr>

            <div class="row">
                <!-- BLOCO VENDAS (Com filtro de data) -->
                <div class="col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="glyphicon glyphicon-shopping-cart"></i> Relatório de Vendas</h3>
                        </div>
                        <div class="panel-body">
                            <form action="gerenciar_relatorio.do" method="POST">
                                <input type="hidden" name="acao" value="vendas_por_periodo"/>
                                <div class="row">
                                    <div class="col-xs-6">
                                        <label>De:</label>
                                        <input type="date" name="data_inicio" class="form-control" required/>
                                    </div>
                                    <div class="col-xs-6">
                                        <label>Até:</label>
                                        <input type="date" name="data_fim" class="form-control" required/>
                                    </div>
                                </div>
                                <br>
                                <button type="submit" class="btn btn-primary btn-block">
                                    <i class="glyphicon glyphicon-print"></i> Gerar Relatório de Vendas
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- BLOCO CADASTROS GERAIS -->
                <div class="col-md-6">
                    <div class="list-group">
                        <a href="gerenciar_relatorio.do?acao=clientes_geral" target="_blank" class="list-group-item list-group-item-info panel-report">
                            <h4 class="list-group-item-heading"><i class="glyphicon glyphicon-user"></i> Relatório de Clientes</h4>
                            <p class="list-group-item-text">Lista geral de todos os clientes ativos.</p>
                        </a>

                        <a href="gerenciar_relatorio.do?acao=produtos_geral" target="_blank" class="list-group-item list-group-item-warning panel-report" style="margin-top: 10px;">
                            <h4 class="list-group-item-heading"><i class="glyphicon glyphicon-tag"></i> Relatório de Produtos</h4>
                            <p class="list-group-item-text">Lista geral de estoque e preços.</p>
                        </a>

                        <a href="gerenciar_relatorio.do?acao=fornecedores_geral" target="_blank" class="list-group-item list-group-item-success panel-report" style="margin-top: 10px;">
                            <h4 class="list-group-item-heading"><i class="glyphicon glyphicon-briefcase"></i> Relatório de Fornecedores</h4>
                            <p class="list-group-item-text">Lista de contato dos fornecedores cadastrados.</p>
                        </a>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>