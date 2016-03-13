package com.andreas.accounting.model.util;

import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import java.io.Serializable;
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
    private Perusahaan perusahaan = new Perusahaan();
    private Orang orang = new Orang();

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
}
