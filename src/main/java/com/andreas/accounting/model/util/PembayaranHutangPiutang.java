package com.andreas.accounting.model.util;

import com.andreas.accounting.model.administrator.Rekening;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class PembayaranHutangPiutang implements Serializable {

    private static final long serialVersionUID = 8505039521030546387L;

    private Date tanggal = new Date();
    private String deskripsi = "";
    private Rekening rekening = new Rekening();
    private ArrayList<Pembayaran> pembayarans = new ArrayList<>();

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Rekening getRekening() {
        return rekening;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }

    public ArrayList<Pembayaran> getPembayarans() {
        return pembayarans;
    }

    public void setPembayarans(ArrayList<Pembayaran> pembayarans) {
        this.pembayarans = pembayarans;
    }
}
