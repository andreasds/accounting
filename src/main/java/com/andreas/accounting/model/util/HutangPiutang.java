package com.andreas.accounting.model.util;

import com.andreas.accounting.model.administrator.daftarnama.Orang;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class HutangPiutang implements Serializable {

    private static final long serialVersionUID = -5556234226973968761L;

    private BigDecimal total = new BigDecimal(0.0);
    private BigDecimal bayar = new BigDecimal(0.0);
    private Orang orang = new Orang();
    private MataUang mataUang = new MataUang();
    private Invoice invoice = new Invoice();

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getBayar() {
        return bayar;
    }

    public void setBayar(BigDecimal bayar) {
        this.bayar = bayar;
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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getSisa() {
        return total.subtract(bayar);
    }
}
