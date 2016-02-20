package com.andreas.accounting.bean.auth;

import com.andreas.accounting.model.auth.Menu;
import com.andreas.accounting.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
@ManagedBean(name = "menuBean")
@SessionScoped
public class MenuBean implements Serializable {

    private static final long serialVersionUID = -2192728682604636301L;

    private ArrayList<Menu> menus = new ArrayList<>();
    private String currentMenu;

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    public String getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(String currentMenu) {
        this.currentMenu = currentMenu;
    }

    public ArrayList<String> getBreadcrumbs() {
        return Util.generateBreadcrumbs();
    }
}
