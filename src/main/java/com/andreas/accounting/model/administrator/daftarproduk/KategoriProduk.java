package com.andreas.accounting.model.administrator.daftarproduk;

import java.io.Serializable;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class KategoriProduk implements Serializable {

    private static final long serialVersionUID = -6475899306226842271L;

    private Long id;
    private String nama;
    private String kode;

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

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
}
