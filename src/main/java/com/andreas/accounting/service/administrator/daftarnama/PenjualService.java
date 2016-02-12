package com.andreas.accounting.service.administrator.daftarnama;

import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.util.BaseServiceInterface;
import com.andreas.accounting.util.GrailsRestClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class PenjualService implements BaseServiceInterface, Serializable {

    private static final long serialVersionUID = 969259352658854470L;
    
    private final GrailsRestClient grc = new GrailsRestClient();
    private final Gson gson = new Gson();
    private final String endpoint = "penjual";

    @Override
    public Object listAll() {
        String response = grc.get(endpoint);
        System.out.println("List All Response = " + response);
        Type type = new TypeToken<ArrayList<Orang>>() {}.getType();
        return gson.fromJson(response, type);
    }
    
    public Object list(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String params = "?offset=" + first + "&max=" + pageSize + "&sort=" + sortField + "&order=" + (sortOrder == SortOrder.DESCENDING ? "desc" : "asc");
        String response = grc.get(endpoint + "/list" + params);
        System.out.println("List Response = " + response);
        Type type = new TypeToken<ArrayList<Orang>>() {}.getType();
        return gson.fromJson(response, type);
    }
    
    public Object listNama() {
        String response = grc.get(endpoint + "/listNama");
        System.out.println("List Nama Response = " + response);
        Type type = new TypeToken<ArrayList<Orang>>() {}.getType();
        return gson.fromJson(response, type);
    }

    @Override
    public Object save(Object obj) {
        String response = grc.add(endpoint + "/save", obj);
        System.out.println("Save Response = " + response);
        return gson.fromJson(response, JsonObject.class).get("id");
    }

    @Override
    public Object get(long id) {
        String response = grc.get(endpoint + "/get?id=" + id);
        System.out.println("Get Response = " + response);
        return gson.fromJson(response, Orang.class);
    }

    @Override
    public Object update(Object obj) {
        Orang penjual = (Orang) obj;
        String response = grc.put(endpoint + "/update?id=" + penjual.getId(), obj);
        System.out.println("Update Response = " + response);
        return gson.fromJson(response, JsonObject.class).get("id");
    }

    @Override
    public Object delete(long id) {
        String response = grc.delete(endpoint + "/delete?id=" + id);
        System.out.println("Delete Response = " + response);
        return gson.fromJson(response, JsonObject.class);
    }

    @Override
    public Object search(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int count(Map<String, Object> filters) {
        String response = grc.get(endpoint + "/count");
        System.out.println("Count Response = " + response);
        return Integer.parseInt(response);
    }
}
