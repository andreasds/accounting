package com.andreas.accounting.service.util;

import com.andreas.accounting.util.GrailsRestClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class ExchangeRatesService implements Serializable {

    private static final long serialVersionUID = 3770640044420674720L;

    private final GrailsRestClient grc = new GrailsRestClient();
    private final Gson gson = new Gson();
    private final String endpoint = "http://api.fixer.io";

    public BigDecimal getRate(String kode, Date tanggal) {
        if (!kode.equalsIgnoreCase("IDR")) {
            try {
                String response = grc.get_out(endpoint + "/" + new SimpleDateFormat("yyyy-MM-dd").format(tanggal) + "?base=" + kode + "&symbols=IDR");
                if (response != null) {
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    jsonObject = gson.fromJson(jsonObject.get("rates"), JsonObject.class);
                    return new BigDecimal(jsonObject.get("IDR").toString());
                } else {
                    return new BigDecimal(0.0);
                }
            } catch (Exception ex) {
                return new BigDecimal(0.0);
            }
        } else {
            return new BigDecimal(1.0);
        }
    }
}
