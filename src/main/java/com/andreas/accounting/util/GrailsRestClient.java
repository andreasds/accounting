package com.andreas.accounting.util;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;

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
        if(response.getStatus() != 200) {
            System.out.println("Login error");
            output = "failed";
        } else {
            output = response.getEntity(String.class);
        }
        return output;
    }
}
