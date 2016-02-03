package com.andreas.accounting.model.auth;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = -7245726721448894519L;

    private String nama;
    private String path;
    private String icon;
    private ArrayList<Menu> subMenu;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArrayList<Menu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(ArrayList<Menu> subMenu) {
        this.subMenu = subMenu;
    }
}
