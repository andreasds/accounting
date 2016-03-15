package com.andreas.accounting.model.administrator.saldoawal;

import com.andreas.accounting.model.administrator.Rekening;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class RekeningAwal implements Serializable {

    private static final long serialVersionUID = 4337726284616235405L;

    private Long id = 0L;
    private BigDecimal saldo = new BigDecimal(0.0);
    private Perusahaan perusahaan = new Perusahaan();
    private Rekening rekening = new Rekening();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Perusahaan getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(Perusahaan perusahaan) {
        this.perusahaan = perusahaan;
    }

    public Rekening getRekening() {
        return rekening;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }
}
