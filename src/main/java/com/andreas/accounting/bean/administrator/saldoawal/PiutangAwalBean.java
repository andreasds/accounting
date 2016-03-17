package com.andreas.accounting.bean.administrator.saldoawal;

import com.andreas.accounting.lazy.administator.saldoawal.PiutangAwalLazy;
import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.model.administrator.saldoawal.InvoiceAwal;
import com.andreas.accounting.model.util.Invoice;
import com.andreas.accounting.model.util.MataUang;
import com.andreas.accounting.service.administrator.daftarnama.PembeliService;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import com.andreas.accounting.service.administrator.saldoawal.PiutangAwalService;
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
@ManagedBean(name = "piutangAwalBean")
@ViewScoped
public class PiutangAwalBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = -4367980476989176851L;

    private String pageName;
    private final String baseModule = "/modules/administrator/saldo-awal/piutang/";

    private PiutangAwalLazy piutangAwalModels;
    private ArrayList<MataUang> mataUangModels;
    private ArrayList<Perusahaan> perusahaanModels;
    private ArrayList<Orang> pembeliModels;
    private InvoiceAwal piutangAwalModel;
    private Invoice invoiceModel;
    private final PiutangAwalService piutangAwalService = new PiutangAwalService();
    private final MataUangService mataUangService = new MataUangService();
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private final PembeliService pembeliService = new PembeliService();
    private final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
    private long piutangAwalId;
    private long perusahaanId;
    private long perusahaanIdBefore;
    private long pembeliId;
    private long pembeliIdBefore;
    private String noBefore;
    private boolean noValid;
    private long mataUangId;
    private long mataUangIdBefore;
    private BigDecimal rate;
    private BigDecimal total;

    private Date currentDate = new Date();

    @Override
    public void init() {
        pageName = "Piutang Awal";
    }

    @Override
    public void viewInput() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            piutangAwalModel = new InvoiceAwal();
            invoiceModel = new Invoice();
            perusahaanIdBefore = 0;
            pembeliIdBefore = 0;
            noBefore = "";
            mataUangId = ((MataUang) mataUangService.getIDR()).getId();
            mataUangIdBefore = 0;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            pembeliModels = (ArrayList<Orang>) pembeliService.listNama();
            mataUangModels = (ArrayList<MataUang>) mataUangService.listKode();
            noValid = false;
            rate = new BigDecimal(1.0);
            piutangAwalModel.setRate(new BigDecimal(1.0));
        }
    }

    @Override
    public void viewDetail(long id) {
        init();
        piutangAwalModel = (InvoiceAwal) piutangAwalService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            piutangAwalModel = (InvoiceAwal) piutangAwalService.get(id);
            invoiceModel = piutangAwalModel.getInvoice();
            perusahaanId = piutangAwalModel.getInvoice().getPerusahaan().getId();
            perusahaanIdBefore = piutangAwalModel.getInvoice().getPerusahaan().getId();
            pembeliId = piutangAwalModel.getInvoice().getOrang().getId();
            pembeliIdBefore = piutangAwalModel.getInvoice().getOrang().getId();
            mataUangId = piutangAwalModel.getMataUang().getId();
            mataUangIdBefore = piutangAwalModel.getMataUang().getId();
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            pembeliModels = (ArrayList<Orang>) pembeliService.listNama();
            mataUangModels = (ArrayList<MataUang>) mataUangService.listKode();
            noValid = true;
            rate = exchangeRatesService.getRate(getKodeMataUang(mataUangId), piutangAwalModel.getInvoice().getTanggal());
        }
    }

    @Override
    public void viewAll() {
        init();
        piutangAwalModels = new PiutangAwalLazy();
        perusahaanId = 0;
        total = piutangAwalService.getTotal(perusahaanId);
    }

    public void save() {
        String response;
        invoiceModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        invoiceModel.setOrang((Orang) pembeliService.get(pembeliId));

        piutangAwalModel.setInvoice(invoiceModel);
        piutangAwalModel.setMataUang((MataUang) mataUangService.get(mataUangId));
        response = piutangAwalService.save(piutangAwalModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        invoiceModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        invoiceModel.setOrang((Orang) pembeliService.get(pembeliId));

        piutangAwalModel.setInvoice(invoiceModel);
        piutangAwalModel.setMataUang((MataUang) mataUangService.get(mataUangId));
        response = piutangAwalService.update(piutangAwalModel).toString();

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
        piutangAwalService.delete(id);
        list();
    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    public void checkNo() {
        if (invoiceModel.getNo().equalsIgnoreCase(noBefore)
                && perusahaanId == perusahaanIdBefore) {
            noValid = true;
        } else {
            noValid = piutangAwalService.checkNo(invoiceModel.getNo(), perusahaanId);
        }
    }

    public void checkRates() {
        rate = exchangeRatesService.getRate(getKodeMataUang(mataUangId), invoiceModel.getTanggal());
        piutangAwalModel.setRate(rate);
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

    public LazyDataModel<InvoiceAwal> getPiutangAwalModels() {
        return piutangAwalModels;
    }

    public void setPiutangAwalModels(PiutangAwalLazy piutangAwalModels) {
        this.piutangAwalModels = piutangAwalModels;
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

    public ArrayList<Orang> getPembeliModels() {
        return pembeliModels;
    }

    public void setPembeliModels(ArrayList<Orang> pembeliModels) {
        this.pembeliModels = pembeliModels;
    }

    public InvoiceAwal getPiutangAwalModel() {
        return piutangAwalModel;
    }

    public void setPiutangAwalModel(InvoiceAwal piutangAwalModel) {
        this.piutangAwalModel = piutangAwalModel;
    }

    public Invoice getInvoiceModel() {
        return invoiceModel;
    }

    public void setInvoiceModel(Invoice invoiceModel) {
        this.invoiceModel = invoiceModel;
    }

    public long getPiutangAwalId() {
        return piutangAwalId;
    }

    public void setPiutangAwalId(long piutangAwalId) {
        this.piutangAwalId = piutangAwalId;
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

    public long getPembeliId() {
        return pembeliId;
    }

    public void setPembeliId(long pembeliId) {
        this.pembeliId = pembeliId;
    }

    public long getPembeliIdBefore() {
        return pembeliIdBefore;
    }

    public void setPembeliIdBefore(long pembeliIdBefore) {
        this.pembeliIdBefore = pembeliIdBefore;
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
