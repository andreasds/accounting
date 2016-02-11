/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
