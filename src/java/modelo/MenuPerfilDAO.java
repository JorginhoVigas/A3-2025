package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuPerfilDAO extends DataBaseDAO {

    public MenuPerfilDAO() throws ClassNotFoundException {
    }

    public boolean vincular(int idMenu, int idPerfil) {
        boolean sucesso = false;
        String sql = "INSERT INTO menu_perfil (menu_id, perfil_id) VALUES (?, ?)";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idMenu);
                pstm.setInt(2, idPerfil);
                sucesso = pstm.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            Logger.getLogger(MenuPerfilDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return sucesso;
    }

    public boolean desvincular(int idMenu, int idPerfil) {
        boolean sucesso = false;
        String sql = "DELETE FROM menu_perfil WHERE menu_id = ? AND perfil_id = ?";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idMenu);
                pstm.setInt(2, idPerfil);
                sucesso = pstm.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            Logger.getLogger(MenuPerfilDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return sucesso;
    }

    public ArrayList<Menu> menusVinculadosPorPerfil(int idPerfil) {
        ArrayList<Menu> lista = new ArrayList<>();

        String sql = "SELECT m.* FROM menu_perfil mp "
                + "INNER JOIN menu m ON mp.menu_id = m.id_menu "
                + "WHERE mp.perfil_id = ?";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idPerfil);

                try (ResultSet rs = pstm.executeQuery()) {
                    while (rs.next()) {
                        Menu m = new Menu();
                        m.setIdMenu(rs.getInt("id_menu"));  // <-- CORRETO
                        m.setNome(rs.getString("nome"));
                        m.setLink(rs.getString("link"));
                        m.setIcone(rs.getString("icone"));
                        m.setExibir(rs.getInt("exibir"));
                        lista.add(m);
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(MenuPerfilDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return lista;
    }

    public ArrayList<Menu> menusNaoVinculadosPorPerfil(int idPerfil) {
        ArrayList<Menu> lista = new ArrayList<>();

        String sql = "SELECT * FROM menu "
                + "WHERE id_menu NOT IN "
                + "(SELECT menu_id FROM menu_perfil WHERE perfil_id = ?)";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idPerfil);

                try (ResultSet rs = pstm.executeQuery()) {
                    while (rs.next()) {
                        Menu m = new Menu();
                        m.setIdMenu(rs.getInt("id_menu"));  // <-- CORRETO
                        m.setNome(rs.getString("nome"));
                        m.setLink(rs.getString("link"));
                        m.setIcone(rs.getString("icone"));
                        m.setExibir(rs.getInt("exibir"));
                        lista.add(m);
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(MenuPerfilDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return lista;
    }

    public MenuPerfil getCarregaMenusPorPerfil(int idPerfil) {
        MenuPerfil mp = new MenuPerfil();
        mp.setMenusVinculados(menusVinculadosPorPerfil(idPerfil));
        mp.setMenusNaoVinculados(menusNaoVinculadosPorPerfil(idPerfil));
        return mp;
    }
}
