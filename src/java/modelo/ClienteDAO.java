package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO extends DataBaseDAO {

    public ClienteDAO() throws ClassNotFoundException {
    }

    public ArrayList<Cliente> getLista() {
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE status = 1 ORDER BY nome_razao"; // ✅ só ativos e ordenados

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery()) {

                while (rs.next()) {
                    Cliente c = new Cliente();
                    c.setIdCliente(rs.getInt("id_cliente"));
                    c.setNome(rs.getString("nome_razao"));
                    c.setTelefone(rs.getString("telefone"));
                    c.setTipoDocumento(rs.getInt("tipo_documento"));
                    c.setCpfCnpj(rs.getString("cpf_cnpj"));
                    c.setStatus(rs.getInt("status"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return lista;
    }

    public boolean gravar(Cliente c) {
        boolean sucesso = false;
        String sql;

        if (c.getIdCliente() == 0) {
            sql = "INSERT INTO cliente (nome_razao, telefone, tipo_documento, cpf_cnpj, status) VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE cliente SET nome_razao = ?, telefone = ?, tipo_documento = ?, cpf_cnpj = ?, status = ? WHERE id_cliente = ?";
        }

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setString(1, c.getNome());
                pstm.setString(2, c.getTelefone());
                pstm.setInt(3, c.getTipoDocumento());
                pstm.setString(4, c.getCpfCnpj());
                pstm.setInt(5, c.getStatus());

                if (c.getIdCliente() > 0) {
                    pstm.setInt(6, c.getIdCliente());
                }

                int rowsAffected = pstm.executeUpdate();
                if (rowsAffected > 0) {
                    sucesso = true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return sucesso;
    }

    public Cliente getCarregaPorID(int idCliente) {
        Cliente c = null;
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idCliente);

                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        c = new Cliente();
                        c.setIdCliente(rs.getInt("id_cliente"));
                        c.setNome(rs.getString("nome_razao")); // ✅ mesmo ajuste aqui
                        c.setTelefone(rs.getString("telefone"));
                        c.setTipoDocumento(rs.getInt("tipo_documento"));
                        c.setCpfCnpj(rs.getString("cpf_cnpj"));
                        c.setStatus(rs.getInt("status"));
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return c;
    }

    public boolean desativar(Cliente c) {
        boolean sucesso = false;
        try {
            this.conectar();
            String SQL = "UPDATE cliente SET status = 2 WHERE id_cliente = ?";
            try (PreparedStatement pstm = conn.prepareStatement(SQL)) {
                pstm.setInt(1, c.getIdCliente());
                int rowsAffected = pstm.executeUpdate();
                if (rowsAffected > 0) {
                    sucesso = true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return sucesso;
    }
}
