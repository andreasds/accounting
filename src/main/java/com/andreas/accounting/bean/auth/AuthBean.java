package com.andreas.accounting.bean.auth;

import com.andreas.accounting.model.auth.Session;
import com.andreas.accounting.util.GrailsRestClient;
import com.google.gson.Gson;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
@ManagedBean(name = "authBean", eager = true)
@SessionScoped
public class AuthBean implements Serializable {

    private static final long serialVersionUID = 207352772602184333L;

    private GrailsRestClient grc = new GrailsRestClient();
    private Session session = new Session();
    private boolean loggedIn = false;

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

    public AuthBean() {
        System.out.println("Hello AuthBean!");
    }

    public void login() {
        System.out.println("Username = " + session.getUsername());
        System.out.println("Password = " + session.getPassword());
        System.out.println("Hello Login!");

        if (session.getUsername().equals("") || session.getPassword().equals("")) {
            loggedIn = false;
            System.out.println("Silakan isi username dan password!");
            return;
        }
        Gson gson = new Gson();
        String response = grc.login(session);
        System.out.println("Login Response");
        System.out.println(response);
        if (response.equals("failed")) {
            loggedIn = false;
            return;
        }
        loggedIn = true;
        session = gson.fromJson(response, Session.class);
        System.out.println("Roles = " + session.getRoles().toString());
        System.out.println("Token = " + session.getAccess_token());
    }

    public String getMessage() {
        return "Login Page";
    }
}
