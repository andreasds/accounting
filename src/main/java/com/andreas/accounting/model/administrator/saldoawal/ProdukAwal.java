package com.andreas.accounting.model.administrator.saldoawal;

import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.model.administrator.daftarproduk.Produk;
import com.andreas.accounting.model.util.MataUang;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class ProdukAwal implements Serializable {

    private static final long serialVersionUID = 7048554311942005854L;

    private Long id = 0L;
    private int jumlah = 0;
    private BigDecimal hargaBeli = new BigDecimal(0.0);
    private BigDecimal rate = new BigDecimal(0.0);
    private Date tanggal = new Date();
    private Perusahaan perusahaan = new Perusahaan();
    private Produk produk = new Produk();
    private MataUang mataUang = new MataUang();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public BigDecimal getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(BigDecimal hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Perusahaan getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(Perusahaan perusahaan) {
        this.perusahaan = perusahaan;
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
}
