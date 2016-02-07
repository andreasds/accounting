package com.andreas.accounting.bean.auth;

import com.andreas.accounting.model.auth.Menu;
import com.andreas.accounting.model.auth.Session;
import com.andreas.accounting.service.auth.MenuService;
import com.andreas.accounting.util.GrailsRestClient;
import com.andreas.accounting.util.Util;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
@ManagedBean(name = "authBean")
@SessionScoped
public class AuthBean implements Serializable {

    private static final long serialVersionUID = 207352772602184333L;

    private final GrailsRestClient grc = new GrailsRestClient();
    private Session session = new Session();
    private boolean loggedIn = false;

    @ManagedProperty(value = "#{menuBean}")
    private MenuBean menu;
    private final MenuService menuService = new MenuService();

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    public boolean getLoggedIn() {
        return loggedIn;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public MenuBean getMenu() {
        return menu;
    }

    public void setMenu(MenuBean menu) {
        this.menu = menu;
    }

    public void login() {
        if (session.getUsername().equals("") || session.getPassword().equals("")) {
            System.out.println("Silakan isi username dan password!");
            return;
        }

        String response = grc.login(session);
        if (response.equals("failed")) {
            return;
        }
        loggedIn = true;
        Gson gson = new Gson();
        session = gson.fromJson(response, Session.class);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("token", session.getToken());

        menu.setMenus((ArrayList<Menu>) menuService.listAuthorizedMenu());
        Util.redirectToPage("/home.xhtml");
    }
    
    public void logout() {
        grc.logout();
        loggedIn = false;
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.clear();
        Util.redirectToPage("/login.xhtml");
    }
    
    public void validate() {
        if(!loggedIn) {
            Util.redirectToPage("/login.xhtml");
        }
    }
}
