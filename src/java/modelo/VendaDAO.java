package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date; // Importante para o relatório
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendaDAO extends DataBaseDAO {

    public VendaDAO() throws ClassNotFoundException {
    }

    public boolean gravar(Venda v) {
        boolean sucesso = false;
        String sql;

        if (v.getIdVenda() == 0) {
            sql = "INSERT INTO venda (usuario_id, cliente_id, status, valor_total, desconto, observacao, data_venda) "
                    + "VALUES (?, ?, ?, ?, ?, ?, NOW())";
        } else {
            sql = "UPDATE venda SET usuario_id=?, cliente_id=?, status=?, valor_total=?, desconto=?, observacao=?, data_venda=? "
                    + "WHERE id_venda=?";
        }

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstm.setInt(1, v.getUsuario().getIdUsuario());
                pstm.setInt(2, v.getCliente().getIdCliente());
                pstm.setInt(3, v.getStatus());
                pstm.setDouble(4, v.getValorTotal());
                pstm.setDouble(5, v.getDesconto());
                pstm.setString(6, v.getObservacao());

                if (v.getIdVenda() == 0) {
                    // --- É UMA NOVA VENDA (INSERT) ---
                    int rowsAffected = pstm.executeUpdate();
                    if (rowsAffected > 0) {
                        try (ResultSet rs = pstm.getGeneratedKeys()) {
                            if (rs.next()) {
                                v.setIdVenda(rs.getInt(1));
                            }
                        }
                        sucesso = true;
                    }
                } else {
                    // --- É UMA ATUALIZAÇÃO (UPDATE) ---
                    Timestamp dataVenda = v.getDataVenda();
                    if (dataVenda == null) {
                        dataVenda = new Timestamp(System.currentTimeMillis());
                    }
                    pstm.setTimestamp(7, dataVenda);
                    pstm.setInt(8, v.getIdVenda());

                    int rowsAffected = pstm.executeUpdate();
                    if (rowsAffected > 0) {
                        sucesso = true;

                        // Se o status for 1 (Realizada), baixamos o estoque!
                        if (v.getStatus() == 1) {
                            baixarEstoque(v.getIdVenda());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return sucesso;
    }

    // --- MÉTODO GETLISTA (MODO SEGURO) ---
    public ArrayList<Venda> getLista() {
        ArrayList<Venda> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda ORDER BY data_venda DESC";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery()) {

                while (rs.next()) {
                    Venda v = new Venda();
                    v.setIdVenda(rs.getInt("id_venda"));
                    v.setStatus(rs.getInt("status"));
                    v.setValorTotal(rs.getDouble("valor_total"));
                    v.setDesconto(rs.getDouble("desconto"));
                    v.setObservacao(rs.getString("observacao"));
                    v.setDataVenda(rs.getTimestamp("data_venda"));

                    modelo.Cliente c = new modelo.Cliente();
                    c.setIdCliente(rs.getInt("cliente_id"));
                    v.setCliente(c);

                    modelo.Usuario u = new modelo.Usuario();
                    u.setIdUsuario(rs.getInt("usuario_id"));
                    v.setUsuario(u);

                    lista.add(v);
                }
            }
            this.desconectar();

            // AGORA PREENCHEMOS OS DADOS COMPLETOS
            ClienteDAO cDAO = new ClienteDAO();
            UsuarioDAO uDAO = new UsuarioDAO();

            for (Venda v : lista) {
                try {
                    modelo.Cliente cCompleto = cDAO.getCarregaPorID(v.getCliente().getIdCliente());
                    if (cCompleto != null) {
                        v.setCliente(cCompleto);
                    } else {
                        v.getCliente().setNome("Cliente não encontrado / Inativo");
                    }
                } catch (Exception e) {
                    v.getCliente().setNome("Erro ao carregar");
                }

                try {
                    modelo.Usuario uCompleto = uDAO.getCarregaPorID(v.getUsuario().getIdUsuario());
                    if (uCompleto != null) {
                        v.setUsuario(uCompleto);
                    } else {
                        v.getUsuario().setNome("Usuário não encontrado");
                    }
                } catch (Exception e) {
                    v.getUsuario().setNome("Erro ao carregar");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.desconectar();
            } catch (Exception e) {
            }
        }

        return lista;
    }

    // --- MÉTODO GETCARREGAPORID ---
    public Venda getCarregaPorID(int idVenda) {
        Venda v = null;
        String sql = "SELECT * FROM venda WHERE id_venda = ?";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idVenda);
                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        v = new Venda();
                        v.setIdVenda(rs.getInt("id_venda"));
                        v.setStatus(rs.getInt("status"));
                        v.setValorTotal(rs.getDouble("valor_total"));
                        v.setDesconto(rs.getDouble("desconto"));
                        v.setObservacao(rs.getString("observacao"));
                        v.setDataVenda(rs.getTimestamp("data_venda"));

                        modelo.Cliente c = new modelo.Cliente();
                        c.setIdCliente(rs.getInt("cliente_id"));
                        v.setCliente(c);

                        modelo.Usuario u = new modelo.Usuario();
                        u.setIdUsuario(rs.getInt("usuario_id"));
                        v.setUsuario(u);
                    }
                }
            }
            this.desconectar();

            if (v != null) {
                ClienteDAO cDAO = new ClienteDAO();
                UsuarioDAO uDAO = new UsuarioDAO();

                try {
                    modelo.Cliente c = cDAO.getCarregaPorID(v.getCliente().getIdCliente());
                    if (c != null) {
                        v.setCliente(c);
                    }
                } catch (Exception e) {
                }

                try {
                    modelo.Usuario u = uDAO.getCarregaPorID(v.getUsuario().getIdUsuario());
                    if (u != null) {
                        v.setUsuario(u);
                    }
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.desconectar();
            } catch (Exception e) {
            }
        }

        return v;
    }

    public boolean desativar(Venda v) {
        boolean sucesso = false;
        String sql = "UPDATE venda SET status = 2 WHERE id_venda = ?";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, v.getIdVenda());
                int rowsAffected = pstm.executeUpdate();
                if (rowsAffected > 0) {
                    sucesso = true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return sucesso;
    }

    public void atualizarTotalVenda(int idVenda) {
        try {
            this.conectar();
            // Verificando nome da tabela de itens (ajustar se for produto_venda)
            String sqlSoma = "SELECT SUM(valor_total) as total FROM venda_produto WHERE venda_id = ?";
            double total = 0.0;

            try (PreparedStatement ps = conn.prepareStatement(sqlSoma)) {
                ps.setInt(1, idVenda);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        total = rs.getDouble("total");
                    }
                }
            }

            String sqlUpdate = "UPDATE venda SET valor_total = ? WHERE id_venda = ?";
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.setDouble(1, total);
                psUpdate.setInt(2, idVenda);
                psUpdate.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("Erro ao atualizar total: " + e);
        } finally {
            try {
                this.desconectar();
            } catch (Exception e) {
            }
        }
    }

    public boolean excluir(int idVenda) {
        try {
            this.conectar();

            // Tenta deletar itens (assume venda_produto)
            String sqlItens = "DELETE FROM venda_produto WHERE venda_id = ?";
            try (PreparedStatement psItens = conn.prepareStatement(sqlItens)) {
                psItens.setInt(1, idVenda);
                psItens.executeUpdate();
            } catch (SQLException e) {
                // Fallback se o nome for produto_venda
                String sqlItens2 = "DELETE FROM produto_venda WHERE venda_id = ?";
                try (PreparedStatement psItens2 = conn.prepareStatement(sqlItens2)) {
                    psItens2.setInt(1, idVenda);
                    psItens2.executeUpdate();
                }
            }

            String sqlVenda = "DELETE FROM venda WHERE id_venda = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlVenda)) {
                ps.setInt(1, idVenda);
                ps.execute();
            }

            return true;

        } catch (Exception e) {
            System.out.println("ERRO AO EXCLUIR: " + e.getMessage());
            return false;
        } finally {
            try {
                this.desconectar();
            } catch (Exception e) {
            }
        }
    }

    private void baixarEstoque(int idVenda) {
        String sqlBuscaItens = "SELECT produto_id, quantidade FROM venda_produto WHERE venda_id = ?";
        String sqlBaixaEstoque = "UPDATE produtos SET quantidade_estoque = quantidade_estoque - ? WHERE id_produto = ?";

        try {
            try (PreparedStatement psBusca = conn.prepareStatement(sqlBuscaItens)) {
                psBusca.setInt(1, idVenda);

                try (ResultSet rs = psBusca.executeQuery()) {
                    while (rs.next()) {
                        int idProduto = rs.getInt("produto_id");
                        int qtdVendida = rs.getInt("quantidade");

                        try (PreparedStatement psBaixa = conn.prepareStatement(sqlBaixaEstoque)) {
                            psBaixa.setInt(1, qtdVendida);
                            psBaixa.setInt(2, idProduto);
                            psBaixa.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao baixar estoque: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =========================================================================
    // --- NOVO MÉTODO PARA RELATÓRIO (Era esse que faltava!) ---
    // =========================================================================
    public ArrayList<Venda> listarVendasPorPeriodo(Date dataInicio, Date dataFim) {
        ArrayList<Venda> lista = new ArrayList<>();
        // O CAST(... AS DATE) garante que pegamos o dia inteiro, ignorando horas/minutos
        String sql = "SELECT * FROM venda WHERE CAST(data_venda AS DATE) BETWEEN ? AND ? ORDER BY data_venda DESC";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setDate(1, dataInicio);
                pstm.setDate(2, dataFim);

                try (ResultSet rs = pstm.executeQuery()) {
                    while (rs.next()) {
                        Venda v = new Venda();
                        v.setIdVenda(rs.getInt("id_venda"));
                        v.setDataVenda(rs.getTimestamp("data_venda"));
                        v.setValorTotal(rs.getDouble("valor_total"));
                        v.setStatus(rs.getInt("status"));

                        // Preenche Cliente (Só ID e Nome para o relatório ficar leve)
                        modelo.Cliente c = new modelo.Cliente();
                        c.setIdCliente(rs.getInt("cliente_id"));
                        v.setCliente(c);

                        // Preenche Usuário
                        modelo.Usuario u = new modelo.Usuario();
                        u.setIdUsuario(rs.getInt("usuario_id"));
                        v.setUsuario(u);

                        lista.add(v);
                    }
                }
            }
            this.desconectar();

            // Hidrata os nomes dos clientes
            ClienteDAO cDAO = new ClienteDAO();
            for (Venda v : lista) {
                try {
                    modelo.Cliente c = cDAO.getCarregaPorID(v.getCliente().getIdCliente());
                    v.setCliente(c != null ? c : new modelo.Cliente(0, "Cliente Excluído", "", 0, "", 0));
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.desconectar();
            } catch (Exception e) {
            }
        }
        return lista;
    }
}
