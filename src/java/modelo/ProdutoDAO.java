package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDAO extends DataBaseDAO {

    public ProdutoDAO() throws ClassNotFoundException {
    }

    public ArrayList<Produto> getLista() {
        ArrayList<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try {
            this.conectar();

            if (conn == null) {
                Logger.getLogger(ProdutoDAO.class.getName())
                        .log(Level.SEVERE, "Conexão com o banco não foi estabelecida.");
                return lista;
            }

            try (PreparedStatement pstm = conn.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery()) {

                while (rs.next()) {
                    Produto p = new Produto();
                    p.setIdProduto(rs.getInt("id_produto"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setCusto(rs.getDouble("custo"));
                    p.setFornecedorId(rs.getInt("fornecedor_id"));
                    p.setCodigoBarras(rs.getString("codigo_barras"));
                    p.setMarca(rs.getString("marca"));
                    p.setUnidadeMedida(rs.getString("unidade_medida"));

                    Timestamp ts = rs.getTimestamp("data_aquisicao");
                    if (ts != null) {
                        p.setDataAquisicao(new Date(ts.getTime()));
                    } else {
                        p.setDataAquisicao(null);
                    }

                    p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                    p.setEstoqueMinimo(rs.getInt("estoque_minimo"));
                    p.setValorVenda(rs.getDouble("valor_venda"));
                    p.setStatus(rs.getInt("status"));
                    p.setCategoriaId(rs.getInt("categoria_id"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return lista;
    }

    public boolean gravar(Produto p) {
        boolean sucesso = false;
        String sql;

        if (p.getIdProduto() == 0) {
            sql = "INSERT INTO produtos (descricao, custo, fornecedor_id, codigo_barras, marca, unidade_medida, data_aquisicao, quantidade_estoque, estoque_minimo, valor_venda, status, categoria_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE produtos SET descricao=?, custo=?, fornecedor_id=?, codigo_barras=?, marca=?, unidade_medida=?, data_aquisicao=?, quantidade_estoque=?, estoque_minimo=?, valor_venda=?, status=?, categoria_id=? "
                    + "WHERE id_produto=?";
        }

        try {
            this.conectar();

            if (conn == null) {
                Logger.getLogger(ProdutoDAO.class.getName())
                        .log(Level.SEVERE, "Conexão com o banco não foi estabelecida.");
                return false;
            }

            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setString(1, p.getDescricao());
                pstm.setDouble(2, p.getCusto());
                pstm.setInt(3, p.getFornecedorId());
                pstm.setString(4, p.getCodigoBarras());
                pstm.setString(5, p.getMarca());
                pstm.setString(6, p.getUnidadeMedida());
                pstm.setDate(7, p.getDataAquisicao());
                pstm.setInt(8, p.getQuantidadeEstoque());
                pstm.setInt(9, p.getEstoqueMinimo());
                pstm.setDouble(10, p.getValorVenda());
                pstm.setInt(11, p.getStatus());
                pstm.setInt(12, p.getCategoriaId());

                if (p.getIdProduto() > 0) {
                    pstm.setInt(13, p.getIdProduto());
                }

                sucesso = (pstm.executeUpdate() > 0);
            }
        } catch (SQLException e) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return sucesso;
    }

    public Produto getCarregaPorID(int idProduto) {
        Produto p = null;
        String sql = "SELECT * FROM produtos WHERE id_produto = ?";

        try {
            this.conectar();

            if (conn == null) {
                Logger.getLogger(ProdutoDAO.class.getName())
                        .log(Level.SEVERE, "Conexão com o banco não foi estabelecida.");
                return null;
            }

            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idProduto);

                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        p = new Produto();
                        p.setIdProduto(rs.getInt("id_produto"));
                        p.setDescricao(rs.getString("descricao"));
                        p.setCusto(rs.getDouble("custo"));
                        p.setFornecedorId(rs.getInt("fornecedor_id"));
                        p.setCodigoBarras(rs.getString("codigo_barras"));
                        p.setMarca(rs.getString("marca"));
                        p.setUnidadeMedida(rs.getString("unidade_medida"));

                        Timestamp ts = rs.getTimestamp("data_aquisicao");
                        if (ts != null) {
                            p.setDataAquisicao(new Date(ts.getTime()));
                        } else {
                            p.setDataAquisicao(null);
                        }

                        p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                        p.setEstoqueMinimo(rs.getInt("estoque_minimo"));
                        p.setValorVenda(rs.getDouble("valor_venda"));
                        p.setStatus(rs.getInt("status"));
                        p.setCategoriaId(rs.getInt("categoria_id"));
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return p;
    }

    public boolean desativar(Produto p) {
        boolean sucesso = false;
        try {
            this.conectar();

            if (conn == null) {
                Logger.getLogger(ProdutoDAO.class.getName())
                        .log(Level.SEVERE, "Conexão com o banco não foi estabelecida.");
                return false;
            }

            String SQL = "UPDATE produtos SET status = 2 WHERE id_produto = ?";
            try (PreparedStatement pstm = conn.prepareStatement(SQL)) {
                pstm.setInt(1, p.getIdProduto());
                sucesso = (pstm.executeUpdate() > 0);
            }

        } catch (SQLException e) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return sucesso;
    }
}
