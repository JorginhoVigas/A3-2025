package modelo;

public class Perfil {

    private int idPerfil;
    private String nome;
    private int status;
    private MenuPerfil menus;

    public Perfil() {

        this.menus = new MenuPerfil();
    }

    public Perfil(int idPerfil, String nome, int status, MenuPerfil menus) {
        this.idPerfil = idPerfil;
        this.nome = nome;
        this.status = status;
        this.menus = menus != null ? menus : new MenuPerfil();
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MenuPerfil getMenus() {
        return menus;
    }

    public void setMenus(MenuPerfil menus) {
        this.menus = menus != null ? menus : new MenuPerfil();
    }

    @Override
    public String toString() {
        return "Perfil{"
                + "idPerfil=" + idPerfil
                + ", nome='" + nome + '\''
                + ", status=" + status
                + '}';
    }

}
