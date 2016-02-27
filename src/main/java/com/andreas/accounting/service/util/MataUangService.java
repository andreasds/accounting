package com.andreas.accounting.service.util;

import com.andreas.accounting.model.util.MataUang;
import com.andreas.accounting.util.BaseServiceInterface;
import com.andreas.accounting.util.GrailsRestClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class MataUangService implements BaseServiceInterface, Serializable {

    private static final long serialVersionUID = 12570098091495691L;

    private final GrailsRestClient grc = new GrailsRestClient();
    private final Gson gson = new Gson();
    private final String endpoint = "mataUang";

    @Override
    public Object listAll() {
        String response = grc.get(endpoint);
        Type type = new TypeToken<ArrayList<MataUang>>() {
        }.getType();
        return gson.fromJson(response, type);
    }

    public Object listKode() {
        String response = grc.get(endpoint + "/listKode");
        Type type = new TypeToken<ArrayList<MataUang>>() {
        }.getType();
        return gson.fromJson(response, type);
    }

    @Override
    public Object save(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object get(long id) {
        String response = grc.get(endpoint + "/get?id=" + id);
        return gson.fromJson(response, MataUang.class);
    }
    
    public Object getIDR() {
        String response = grc.get(endpoint + "/getIDR");
        return gson.fromJson(response, MataUang.class);
    }

    @Override
    public Object update(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object search(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
