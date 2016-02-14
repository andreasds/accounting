package com.andreas.accounting.util;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public interface BaseBeanInterface {

    public void init();

    public void viewInput();

    public void viewDetail(long id);

    public void viewEdit(long id);

    public void viewAll();
}
