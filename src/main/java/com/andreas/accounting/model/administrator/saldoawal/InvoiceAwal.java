package com.andreas.accounting.model.administrator.saldoawal;

import com.andreas.accounting.model.util.Invoice;
import com.andreas.accounting.model.util.MataUang;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class InvoiceAwal implements Serializable {

    private static final long serialVersionUID = 948659738814683810L;

    private Long id = 0L;
    private BigDecimal jumlah = new BigDecimal(0.0);
    private BigDecimal rate = new BigDecimal(0.0);
    private Invoice invoice = new Invoice();
    private MataUang mataUang = new MataUang();

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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public MataUang getMataUang() {
        return mataUang;
    }

    public void setMataUang(MataUang mataUang) {
        this.mataUang = mataUang;
    }
}
