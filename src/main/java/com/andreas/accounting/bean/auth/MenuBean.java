package com.andreas.accounting.bean.auth;

import com.andreas.accounting.model.auth.Menu;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
@ManagedBean(name = "menuBean", eager = true)
@SessionScoped
public class MenuBean implements Serializable {

    private static final long serialVersionUID = -2192728682604636301L;

    private ArrayList<Menu> menus = new ArrayList<>();

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }
}
