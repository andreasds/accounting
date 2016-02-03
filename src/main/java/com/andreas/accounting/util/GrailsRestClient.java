package com.andreas.accounting.util;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class GrailsRestClient implements Serializable {

    private static final long serialVersionUID = -7321278237744509256L;

    public GrailsRestClient() {
        url = Util.getInitialParam("grailsEndPoint");
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String login(Object post) {
        String output;
        Client client = Client.create();
        WebResource webResource = client.resource(url + "api/login");
        Gson gson = new Gson();
        String input = gson.toJson(post);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
        if (response.getStatus() != 200) {
            System.out.println("Login error");
            output = "failed";
        } else {
            output = response.getEntity(String.class);
        }
        return output;
    }

    public String get(String path) {
        String output;
        Client client = Client.create();
        WebResource webResource = client.resource(url + path);
        ClientResponse response = webResource.accept("application/json").header("X-Auth-Token", getToken()).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            System.out.println("Get error");
            output = "[]";
        } else {
            output = response.getEntity(String.class);
        }
        return output;
    }

    public String getToken() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        System.out.println("sessionMap = " + sessionMap.toString());
        return sessionMap.get("token").toString();
    }
}
