package com.andreas.accounting.util;

import java.io.Serializable;
import javax.faces.context.FacesContext;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class Util implements Serializable {

    private static final long serialVersionUID = -612529271194259738L;

    public static String getInitialParam(String param) {
        return FacesContext.getCurrentInstance().getExternalContext().getInitParameter(param);
    }
}
