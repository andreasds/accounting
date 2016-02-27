package com.andreas.accounting.model.administrator;

import java.io.Serializable;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class Rekening implements Serializable {

    private static final long serialVersionUID = -191083231951307883L;

    private Long id = 0L;
    private String nama = "";
    private String deskripsi = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
