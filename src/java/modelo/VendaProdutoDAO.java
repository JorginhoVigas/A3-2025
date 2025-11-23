package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendaProdutoDAO extends DataBaseDAO {

    public VendaProdutoDAO() throws ClassNotFoundException {
    }

    public ArrayList<VendaProduto> getLista() {
        ArrayList<VendaProduto> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda_produto";

        try {
            this.conectar();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            VendaDAO vDAO = new VendaDAO();
            ProdutoDAO pDAO = new ProdutoDAO();

            while (rs.next()) {
                VendaProduto vp = new VendaProduto();
                vp.setIdVendaProduto(rs.getInt("id_venda_produto"));
                vp.setVenda(vDAO.getCarregaPorID(rs.getInt("venda_id")));
                vp.setProduto(pDAO.getCarregaPorID(rs.getInt("produto_id")));
                vp.setQuantidade(rs.getInt("quantidade"));
                vp.setValorUnitario(rs.getDouble("valor_unitario"));

                // LER É PERMITIDO: Pegamos o valor que o banco calculou
                vp.setValorTotal(rs.getDouble("subtotal"));

                lista.add(vp);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(VendaProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return lista;
    }

    public boolean gravar(VendaProduto vp) {
        boolean sucesso = false;
        String sql;

        // CORREÇÃO IMPORTANTE:
        // Removemos 'subtotal' do INSERT e UPDATE porque é uma coluna gerada automaticamente pelo banco.
        if (vp.getIdVendaProduto() == 0) {
            sql = "INSERT INTO venda_produto (venda_id, produto_id, quantidade, valor_unitario) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE venda_produto SET venda_id=?, produto_id=?, quantidade=?, valor_unitario=? WHERE id_venda_produto=?";
        }

        try {
            this.conectar();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, vp.getVenda().getIdVenda());
            pstm.setInt(2, vp.getProduto().getIdProduto());
            pstm.setInt(3, vp.getQuantidade());
            pstm.setDouble(4, vp.getValorUnitario());

            // NÃO passamos o valorTotal aqui, o banco calcula sozinho.
            if (vp.getIdVendaProduto() > 0) {
                // O índice agora é 5, pois removemos um parâmetro
                pstm.setInt(5, vp.getIdVendaProduto());
            }

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                sucesso = true;
            }

        } catch (SQLException e) {
            Logger.getLogger(VendaProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return sucesso;
    }

    public boolean excluir(int id) {
        boolean sucesso = false;
        String sql = "DELETE FROM venda_produto WHERE id_venda_produto = ?";

        try {
            this.conectar();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                sucesso = true;
            }

        } catch (SQLException e) {
            Logger.getLogger(VendaProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return sucesso;
    }

    public VendaProduto getCarregaPorID(int idVendaProduto) {
        VendaProduto vp = null;
        String sql = "SELECT * FROM venda_produto WHERE id_venda_produto = ?";

        try {
            this.conectar();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, idVendaProduto);
            ResultSet rs = pstm.executeQuery();

            VendaDAO vDAO = new VendaDAO();
            ProdutoDAO pDAO = new ProdutoDAO();

            if (rs.next()) {
                vp = new VendaProduto();
                vp.setIdVendaProduto(rs.getInt("id_venda_produto"));
                vp.setVenda(vDAO.getCarregaPorID(rs.getInt("venda_id")));
                vp.setProduto(pDAO.getCarregaPorID(rs.getInt("produto_id")));
                vp.setQuantidade(rs.getInt("quantidade"));
                vp.setValorUnitario(rs.getDouble("valor_unitario"));

                // LER É PERMITIDO
                vp.setValorTotal(rs.getDouble("subtotal"));
            }

        } catch (SQLException e) {
            Logger.getLogger(VendaProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(VendaProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return vp;
    }

    public ArrayList<VendaProduto> getListaPorVenda(int idVenda) {
        ArrayList<VendaProduto> lista = new ArrayList<>();
        String sql = "SELECT * FROM venda_produto WHERE venda_id = ?";

        try {
            this.conectar();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, idVenda);
            ResultSet rs = pstm.executeQuery();

            VendaDAO vDAO = new VendaDAO();
            ProdutoDAO pDAO = new ProdutoDAO();

            while (rs.next()) {
                VendaProduto vp = new VendaProduto();
                vp.setIdVendaProduto(rs.getInt("id_venda_produto"));
                vp.setVenda(vDAO.getCarregaPorID(rs.getInt("venda_id")));
                vp.setProduto(pDAO.getCarregaPorID(rs.getInt("produto_id")));
                vp.setQuantidade(rs.getInt("quantidade"));
                vp.setValorUnitario(rs.getDouble("valor_unitario"));

                // LER É PERMITIDO
                vp.setValorTotal(rs.getDouble("subtotal"));

                lista.add(vp);
            }

        } catch (SQLException e) {
            Logger.getLogger(VendaProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(VendaProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return lista;
    }

}
