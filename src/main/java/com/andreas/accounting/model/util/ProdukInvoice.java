package com.andreas.accounting.model.util;

import com.andreas.accounting.model.administrator.daftarproduk.Produk;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class ProdukInvoice implements Serializable {

    private static final long serialVersionUID = -92174982148031659L;

    private Long id = 0L;
    private BigDecimal jumlah = new BigDecimal(0.0);
    private BigDecimal harga = new BigDecimal(0.0);
    private BigDecimal rate = new BigDecimal(0.0);
    private Produk produk = new Produk();
    private MataUang mataUang = new MataUang();

    private boolean removed = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getJumlah() {
        return jumlah;
    }

    public void setJumlah(BigDecimal jumlah) {
        this.jumlah = jumlah;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public MataUang getMataUang() {
        return mataUang;
    }

    public void setMataUang(MataUang mataUang) {
        this.mataUang = mataUang;
    }

    public boolean getRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public BigDecimal getTotal() {
        return jumlah.multiply(harga.multiply(rate));
    }
}
