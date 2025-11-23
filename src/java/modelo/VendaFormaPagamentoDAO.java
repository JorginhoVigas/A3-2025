package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendaFormaPagamentoDAO extends DataBaseDAO {

    public VendaFormaPagamentoDAO() throws ClassNotFoundException {
    }

    public ArrayList<VendaFormaPagamento> getLista() {
        ArrayList<VendaFormaPagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda_formapagamento";

        try {
            this.conectar();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            VendaDAO vDAO = new VendaDAO();
            FormaPagamentoDAO fDAO = new FormaPagamentoDAO();

            while (rs.next()) {
                VendaFormaPagamento vfp = new VendaFormaPagamento();
                vfp.setIdVendaFormaPagamento(rs.getInt("id_venda_formapagamento"));
                vfp.setVenda(vDAO.getCarregaPorID(rs.getInt("venda_id")));
                vfp.setFormaPagamento(fDAO.getCarregaPorID(rs.getInt("forma_pagamento_id")));
                vfp.setValorPago(rs.getDouble("valor_pago"));
                vfp.setNumeroParcelas(rs.getInt("numero_parcelas"));
                lista.add(vfp);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(VendaFormaPagamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return lista;
    }

    public boolean gravar(VendaFormaPagamento vfp) {
        boolean sucesso = false;
        String sql;

        if (vfp.getIdVendaFormaPagamento() == 0) {
            sql = "INSERT INTO venda_formapagamento (venda_id, forma_pagamento_id, valor_pago, numero_parcelas) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE venda_formapagamento SET venda_id=?, forma_pagamento_id=?, valor_pago=?, numero_parcelas=? WHERE id_venda_formapagamento=?";
        }

        try {
            this.conectar();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, vfp.getVenda().getIdVenda());
            pstm.setInt(2, vfp.getFormaPagamento().getIdFormaPagamento());
            pstm.setDouble(3, vfp.getValorPago());
            pstm.setInt(4, vfp.getNumeroParcelas());

            if (vfp.getIdVendaFormaPagamento() > 0) {
                pstm.setInt(5, vfp.getIdVendaFormaPagamento());
            }

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                sucesso = true;
            }

        } catch (SQLException e) {
            Logger.getLogger(VendaFormaPagamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return sucesso;
    }

    public VendaFormaPagamento getCarregaPorID(int id) {
        VendaFormaPagamento vfp = null;
        String sql = "SELECT * FROM venda_formapagamento WHERE id_venda_formapagamento = ?";

        try {
            this.conectar();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            VendaDAO vDAO = new VendaDAO();
            FormaPagamentoDAO fDAO = new FormaPagamentoDAO();

            if (rs.next()) {
                vfp = new VendaFormaPagamento();
                vfp.setIdVendaFormaPagamento(rs.getInt("id_venda_formapagamento"));
                vfp.setVenda(vDAO.getCarregaPorID(rs.getInt("venda_id")));
                vfp.setFormaPagamento(fDAO.getCarregaPorID(rs.getInt("forma_pagamento_id")));
                vfp.setValorPago(rs.getDouble("valor_pago"));
                vfp.setNumeroParcelas(rs.getInt("numero_parcelas"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(VendaFormaPagamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return vfp;
    }

    public boolean excluir(int id) {
        boolean sucesso = false;
        String sql = "DELETE FROM venda_formapagamento WHERE id_venda_formapagamento = ?";

        try {
            this.conectar();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                sucesso = true;
            }

        } catch (SQLException e) {
            Logger.getLogger(VendaFormaPagamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return sucesso;
    }
}
