package com.andreas.accounting.bean.penjualan;

import com.andreas.accounting.lazy.penjualan.InvoicePenjualanLazy;
import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.model.administrator.daftarproduk.Produk;
import com.andreas.accounting.model.util.Invoice;
import com.andreas.accounting.model.util.MataUang;
import com.andreas.accounting.model.util.ProdukInvoice;
import com.andreas.accounting.service.administrator.daftarnama.PembeliService;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import com.andreas.accounting.service.administrator.daftarproduk.ProdukService;
import com.andreas.accounting.service.penjualan.InvoicePenjualanService;
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
@ManagedBean(name = "invoicePenjualanBean")
@ViewScoped
public class InvoicePenjualanBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = 1462945210158159943L;

    private String pageName;
    private final String baseModule = "/modules/penjualan/invoice/";

    private InvoicePenjualanLazy invoiceModels;
    private ArrayList<Perusahaan> perusahaanModels;
    private ArrayList<Orang> pembeliModels;
    private ArrayList<Produk> produkModels;
    private ArrayList<MataUang> mataUangModels;
    private Invoice invoiceModel;
    private ProdukInvoice produkInvoiceModel;
    private ArrayList<ProdukInvoice> produkInvoices;
    private final InvoicePenjualanService invoicePenjualanService = new InvoicePenjualanService();
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private final PembeliService pembeliService = new PembeliService();
    private final ProdukService produkService = new ProdukService();
    private final MataUangService mataUangService = new MataUangService();
    private final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
    private long invoiceId;
    private long pemilikId;
    private long pemilikIdBefore;
    private long pembeliId;
    private long pembeliIdBefore;
    private String noBefore;
    private boolean noValid;
    private long produkId;
    private long produkIdBefore;
    private boolean produkValid;
    private long mataUangId;
    private long mataUangIdBefore;
    private BigDecimal rate;

    private Date currentDate = new Date();

    @Override
    public void init() {
        pageName = "Invoice Penjualan";
    }

    @Override
    public void viewInput() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            pembeliModels = (ArrayList<Orang>) pembeliService.listNama();
            produkModels = (ArrayList<Produk>) produkService.listKode();
            mataUangModels = (ArrayList<MataUang>) mataUangService.listKode();
            invoiceModel = new Invoice();
            produkInvoices = new ArrayList<>();
            pemilikIdBefore = 0;
            pembeliIdBefore = 0;
            noBefore = "";
            noValid = false;
            refreshProduk();
        }
    }

    @Override
    public void viewDetail(long id) {
        init();
    }

    @Override
    public void viewEdit(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {

        }
    }

    @Override
    public void viewAll() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {

        }
    }

    public void save() {

    }

    public void update() {

    }

    public void detail(long id) {
        Util.redirectToPage(baseModule + "detail.xhtml?id=" + id);
    }

    public void edit(long id) {
        Util.redirectToPage(baseModule + "edit.xhtml?id=" + id);
    }

    public void delete(long id) {

    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    public void checkNo() {
        if (invoiceModel.getNo().equalsIgnoreCase(noBefore)
                && pemilikId == pemilikIdBefore) {
            noValid = true;
        } else {
            noValid = invoicePenjualanService.checkNo(invoiceModel.getNo(), pemilikId);
        }
    }

    public void checkProduk() {
        if (produkId == produkIdBefore) {
            produkValid = true;
        } else {
            produkValid = true;
            ArrayList<ProdukInvoice> temp = invoiceModel.getProdukInvoices();
            for (ProdukInvoice produkInvoice : temp) {
                if (produkInvoice.getProduk().getId() == produkId) {
                    produkValid = false;
                    break;
                }
            }
        }
    }

    public void addProduk() {
        ArrayList<ProdukInvoice> temp = invoiceModel.getProdukInvoices();
        produkInvoiceModel.setProduk(getProduk());
        produkInvoiceModel.setMataUang(getMataUang());
        temp.add(produkInvoiceModel);
        invoiceModel.setProdukInvoices(temp);
    }

    public void deleteProduk(int index) {
        ArrayList<ProdukInvoice> temp = invoiceModel.getProdukInvoices();
        temp.remove(index);
        invoiceModel.setProdukInvoices(temp);
    }

    public void refreshProduk() {
        produkInvoiceModel = new ProdukInvoice();
        produkId = 0;
        produkIdBefore = 0;
        produkValid = false;
        mataUangId = ((MataUang) mataUangService.getIDR()).getId();
        mataUangIdBefore = 0;
        rate = new BigDecimal(1.0);
        produkInvoiceModel.setRate(new BigDecimal(1.0));
    }

    public void checkRates() {
        rate = exchangeRatesService.getRate(getKodeMataUang(mataUangId), invoiceModel.getTanggal());
        produkInvoiceModel.setRate(rate);
    }

    public String getKodeMataUang(long mataUangId) {
        for (MataUang mataUang : mataUangModels) {
            if (mataUang.getId() == mataUangId) {
                return mataUang.getKode();
            }
        }

        return "";
    }

    public Produk getProduk() {
        for (Produk produk : produkModels) {
            if (produk.getId() == produkId) {
                return produk;
            }
        }
        return null;
    }

    public MataUang getMataUang() {
        for (MataUang mataUang : mataUangModels) {
            if (mataUang.getId() == mataUangId) {
                return mataUang;
            }
        }
        return null;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public LazyDataModel<Invoice> getInvoiceModels() {
        return invoiceModels;
    }

    public void setInvoiceModels(InvoicePenjualanLazy invoiceModels) {
        this.invoiceModels = invoiceModels;
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

    public Invoice getInvoiceModel() {
        return invoiceModel;
    }

    public void setInvoiceModel(Invoice invoiceModel) {
        this.invoiceModel = invoiceModel;
    }

    public ProdukInvoice getProdukInvoiceModel() {
        return produkInvoiceModel;
    }

    public void setProdukInvoiceModel(ProdukInvoice produkInvoiceModel) {
        this.produkInvoiceModel = produkInvoiceModel;
    }

    public ArrayList<ProdukInvoice> getProdukInvoices() {
        return produkInvoices;
    }

    public void setProdukInvoices(ArrayList<ProdukInvoice> produkInvoices) {
        this.produkInvoices = produkInvoices;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getPemilikId() {
        return pemilikId;
    }

    public void setPemilikId(long pemilikId) {
        this.pemilikId = pemilikId;
    }

    public long getPemilikIdBefore() {
        return pemilikIdBefore;
    }

    public void setPemilikIdBefore(long pemilikIdBefore) {
        this.pemilikIdBefore = pemilikIdBefore;
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

    public boolean getProdukValid() {
        return produkValid;
    }

    public void setProdukValid(boolean produkValid) {
        this.produkValid = produkValid;
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

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }
}
