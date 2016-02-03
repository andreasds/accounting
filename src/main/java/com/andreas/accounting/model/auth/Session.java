package com.andreas.accounting.model.auth;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class Session implements Serializable {

    private static final long serialVersionUID = -8842662144148431966L;

    private String username;
    private String password;
    private ArrayList<String> roles;
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
