package com.andreas.accounting.model.util;

import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class Invoice implements Serializable {

    private static final long serialVersionUID = -5074865570625259744L;

    private Long id = 0L;
    private String no = "";
    private Date tanggal = new Date();
    private BigDecimal rate = new BigDecimal(0.0);
    private Perusahaan perusahaan = new Perusahaan();
    private Orang orang = new Orang();
    private MataUang mataUang = new MataUang();
    private ArrayList<ProdukInvoice> produkInvoices = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Perusahaan getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(Perusahaan perusahaan) {
        this.perusahaan = perusahaan;
    }

    public Orang getOrang() {
        return orang;
    }

    public void setOrang(Orang orang) {
        this.orang = orang;
    }

    public MataUang getMataUang() {
        return mataUang;
    }

    public void setMataUang(MataUang mataUang) {
        this.mataUang = mataUang;
    }

    public ArrayList<ProdukInvoice> getProdukInvoices() {
        return produkInvoices;
    }

    public void setProdukInvoices(ArrayList<ProdukInvoice> produkInvoices) {
        this.produkInvoices = produkInvoices;
    }

    public BigDecimal getTotal() {
        BigDecimal temp = new BigDecimal(0.0);
        for (ProdukInvoice produkInvoice : produkInvoices) {
            temp = temp.add(produkInvoice.getTotal().multiply(rate));
        }
        return temp;
    }
}
