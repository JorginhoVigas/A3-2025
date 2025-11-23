package modelo;

import java.util.ArrayList;

public class MenuPerfil {
    
    private Perfil perfil;
    private Menu menu;
    private ArrayList<Menu> menusVinculados;
    private ArrayList<Menu> menusNaoVinculados;

    public MenuPerfil() {
    }

    public MenuPerfil(Perfil perfil, Menu menu, ArrayList<Menu> menusVinculados, ArrayList<Menu> menusNaoVinculados) {
        this.perfil = perfil;
        this.menu = menu;
        this.menusVinculados = menusVinculados;
        this.menusNaoVinculados = menusNaoVinculados;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public ArrayList<Menu> getMenusVinculados() {
        return menusVinculados;
    }

    public void setMenusVinculados(ArrayList<Menu> menusVinculados) {
        this.menusVinculados = menusVinculados;
    }

    public ArrayList<Menu> getMenusNaoVinculados() {
        return menusNaoVinculados;
    }

    public void setMenusNaoVinculados(ArrayList<Menu> menusNaoVinculados) {
        this.menusNaoVinculados = menusNaoVinculados;
    }

    @Override
    public String toString() {
        return "MenuPerfil{" + "perfil=" + perfil + ", menu=" + menu + ", menusVinculados=" + menusVinculados + ", menusNaoVinculados=" + menusNaoVinculados + '}';
    }
    
    
    
}
