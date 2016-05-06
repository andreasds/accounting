package com.andreas.accounting.bean.administrator.saldoawal;

import com.andreas.accounting.lazy.administator.saldoawal.HutangAwalLazy;
import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.model.administrator.saldoawal.InvoiceAwal;
import com.andreas.accounting.model.util.Invoice;
import com.andreas.accounting.model.util.MataUang;
import com.andreas.accounting.service.administrator.daftarnama.PenjualService;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import com.andreas.accounting.service.administrator.saldoawal.HutangAwalService;
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
@ManagedBean(name = "hutangAwalBean")
@ViewScoped
public class HutangAwalBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = -2546840710565627576L;

    private String pageName;
    private final String baseModule = "/modules/administrator/saldo-awal/hutang/";

    private HutangAwalLazy hutangAwalModels;
    private ArrayList<MataUang> mataUangModels;
    private ArrayList<Perusahaan> perusahaanModels;
    private ArrayList<Orang> penjualModels;
    private InvoiceAwal hutangAwalModel;
    private Invoice invoiceModel;
    private Perusahaan perusahaanModel;
    private final HutangAwalService hutangAwalService = new HutangAwalService();
    private final MataUangService mataUangService = new MataUangService();
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private final PenjualService penjualService = new PenjualService();
    private final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
    private long hutangAwalId;
    private long perusahaanId;
    private long perusahaanIdBefore;
    private long pemilikId;
    private long penjualId;
    private long penjualIdBefore;
    private String noBefore;
    private boolean noValid;
    private long mataUangId;
    private long mataUangIdBefore;
    private BigDecimal rate;
    private BigDecimal total;

    private Date currentDate = new Date();

    @Override
    public void init() {
        pageName = "Hutang Awal";
    }

    @Override
    public void viewInput() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            hutangAwalModel = new InvoiceAwal();
            invoiceModel = new Invoice();
            perusahaanIdBefore = 0;
            penjualIdBefore = 0;
            noBefore = "";
            mataUangId = ((MataUang) mataUangService.getIDR()).getId();
            mataUangIdBefore = 0;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            penjualModels = (ArrayList<Orang>) penjualService.listNama();
            mataUangModels = (ArrayList<MataUang>) mataUangService.listKode();
            noValid = false;
            rate = new BigDecimal(1.0);
            invoiceModel.setRate(new BigDecimal(1.0));
        }
    }

    public void viewDetailPerusahaan(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pemilikId = 0;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            hutangAwalModels = new HutangAwalLazy(id, pemilikId);
            perusahaanModel = (Perusahaan) perusahaanService.get(id);
            total = hutangAwalService.getTotal(perusahaanId, pemilikId);
        }
    }

    @Override
    public void viewDetail(long id) {
        init();
        hutangAwalModel = (InvoiceAwal) hutangAwalService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            hutangAwalModel = (InvoiceAwal) hutangAwalService.get(id);
            invoiceModel = hutangAwalModel.getInvoice();
            perusahaanId = hutangAwalModel.getInvoice().getPerusahaan().getId();
            perusahaanIdBefore = hutangAwalModel.getInvoice().getPerusahaan().getId();
            penjualId = hutangAwalModel.getInvoice().getOrang().getId();
            penjualIdBefore = hutangAwalModel.getInvoice().getOrang().getId();
            mataUangId = hutangAwalModel.getInvoice().getMataUang().getId();
            mataUangIdBefore = hutangAwalModel.getInvoice().getMataUang().getId();
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            penjualModels = (ArrayList<Orang>) penjualService.listNama();
            mataUangModels = (ArrayList<MataUang>) mataUangService.listKode();
            noValid = true;
            rate = exchangeRatesService.getRate(getKodeMataUang(mataUangId), hutangAwalModel.getInvoice().getTanggal());
        }
    }

    @Override
    public void viewAll() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            perusahaanId = 0;
            pemilikId = 0;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            hutangAwalModels = new HutangAwalLazy(perusahaanId, pemilikId);
            total = hutangAwalService.getTotal(perusahaanId, pemilikId);
        }
    }

    public void save() {
        String response;
        invoiceModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        invoiceModel.setOrang((Orang) penjualService.get(penjualId));
        invoiceModel.setMataUang((MataUang) mataUangService.get(mataUangId));

        hutangAwalModel.setInvoice(invoiceModel);
        response = hutangAwalService.save(hutangAwalModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        invoiceModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        invoiceModel.setOrang((Orang) penjualService.get(penjualId));
        invoiceModel.setMataUang((MataUang) mataUangService.get(mataUangId));

        hutangAwalModel.setInvoice(invoiceModel);
        response = hutangAwalService.update(hutangAwalModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void detailPerusahaan(long id) {
        Util.redirectToPage(baseModule + "details.xhtml?id=" + id);
    }

    public void detail(long id) {
        Util.redirectToPage(baseModule + "detail.xhtml?id=" + id);
    }

    public void edit(long id) {
        Util.redirectToPage(baseModule + "edit.xhtml?id=" + id);
    }

    public void delete(long id) {
        hutangAwalService.delete(id);
        detailPerusahaan(perusahaanId);
    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    public void refreshList() {
        hutangAwalModels = new HutangAwalLazy(perusahaanId, pemilikId);
        total = hutangAwalService.getTotal(perusahaanId, pemilikId);
    }

    public void checkNo() {
        if (invoiceModel.getNo().equalsIgnoreCase(noBefore)
                && perusahaanId == perusahaanIdBefore) {
            noValid = true;
        } else {
            noValid = hutangAwalService.checkNo(invoiceModel.getNo(), perusahaanId);
        }
    }

    public void checkRates() {
        rate = exchangeRatesService.getRate(getKodeMataUang(mataUangId), invoiceModel.getTanggal());
        invoiceModel.setRate(rate);
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

    public LazyDataModel<InvoiceAwal> getHutangAwalModels() {
        return hutangAwalModels;
    }

    public void setHutangAwalModels(HutangAwalLazy hutangAwalModels) {
        this.hutangAwalModels = hutangAwalModels;
    }

    public ArrayList<MataUang> getMataUangModels() {
        return mataUangModels;
    }

    public void setMataUangModels(ArrayList<MataUang> mataUangModels) {
        this.mataUangModels = mataUangModels;
    }

    public ArrayList<Perusahaan> getPerusahaanModels() {
        return perusahaanModels;
    }

    public void setPerusahaanModels(ArrayList<Perusahaan> perusahaanModels) {
        this.perusahaanModels = perusahaanModels;
    }

    public ArrayList<Orang> getPenjualModels() {
        return penjualModels;
    }

    public void setPenjualModels(ArrayList<Orang> penjualModels) {
        this.penjualModels = penjualModels;
    }

    public InvoiceAwal getHutangAwalModel() {
        return hutangAwalModel;
    }

    public void setHutangAwalModel(InvoiceAwal hutangAwalModel) {
        this.hutangAwalModel = hutangAwalModel;
    }

    public Invoice getInvoiceModel() {
        return invoiceModel;
    }

    public void setInvoiceModel(Invoice invoiceModel) {
        this.invoiceModel = invoiceModel;
    }

    public Perusahaan getPerusahaanModel() {
        return perusahaanModel;
    }

    public void setPerusahaanModel(Perusahaan perusahaanModel) {
        this.perusahaanModel = perusahaanModel;
    }

    public long getHutangAwalId() {
        return hutangAwalId;
    }

    public void setHutangAwalId(long hutangAwalId) {
        this.hutangAwalId = hutangAwalId;
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

    public long getPemilikId() {
        return pemilikId;
    }

    public void setPemilikId(long pemilikId) {
        this.pemilikId = pemilikId;
    }

    public long getPenjualId() {
        return penjualId;
    }

    public void setPenjualId(long penjualId) {
        this.penjualId = penjualId;
    }

    public long getPenjualIdBefore() {
        return penjualIdBefore;
    }

    public void setPenjualIdBefore(long penjualIdBefore) {
        this.penjualIdBefore = penjualIdBefore;
    }

    public String getNoBefore() {
        return noBefore;
    }

    public void setNoBefore(String noBefore) {
        this.noBefore = noBefore;
    }

    public boolean getNoValid() {
        return noValid;
    }

    public void setNoValid(boolean noValid) {
        this.noValid = noValid;
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
