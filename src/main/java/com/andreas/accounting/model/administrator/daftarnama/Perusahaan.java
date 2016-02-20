package com.andreas.accounting.model.administrator.daftarnama;

import java.io.Serializable;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class Perusahaan implements Serializable {

    private static final long serialVersionUID = -4825756718947072529L;

    private Long id = 0L;
    private String nama = "";
    private String alamat = "";
    private String kota = "";
    private boolean pemilik = false;

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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public boolean getPemilik() {
        return pemilik;
    }

    public void setPemilik(boolean pemilik) {
        this.pemilik = pemilik;
    }
}
