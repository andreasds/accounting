package com.andreas.accounting.service.administrator.saldoawal;

import com.andreas.accounting.model.administrator.saldoawal.ProdukAwal;
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
public class ProdukAwalService implements BaseServiceInterface, Serializable {

    private static final long serialVersionUID = -8740374934330485586L;

    private final GrailsRestClient grc = new GrailsRestClient();
    private final Gson gson = new Gson();
    private final String endpoint = "produkAwal";

    @Override
    public Object listAll() {
        String response = grc.get(endpoint);
        Type type = new TypeToken<ArrayList<ProdukAwal>>() {
        }.getType();
        return gson.fromJson(response, type);
    }

    public Object list(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        String params = "?offset=" + first + "&max=" + pageSize + "&sort=" + sortField + "&order=" + (sortOrder == SortOrder.DESCENDING ? "desc" : "asc");
        String response = grc.get(endpoint + "/list" + params);
        Type type = new TypeToken<ArrayList<ProdukAwal>>() {
        }.getType();
        return gson.fromJson(response, type);
    }

    @Override
    public Object save(Object obj) {
        ProdukAwal produk = (ProdukAwal) obj;
        String response = grc.add(endpoint + "/save?id=" + produk.getId(), obj);
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
        return gson.fromJson(response, ProdukAwal.class);
    }

    @Override
    public Object update(Object obj) {
        ProdukAwal produk = (ProdukAwal) obj;
        String response = grc.put(endpoint + "/update?id=" + produk.getId(), obj);
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

    public boolean checkProduk(long produkId, long perusahaanId) {
        String response = grc.get(endpoint + "/checkProduk?produkId=" + produkId + "&perusahaanId=" + perusahaanId);
        return Integer.parseInt(response) == 0;
    }

    public BigDecimal getTotal(long perusahaanId) {
        String response = grc.get(endpoint + "/getTotal?perusahaanId=" + perusahaanId);
        return new BigDecimal(response);
    }

    public int count(Map<String, Object> filters) {
        String response = grc.get(endpoint + "/count");
        return Integer.parseInt(response);
    }
}
