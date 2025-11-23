package modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Criptografia; // Importando nossa classe nova

public class UsuarioDAO extends DataBaseDAO {

    public UsuarioDAO() throws ClassNotFoundException {
    }

    public ArrayList<Usuario> getLista() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE status = 1 ORDER BY nome";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery()) {

                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNome(rs.getString("nome"));
                    u.setLogin(rs.getString("login"));
                    // Não precisamos retornar a senha na lista (segurança)
                    u.setSenha(rs.getString("senha"));
                    u.setStatus(rs.getInt("status"));
                    u.setDataNasc(rs.getDate("data_nascimento"));

                    try {
                        PerfilDAO pDAO = new PerfilDAO();
                        u.setPerfil(pDAO.getCarregaPorID(rs.getInt("perfil_id")));
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }

        return lista;
    }

    public boolean gravar(Usuario u) {
        boolean sucesso = false;
        String sql;

        // Lógica para criptografar senha
        // Se tiver senha, criptografa. Se for vazio (no update), mantém a antiga.
        String senhaCriptografada = "";
        if (u.getSenha() != null && !u.getSenha().isEmpty()) {
            senhaCriptografada = Criptografia.converter(u.getSenha());
            u.setSenha(senhaCriptografada); // Atualiza o objeto com o hash
        }

        if (u.getIdUsuario() == 0) {
            // --- INSERT ---
            sql = "INSERT INTO usuario (nome, login, senha, data_nascimento, status, perfil_id) VALUES (?,?,?,?,?,?)";
        } else {
            // --- UPDATE ---
            // Truque: Se a senha estiver vazia, não atualizamos esse campo
            if (senhaCriptografada.isEmpty()) {
                sql = "UPDATE usuario SET nome=?, login=?, data_nascimento=?, status=?, perfil_id=? WHERE id_usuario=?";
            } else {
                sql = "UPDATE usuario SET nome=?, login=?, senha=?, data_nascimento=?, status=?, perfil_id=? WHERE id_usuario=?";
            }
        }

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setString(1, u.getNome());
                pstm.setString(2, u.getLogin());

                // Controle dos índices por causa do campo senha dinâmico no UPDATE
                int index = 3;

                if (u.getIdUsuario() == 0) {
                    // Insert sempre tem senha
                    pstm.setString(index++, u.getSenha());
                } else {
                    // Update só tem senha se foi digitada
                    if (!senhaCriptografada.isEmpty()) {
                        pstm.setString(index++, u.getSenha());
                    }
                }

                pstm.setDate(index++, new Date(u.getDataNasc().getTime()));
                pstm.setInt(index++, u.getStatus());
                pstm.setInt(index++, u.getPerfil().getIdPerfil());

                if (u.getIdUsuario() > 0) {
                    pstm.setInt(index++, u.getIdUsuario());
                }

                int rowsAffected = pstm.executeUpdate();
                if (rowsAffected > 0) {
                    sucesso = true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return sucesso;
    }

    public Usuario getCarregaPorID(int idUsuario) {
        Usuario u = null;
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idUsuario);

                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        u = new Usuario();
                        u.setIdUsuario(rs.getInt("id_usuario"));
                        u.setNome(rs.getString("nome"));
                        u.setLogin(rs.getString("login"));
                        u.setSenha(rs.getString("senha"));
                        u.setStatus(rs.getInt("status"));
                        u.setDataNasc(rs.getDate("data_nascimento"));

                        try {
                            PerfilDAO pDAO = new PerfilDAO();
                            u.setPerfil(pDAO.getCarregaPorID(rs.getInt("perfil_id")));
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return u;
    }

    public boolean desativar(Usuario u) {
        boolean sucesso = false;
        try {
            this.conectar();
            String SQL = "UPDATE usuario SET status=2 WHERE id_usuario=?";
            try (PreparedStatement pstm = conn.prepareStatement(SQL)) {
                pstm.setInt(1, u.getIdUsuario());
                int rowsAffected = pstm.executeUpdate();
                if (rowsAffected > 0) {
                    sucesso = true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return sucesso;
    }

    // --- MÉTODO DE LOGIN ATUALIZADO ---
    // Agora recebe LOGIN e SENHA para validar tudo no banco
    public Usuario getRecuperarUsuario(String login, String senha) {
        Usuario u = null;

        // Criptografa a senha digitada para comparar com a do banco
        String senhaHash = Criptografia.converter(senha);

        String sql = "SELECT u.*, p.nome AS nome_perfil FROM usuario u "
                + "INNER JOIN perfil p ON p.id_perfil = u.perfil_id "
                + "WHERE u.login = ? AND u.senha = ? AND u.status = 1"; // status 1 = ativo

        try {
            this.conectar();
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setString(1, login);
                pstm.setString(2, senhaHash); // Compara com o hash!

                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        u = new Usuario();
                        u.setIdUsuario(rs.getInt("id_usuario"));
                        u.setNome(rs.getString("nome"));
                        u.setLogin(rs.getString("login"));
                        u.setSenha(rs.getString("senha"));
                        u.setStatus(rs.getInt("status"));
                        u.setDataNasc(rs.getDate("data_nascimento"));

                        try {
                            PerfilDAO pDAO = new PerfilDAO();
                            u.setPerfil(pDAO.getCarregaPorID(rs.getInt("perfil_id")));
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.desconectar();
        }
        return u;
    }
}
