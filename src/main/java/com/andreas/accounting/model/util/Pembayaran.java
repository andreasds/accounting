package com.andreas.accounting.model.util;

import com.andreas.accounting.model.administrator.Rekening;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class Pembayaran implements Serializable {

    private static final long serialVersionUID = 7693921578493593418L;

    private Long id = 0L;
    private Date tanggal = new Date();
    private BigDecimal jumlah = new BigDecimal(0.0);
    private BigDecimal rate = new BigDecimal(0.0);
    private String deskripsi = "";
    private Invoice invoice = new Invoice();
    private Rekening rekening = new Rekening();
    private MataUang mataUang = new MataUang();

    private BigDecimal bayar = new BigDecimal(0.0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public BigDecimal getJumlah() {
        return jumlah;
    }

    public void setJumlah(BigDecimal jumlah) {
        this.jumlah = jumlah;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Rekening getRekening() {
        return rekening;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }

    public MataUang getMataUang() {
        return mataUang;
    }

    public void setMataUang(MataUang mataUang) {
        this.mataUang = mataUang;
    }

    public BigDecimal getBayar() {
        return bayar;
    }

    public void setBayar(BigDecimal bayar) {
        this.bayar = bayar;
    }
}
