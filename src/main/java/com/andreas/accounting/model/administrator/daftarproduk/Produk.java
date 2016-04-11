package com.andreas.accounting.model.administrator.daftarproduk;

import java.io.Serializable;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class Produk implements Serializable {

    private static final long serialVersionUID = -1845866666858035113L;

    private Long id = 0L;
    private int indeks = 0;
    private String deskripsi = "";
    private KategoriProduk kategoriProduk = new KategoriProduk();
    private Satuan satuan = new Satuan();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIndeks() {
        return indeks;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public KategoriProduk getKategoriProduk() {
        return kategoriProduk;
    }

    public void setKategoriProduk(KategoriProduk kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
    }

    public Satuan getSatuan() {
        return satuan;
    }

    public void setSatuan(Satuan satuan) {
        this.satuan = satuan;
    }

    public String getKode() {
        return kategoriProduk.getKode() + "-" + String.format("%05d", indeks);
    }
}
