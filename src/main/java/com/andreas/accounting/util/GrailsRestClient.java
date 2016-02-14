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

    public String login(Object post) {
        String output;
        Client client = Client.create();
        WebResource webResource = client.resource(url + "api/login");
        Gson gson = new Gson();
        String input = gson.toJson(post);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
        if (response.getStatus() != 200) {
            System.out.println("Status = " + response.getStatus() + ", Login error");
            output = "failed";
        } else {
            output = response.getEntity(String.class);
        }
        return output;
    }

    public String logout() {
        Client client = Client.create();
        WebResource webResource = client.resource(url + "api/logout");
        ClientResponse response = webResource.accept("application/json").header("X-Auth-Token", getToken()).post(ClientResponse.class);
        if (response.getStatus() != 200) {
            System.out.println("Status = " + response.getStatus() + ", Logout error");
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.getEntity(String.class);
    }

    public String add(String path, Object post) {
        Client client = Client.create();
        WebResource webResource = client.resource(url + path);
        Gson gson = new Gson();
        String input = gson.toJson(post);
        ClientResponse response = webResource.type("application/json").header("X-Auth-Token", getToken()).post(ClientResponse.class, input);
        if (response.getStatus() != 200) {
            System.out.println("Status = " + response.getStatus() + ", Save error");
        }
        return response.getEntity(String.class);
    }

    public String get(String path) {
        String output;
        Client client = Client.create();
        WebResource webResource = client.resource(url + path);
        ClientResponse response = webResource.accept("application/json").header("X-Auth-Token", getToken()).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            System.out.println("Status = " + response.getStatus() + ", Get error");
            output = "[]";
        } else {
            output = response.getEntity(String.class);
        }
        return output;
    }

    public String put(String path, Object post) {
        String output;
        Client client = Client.create();
        WebResource webResource = client.resource(url + path);
        Gson gson = new Gson();
        String input = gson.toJson(post);
        ClientResponse response = webResource.type("application/json").header("X-Auth-Token", getToken()).put(ClientResponse.class, input);
        if (response.getStatus() != 200) {
            System.out.println("Status = " + response.getStatus() + ", Update error");
        }
        return response.getEntity(String.class);
    }

    public String delete(String path) {
        String output;
        Client client = Client.create();
        WebResource webResource = client.resource(url + path);
        ClientResponse response = webResource.accept("application/json").header("X-Auth-Token", getToken()).delete(ClientResponse.class);
        if (response.getStatus() != 200) {
            System.out.println("Status = " + response.getStatus() + ", Delete error");
        }
        return response.getEntity(String.class);
    }

    public String getToken() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        return sessionMap.get("token").toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
