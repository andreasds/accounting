package com.andreas.accounting.model.administrator.daftarproduk;

import java.io.Serializable;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class Produk implements Serializable {

    private static final long serialVersionUID = -1845866666858035113L;

    private Long id;
    private String kode;
    private String deskripsi;
    private int jumlahAwal;
    private int hargaBeliAwal;
    private Satuan satuan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getJumlahAwal() {
        return jumlahAwal;
    }

    public void setJumlahAwal(int jumlahAwal) {
        this.jumlahAwal = jumlahAwal;
    }
    public int getHargaBeliAwal() {
        return hargaBeliAwal;
    }

    public void setHargaBeliAwal(int hargaBeliAwal) {
        this.hargaBeliAwal = hargaBeliAwal;
    }

    public Satuan getSatuan() {
        return satuan;
    }

    public void setSatuan(Satuan satuan) {
        this.satuan = satuan;
    }
}
