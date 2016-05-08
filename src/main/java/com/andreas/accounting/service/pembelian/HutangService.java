package com.andreas.accounting.service.pembelian;

import com.andreas.accounting.model.util.HutangPiutang;
import com.andreas.accounting.model.util.Pembayaran;
import com.andreas.accounting.util.BaseServiceInterface;
import com.andreas.accounting.util.GrailsRestClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class HutangService implements BaseServiceInterface, Serializable {

    private static final long serialVersionUID = -8844545179689959291L;

    private final GrailsRestClient grc = new GrailsRestClient();
    private final Gson gson = new Gson();
    private final String endpoint = "hutang";

    @Override
    public Object listAll() {
        String response = grc.get(endpoint);
        Type type = new TypeToken<ArrayList<HutangPiutang>>() {
        }.getType();
        return gson.fromJson(response, type);
    }

    public Object list(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String params = "?offset=" + first + "&max=" + pageSize + "&sort=" + sortField + "&order=" + (sortOrder == SortOrder.DESCENDING ? "desc" : "asc");
        String response = grc.put(endpoint + "/list" + params, filters);
        Type type = new TypeToken<ArrayList<HutangPiutang>>() {
        }.getType();
        return gson.fromJson(response, type);
    }

    public Object listBayar(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String params = "?offset=" + first + "&max=" + pageSize + "&sort=" + sortField + "&order=" + (sortOrder == SortOrder.ASCENDING ? "asc" : "desc");
        String response = grc.put(endpoint + "/listBayar" + params, filters);
        Type type = new TypeToken<ArrayList<Pembayaran>>() {
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
            return jsonObject.get("message");
        }
    }

    @Override
    public Object get(long id) {
        String response = grc.get(endpoint + "/get?id=" + id);
        return gson.fromJson(response, Pembayaran.class);
    }

    @Override
    public Object update(Object obj) {
        Pembayaran pembayaran = (Pembayaran) obj;
        String response = grc.put(endpoint + "/update?id=" + pembayaran.getId(), obj);
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

    public BigDecimal getTotal(long penjualId, long pemilikId) {
        String response = grc.get(endpoint + "/getTotal?penjualId=" + penjualId + "&pemilikId=" + pemilikId);
        return new BigDecimal(response);
    }

    public int count(Map<String, Object> filters) {
        String response = grc.put(endpoint + "/count", filters);
        return Integer.parseInt(response);
    }

    public int countBayar(Map<String, Object> filters) {
        String response = grc.put(endpoint + "/countBayar", filters);
        return Integer.parseInt(response);
    }
}
