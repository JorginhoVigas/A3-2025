package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FornecedorDAO extends DataBaseDAO {

    public FornecedorDAO() throws ClassNotFoundException {
    }

    public ArrayList<Fornecedor> getLista() {
        ArrayList<Fornecedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor";

        try {
            this.conectar();
            if (conn == null) {
                return lista;
            }

            try (PreparedStatement pstm = conn.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery()) {

                while (rs.next()) {
                    Fornecedor f = new Fornecedor();
                    f.setIdFornecedor(rs.getInt("id_fornecedor"));
                    f.setStatus(rs.getInt("status"));
                    f.setRazaoSocial(rs.getString("razao_social"));
                    f.setNomeFantasia(rs.getString("nome_fantasia"));
                    f.setCnpj(rs.getString("cnpj"));
                    f.setInscricaoEstadual(rs.getString("inscricao_estadual"));
                    f.setDataCadastro(rs.getDate("data_cadastro"));
                    f.setTelefone(rs.getString("telefone"));
                    f.setEnderecoId(rs.getInt("endereco_id"));
                    lista.add(f);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return lista;
    }

    public boolean gravar(Fornecedor f) {
        boolean sucesso = false;
        String sql;

        if (f.getIdFornecedor() == 0) {
            sql = "INSERT INTO fornecedor (status, razao_social, nome_fantasia, cnpj, inscricao_estadual, data_cadastro, telefone, endereco_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE fornecedor SET status=?, razao_social=?, nome_fantasia=?, cnpj=?, inscricao_estadual=?, data_cadastro=?, telefone=?, endereco_id=? "
                    + "WHERE id_fornecedor=?";
        }

        try {
            this.conectar();
            if (conn == null) {
                return false;
            }

            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, f.getStatus());
                pstm.setString(2, f.getRazaoSocial());
                pstm.setString(3, f.getNomeFantasia());
                pstm.setString(4, f.getCnpj());
                pstm.setString(5, f.getInscricaoEstadual());
                pstm.setDate(6, f.getDataCadastro());
                pstm.setString(7, f.getTelefone());
                pstm.setInt(8, f.getEnderecoId());

                if (f.getIdFornecedor() > 0) {
                    pstm.setInt(9, f.getIdFornecedor());
                }

                sucesso = (pstm.executeUpdate() > 0);
            }
        } catch (SQLException e) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return sucesso;
    }

    public Fornecedor getCarregaPorID(int idFornecedor) {
        Fornecedor f = null;
        String sql = "SELECT * FROM fornecedor WHERE id_fornecedor = ?";

        try {
            this.conectar();
            if (conn == null) {
                return null;
            }

            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idFornecedor);
                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        f = new Fornecedor();
                        f.setIdFornecedor(rs.getInt("id_fornecedor"));
                        f.setStatus(rs.getInt("status"));
                        f.setRazaoSocial(rs.getString("razao_social"));
                        f.setNomeFantasia(rs.getString("nome_fantasia"));
                        f.setCnpj(rs.getString("cnpj"));
                        f.setInscricaoEstadual(rs.getString("inscricao_estadual"));
                        f.setDataCadastro(rs.getDate("data_cadastro"));
                        f.setTelefone(rs.getString("telefone"));
                        f.setEnderecoId(rs.getInt("endereco_id"));
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return f;
    }

    public boolean desativar(Fornecedor f) {
        boolean sucesso = false;
        try {
            this.conectar();
            if (conn == null) {
                return false;
            }

            String SQL = "UPDATE fornecedor SET status = 2 WHERE id_fornecedor = ?";
            try (PreparedStatement pstm = conn.prepareStatement(SQL)) {
                pstm.setInt(1, f.getIdFornecedor());
                sucesso = (pstm.executeUpdate() > 0);
            }

        } catch (SQLException e) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return sucesso;
    }
}
