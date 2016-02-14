package com.andreas.accounting.util;

/**
 *
 * @author Andreas
 */
public interface BaseServiceInterface {

    public Object listAll();

    public Object save(Object obj);

    public Object get(long id);

    public Object update(Object obj);

    public Object delete(long id);

    public Object search(Object obj);
}
