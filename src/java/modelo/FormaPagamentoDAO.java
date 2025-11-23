package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FormaPagamentoDAO extends DataBaseDAO {

    public FormaPagamentoDAO() throws ClassNotFoundException {
    }

    // Lista as opções disponíveis
    public ArrayList<FormaPagamento> getLista() {
        ArrayList<FormaPagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM formas_pagamento WHERE status = 1";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    FormaPagamento fp = new FormaPagamento();
                    fp.setIdFormaPagamento(rs.getInt("id_forma_pagamento"));
                    fp.setNome(rs.getString("nome"));
                    lista.add(fp);
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

    // --- O MÉTODO QUE FALTAVA ESTÁ AQUI ---
    public FormaPagamento getCarregaPorID(int id) {
        FormaPagamento fp = new FormaPagamento();
        String sql = "SELECT * FROM formas_pagamento WHERE id_forma_pagamento = ?";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, id);
                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        fp.setIdFormaPagamento(rs.getInt("id_forma_pagamento"));
                        fp.setNome(rs.getString("nome"));
                    }
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
        return fp;
    }

    // Registra o pagamento da venda
    public boolean registrarPagamento(int idVenda, int idFormaPagamento, double valor) {
        String sql = "INSERT INTO venda_formapagamento (venda_id, forma_pagamento_id, valor_pago, data_pagamento) VALUES (?, ?, ?, NOW())";
        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idVenda);
                pstm.setInt(2, idFormaPagamento);
                pstm.setDouble(3, valor);
                pstm.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Erro ao registrar pagamento: " + e.getMessage());
            return false;
        } finally {
            try {
                this.desconectar();
            } catch (Exception e) {
            }
        }
    }
}
