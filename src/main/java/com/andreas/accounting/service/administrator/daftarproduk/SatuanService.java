package com.andreas.accounting.service.administrator.daftarproduk;

import com.andreas.accounting.model.administrator.daftarproduk.Satuan;
import com.andreas.accounting.util.BaseServiceInterface;
import com.andreas.accounting.util.GrailsRestClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class SatuanService implements BaseServiceInterface, Serializable {

    private static final long serialVersionUID = -5969685360664601228L;

    private final GrailsRestClient grc = new GrailsRestClient();
    private final Gson gson = new Gson();
    private final String endpoint = "satuan";

    @Override
    public Object listAll() {
        String response = grc.get(endpoint);
        Type type = new TypeToken<ArrayList<Satuan>>() {
        }.getType();
        return gson.fromJson(response, type);
    }

    public Object list(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String params = "?offset=" + first + "&max=" + pageSize + "&sort=" + sortField + "&order=" + (sortOrder == SortOrder.DESCENDING ? "desc" : "asc");
        String response = grc.get(endpoint + "/list" + params);
        Type type = new TypeToken<ArrayList<Satuan>>() {
        }.getType();
        return gson.fromJson(response, type);
    }

    public Object listKode() {
        String response = grc.get(endpoint + "/listKode");
        Type type = new TypeToken<ArrayList<Satuan>>() {
        }.getType();
        return gson.fromJson(response, type);
    }

    @Override
    public Object save(Object obj) {
        String response = grc.add(endpoint + "/save", obj);
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        if (jsonObject.get("message").toString().equalsIgnoreCase("failed")) {
            return jsonObject.get("error");
        } else {
            return jsonObject.get("id");
        }
    }

    @Override
    public Object get(long id) {
        String response = grc.get(endpoint + "/get?id=" + id);
        return gson.fromJson(response, Satuan.class);
    }

    @Override
    public Object update(Object obj) {
        Satuan satuan = (Satuan) obj;
        String response = grc.put(endpoint + "/update?id=" + satuan.getId(), obj);
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        if (jsonObject.get("message").toString().equalsIgnoreCase("failed")) {
            return jsonObject.get("error");
        } else {
            return jsonObject.get("id");
        }
    }

    @Override
    public Object delete(long id) {
        String response = grc.delete(endpoint + "/delete?id=" + id);
        return gson.fromJson(response, JsonObject.class);
    }

    @Override
    public Object search(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean checkKode(String kode) {
        try {
            String response = grc.get(endpoint + "/checkKode?kode=" + URLEncoder.encode(kode, "UTF-8"));
            return Integer.parseInt(response) == 0;
        } catch (UnsupportedEncodingException | NumberFormatException ex) {
            return false;
        }
    }

    public int count(Map<String, Object> filters) {
        String response = grc.get(endpoint + "/count");
        return Integer.parseInt(response);
    }
}
