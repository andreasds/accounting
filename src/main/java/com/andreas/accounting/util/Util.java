package com.andreas.accounting.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class Util implements Serializable {

    private static final long serialVersionUID = -612529271194259738L;

    public static String getInitialParam(String param) {
        return FacesContext.getCurrentInstance().getExternalContext().getInitParameter(param);
    }

    public static ArrayList<String> generateBreadcrumbs() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String[] urlPart = request.getRequestURL().toString().split("/");
        ArrayList<String> breadcrumbs = new ArrayList<>();
        ArrayList<String> output = new ArrayList<>();

        for (int i = 5; i < urlPart.length - 1; i++) {
            breadcrumbs.add(urlPart[i]);
        }

        breadcrumbs.stream().map((item) -> item.replace("-", " ")).forEach((item) -> {
            output.add(WordUtils.capitalize(item));
        });

        return output;
    }

    public static void redirectToPage(String page) {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String url = String.valueOf(context.getRequestContextPath() + page);
            context.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
