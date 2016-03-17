package com.andreas.accounting.bean.administrator.saldoawal;

import com.andreas.accounting.lazy.administator.saldoawal.ProdukAwalLazy;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.model.administrator.daftarproduk.Produk;
import com.andreas.accounting.model.administrator.saldoawal.ProdukAwal;
import com.andreas.accounting.model.util.MataUang;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import com.andreas.accounting.service.administrator.daftarproduk.ProdukService;
import com.andreas.accounting.service.administrator.saldoawal.ProdukAwalService;
import com.andreas.accounting.service.util.ExchangeRatesService;
import com.andreas.accounting.service.util.MataUangService;
import com.andreas.accounting.util.BaseBeanInterface;
import com.andreas.accounting.util.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
@ManagedBean(name = "produkAwalBean")
@ViewScoped
public class ProdukAwalBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = -7117741740646907592L;

    private String pageName;
    private final String baseModule = "/modules/administrator/saldo-awal/persediaan/";

    private ProdukAwalLazy produkAwalModels;
    private ArrayList<Perusahaan> perusahaanModels;
    private ArrayList<Produk> produkModels;
    private ArrayList<MataUang> mataUangModels;
    private ProdukAwal produkAwalModel;
    private final ProdukAwalService produkAwalService = new ProdukAwalService();
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private final ProdukService produkService = new ProdukService();
    private final MataUangService mataUangService = new MataUangService();
    private final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
    private long produkAwalId;
    private long perusahaanId;
    private long perusahaanIdBefore;
    private long produkId;
    private long produkIdBefore;
    private long mataUangId;
    private long mataUangIdBefore;
    private boolean produkValid;
    private BigDecimal rate;
    private BigDecimal total;

    private Date currentDate = new Date();

    @Override
    public void init() {
        pageName = "Saldo Awal Persediaan";
    }

    @Override
    public void viewInput() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            produkAwalModel = new ProdukAwal();
            perusahaanIdBefore = 0;
            produkIdBefore = 0;
            mataUangId = ((MataUang) mataUangService.getIDR()).getId();
            mataUangIdBefore = 0;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            produkModels = (ArrayList<Produk>) produkService.listKode();
            mataUangModels = (ArrayList<MataUang>) mataUangService.listKode();
            produkValid = false;
            rate = new BigDecimal(1.0);
            produkAwalModel.setRate(new BigDecimal(1.0));
        }
    }

    @Override
    public void viewDetail(long id) {
        init();
        produkAwalModel = (ProdukAwal) produkAwalService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            produkAwalModel = (ProdukAwal) produkAwalService.get(id);
            perusahaanId = produkAwalModel.getPerusahaan().getId();
            perusahaanIdBefore = produkAwalModel.getPerusahaan().getId();
            produkId = produkAwalModel.getProduk().getId();
            produkIdBefore = produkAwalModel.getProduk().getId();
            mataUangId = produkAwalModel.getMataUang().getId();
            mataUangIdBefore = produkAwalModel.getMataUang().getId();
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            produkModels = (ArrayList<Produk>) produkService.listKode();
            mataUangModels = (ArrayList<MataUang>) mataUangService.listKode();
            produkValid = true;
            rate = exchangeRatesService.getRate(getKodeMataUang(mataUangId), produkAwalModel.getTanggal());
        }
    }

    @Override
    public void viewAll() {
        init();
        produkAwalModels = new ProdukAwalLazy();
        perusahaanId = 0;
        total = produkAwalService.getTotal(perusahaanId);
    }

    public void save() {
        String response;
        produkAwalModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        produkAwalModel.setProduk((Produk) produkService.get(produkId));
        produkAwalModel.setMataUang((MataUang) mataUangService.get(mataUangId));
        response = produkAwalService.save(produkAwalModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        produkAwalModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        produkAwalModel.setProduk((Produk) produkService.get(produkId));
        produkAwalModel.setMataUang((MataUang) mataUangService.get(mataUangId));
        response = produkAwalService.update(produkAwalModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void detail(long id) {
        Util.redirectToPage(baseModule + "detail.xhtml?id=" + id);
    }

    public void edit(long id) {
        Util.redirectToPage(baseModule + "edit.xhtml?id=" + id);
    }

    public void delete(long id) {
        produkAwalService.delete(id);
        list();
    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    public void checkProduk() {
        if (produkId == produkIdBefore) {
            produkValid = true;
        } else {
            produkValid = produkAwalService.checkProduk(produkId, perusahaanId);
        }
    }

    public void checkRates() {
        rate = exchangeRatesService.getRate(getKodeMataUang(mataUangId), produkAwalModel.getTanggal());
        produkAwalModel.setRate(rate);
    }

    public String getKodeMataUang(long mataUangId) {
        for (MataUang mataUang : mataUangModels) {
            if (mataUang.getId() == mataUangId) {
                return mataUang.getKode();
            }
        }

        return "";
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public LazyDataModel<ProdukAwal> getProdukAwalModels() {
        return produkAwalModels;
    }

    public void setProdukAwalModels(ProdukAwalLazy produkAwalModels) {
        this.produkAwalModels = produkAwalModels;
    }

    public ArrayList<Perusahaan> getPerusahaanModels() {
        return perusahaanModels;
    }

    public void setPerusahaanModels(ArrayList<Perusahaan> perusahaanModels) {
        this.perusahaanModels = perusahaanModels;
    }

    public ArrayList<Produk> getProdukModels() {
        return produkModels;
    }

    public void setProdukModels(ArrayList<Produk> produkModels) {
        this.produkModels = produkModels;
    }

    public ArrayList<MataUang> getMataUangModels() {
        return mataUangModels;
    }

    public void setMataUangModels(ArrayList<MataUang> mataUangModels) {
        this.mataUangModels = mataUangModels;
    }

    public ProdukAwal getProdukAwalModel() {
        return produkAwalModel;
    }

    public void setProdukAwalModel(ProdukAwal produkAwalModel) {
        this.produkAwalModel = produkAwalModel;
    }

    public long getProdukAwalId() {
        return produkAwalId;
    }

    public void setProdukAwalId(long produkAwalId) {
        this.produkAwalId = produkAwalId;
    }

    public long getPerusahaanId() {
        return perusahaanId;
    }

    public void setPerusahaanId(long perusahaanId) {
        this.perusahaanId = perusahaanId;
    }

    public long getPerusahaanIdBefore() {
        return perusahaanIdBefore;
    }

    public void setPerusahaanIdBefore(long perusahaanIdBefore) {
        this.perusahaanIdBefore = perusahaanIdBefore;
    }

    public long getProdukId() {
        return produkId;
    }

    public void setProdukId(long produkId) {
        this.produkId = produkId;
    }

    public long getProdukIdBefore() {
        return produkIdBefore;
    }

    public void setProdukIdBefore(long produkIdBefore) {
        this.produkIdBefore = produkIdBefore;
    }

    public long getMataUangId() {
        return mataUangId;
    }

    public void setMataUangId(long mataUangId) {
        this.mataUangId = mataUangId;
    }

    public long getMataUangIdBefore() {
        return mataUangIdBefore;
    }

    public void setMataUangIdBefore(long mataUangIdBefore) {
        this.mataUangIdBefore = mataUangIdBefore;
    }

    public boolean getProdukValid() {
        return produkValid;
    }

    public void setProdukValid(boolean produkValid) {
        this.produkValid = produkValid;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }
}
