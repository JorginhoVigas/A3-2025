package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Cliente;
import modelo.ClienteDAO;

public class GerenciarCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idClienteStr = request.getParameter("idCliente");
        Cliente c = new Cliente();
        try {
            ClienteDAO cDAO = new ClienteDAO();
            int idCliente = 0;
            if (acao.equals("alterar")) {
                // CORREÇÃO: Usando verificarAcesso != null
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    try {
                        idCliente = Integer.parseInt(idClienteStr);
                        c = cDAO.getCarregaPorID(idCliente);
                        if (c.getIdCliente() > 0) {
                            RequestDispatcher disp = getServletContext().getRequestDispatcher("/form_cliente.jsp");
                            request.setAttribute("cliente", c);
                            disp.forward(request, response);
                        } else {
                            exibirMensagem("Cliente não encontrado", false, "", response);
                        }

                    } catch (NumberFormatException e) {
                        exibirMensagem("ID de cliente inválido", false, "", response);
                    }
                }
            } else if (acao.equals("desativar")) {
                // CORREÇÃO: Usando verificarAcesso != null
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    try {
                        idCliente = Integer.parseInt(idClienteStr);
                        if (idCliente != 0) {
                            c.setIdCliente(idCliente);
                            if (cDAO.desativar(c)) {
                                exibirMensagem("Cliente desativado com sucesso", true, "gerenciar_cliente.do?acao=listarTodos", response);
                            } else {
                                exibirMensagem("Erro ao desativar", false, "", response);
                            }
                        }
                    } catch (NumberFormatException e) {
                        exibirMensagem("ID de cliente inválido", false, "", response);
                    }
                }
            } else if (acao.equals("listarTodos")) {
                // CORREÇÃO: Usando verificarAcesso != null
                if (GerenciarLogin.verificarAcesso(request, response) != null) {
                    ArrayList<Cliente> listarTodos = new ArrayList<>();
                    listarTodos = cDAO.getLista();

                    RequestDispatcher disp = getServletContext().getRequestDispatcher("/listar_cliente.jsp");
                    request.setAttribute("listarTodos", listarTodos);
                    disp.forward(request, response);
                }
            }

        } catch (Exception e) {
            out.println(e);
            exibirMensagem("Erro ao acessar o banco", false, "", response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        ArrayList<String> erros = new ArrayList<String>();

        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");
        String tipoDocumentoStr = request.getParameter("tipo_documento");
        String cpfCnpj = request.getParameter("cpf_cnpj");
        String statusStr = request.getParameter("status");
        String idClienteStr = request.getParameter("idCliente");

        try {
            int status = 0;
            int idCliente = 0;
            int tipoDocumento = 0;

            if (idClienteStr != null && !idClienteStr.isEmpty()) {
                try {
                    idCliente = Integer.parseInt(idClienteStr);
                } catch (NumberFormatException e) {
                    erros.add("ID de cliente inválido");
                }
            }
            if (statusStr != null && !statusStr.isEmpty()) {
                try {
                    status = Integer.parseInt(statusStr);
                } catch (NumberFormatException e) {
                    erros.add("Status do cliente inválido");
                }
            }
            if (tipoDocumentoStr != null && !tipoDocumentoStr.isEmpty()) {
                try {
                    tipoDocumento = Integer.parseInt(tipoDocumentoStr);
                } catch (NumberFormatException e) {
                    erros.add("Tipo de documento inválido");
                }
            }

            if (nome == null || nome.trim().isEmpty()) {
                erros.add("O campo nome é obrigatório");
            }

            if (telefone == null || telefone.trim().isEmpty()) {
                erros.add("O campo telefone é obrigatório");
            }

            if (tipoDocumento == 0) {
                erros.add("O campo tipo de documento é obrigatório");
            }

            if (cpfCnpj == null || cpfCnpj.trim().isEmpty()) {
                erros.add("O campo CPF/CNPJ é obrigatório");
            }

            if (erros.size() > 0) {
                String campos = "";
                for (String erro : erros) {
                    campos += "\\n - " + erro;
                }
                exibirMensagem("Preencha o(s) campo(s):" + campos, false, "", response);
            } else {
                Cliente c = new Cliente();
                c.setIdCliente(idCliente);
                c.setNome(nome);
                c.setTelefone(telefone);
                c.setTipoDocumento(tipoDocumento);
                c.setCpfCnpj(cpfCnpj);
                c.setStatus(status);

                ClienteDAO dao = new ClienteDAO();
                boolean sucesso = dao.gravar(c);

                if (sucesso) {
                    exibirMensagem("Cliente gravado com sucesso", true, "gerenciar_cliente.do?acao=listarTodos", response);
                } else {
                    exibirMensagem("Erro ao gravar os dados do cliente", false, "", response);
                }
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GerenciarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void exibirMensagem(String mensagem, boolean resposta, String link, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('" + mensagem + "')");
            if (resposta) {
                out.println("location.href='" + link + "'");
            } else {
                out.println("history.back();");
            }
            out.println("</script>");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Gerenciar Cliente";
    }
}
