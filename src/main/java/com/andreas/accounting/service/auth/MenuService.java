package com.andreas.accounting.service.auth;

import com.andreas.accounting.model.auth.Menu;
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
public class MenuService implements Serializable {

    private static final long serialVersionUID = -8134654546995682699L;

    private final GrailsRestClient grc = new GrailsRestClient();
    private final Gson gson = new Gson();
    private final String endpoint = "menu";

    public Object listAuthorizedMenu() {
        String response = grc.get(endpoint + "/index");
        Type type = new TypeToken<ArrayList<Menu>>() {
        }.getType();
        return gson.fromJson(response, type);
    }
}
