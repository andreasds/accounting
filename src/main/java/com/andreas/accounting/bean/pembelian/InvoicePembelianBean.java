package com.andreas.accounting.bean.pembelian;

import com.andreas.accounting.lazy.pembelian.InvoicePembelianLazy;
import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.model.administrator.daftarproduk.Produk;
import com.andreas.accounting.model.util.Invoice;
import com.andreas.accounting.model.util.MataUang;
import com.andreas.accounting.model.util.ProdukInvoice;
import com.andreas.accounting.service.administrator.daftarnama.PenjualService;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import com.andreas.accounting.service.administrator.daftarproduk.ProdukService;
import com.andreas.accounting.service.pembelian.InvoicePembelianService;
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
@ManagedBean(name = "invoicePembelianBean")
@ViewScoped
public class InvoicePembelianBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = 1462945210158159943L;

    private String pageName;
    private final String baseModule = "/modules/pembelian/invoice/";

    private InvoicePembelianLazy invoiceModels;
    private ArrayList<Perusahaan> perusahaanModels;
    private ArrayList<Orang> penjualModels;
    private ArrayList<Produk> produkModels;
    private ArrayList<MataUang> mataUangModels;
    private Invoice invoiceModel;
    private ProdukInvoice produkInvoiceModel;
    private final InvoicePembelianService invoicePembelianService = new InvoicePembelianService();
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private final PenjualService penjualService = new PenjualService();
    private final ProdukService produkService = new ProdukService();
    private final MataUangService mataUangService = new MataUangService();
    private final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
    private long invoiceId;
    private long pemilikId;
    private long pemilikIdBefore;
    private long penjualId;
    private long penjualIdBefore;
    private String noBefore;
    private boolean noValid;
    private long produkId;
    private long produkIdBefore;
    private boolean produkValid;
    private long mataUangId;
    private long mataUangIdBefore;
    private BigDecimal rate;

    private Date currentDate = new Date();
    private int produkInvoiceIndex;
    private ArrayList<ProdukInvoice> produkInvoicesDeleted;

    @Override
    public void init() {
        pageName = "Invoice Pembelian";
    }

    @Override
    public void viewInput() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            penjualModels = (ArrayList<Orang>) penjualService.listNama();
            produkModels = (ArrayList<Produk>) produkService.listKode();
            mataUangModels = (ArrayList<MataUang>) mataUangService.listKode();
            invoiceModel = new Invoice();
            pemilikIdBefore = 0;
            penjualIdBefore = 0;
            noBefore = "";
            noValid = false;
            mataUangId = ((MataUang) mataUangService.getIDR()).getId();
            mataUangIdBefore = 0;
            rate = new BigDecimal(1.0);
            invoiceModel.setRate(new BigDecimal(1.0));
            produkInvoicesDeleted = new ArrayList<>();
            refreshProduk();
        }
    }

    @Override
    public void viewDetail(long id) {
        init();
        invoiceModel = (Invoice) invoicePembelianService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            penjualModels = (ArrayList<Orang>) penjualService.listNama();
            produkModels = (ArrayList<Produk>) produkService.listKode();
            mataUangModels = (ArrayList<MataUang>) mataUangService.listKode();
            invoiceModel = (Invoice) invoicePembelianService.get(id);
            pemilikId = invoiceModel.getPerusahaan().getId();
            pemilikIdBefore = invoiceModel.getPerusahaan().getId();
            penjualId = invoiceModel.getOrang().getId();
            penjualIdBefore = invoiceModel.getOrang().getId();
            noBefore = invoiceModel.getNo();
            noValid = true;
            mataUangId = invoiceModel.getMataUang().getId();
            mataUangIdBefore = invoiceModel.getMataUang().getId();
            rate = exchangeRatesService.getRate(getKodeMataUang(mataUangId), invoiceModel.getTanggal());
            invoiceModel.setRate(new BigDecimal(1.0));
            produkInvoicesDeleted = new ArrayList<>();
            refreshProduk();
        }
    }

    @Override
    public void viewAll() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pemilikId = 0;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            invoiceModels = new InvoicePembelianLazy(pemilikId);
        }
    }

    public void save() {
        String response;
        invoiceModel.setPerusahaan((Perusahaan) perusahaanService.get(pemilikId));
        invoiceModel.setOrang((Orang) penjualService.get(penjualId));
        invoiceModel.setMataUang((MataUang) mataUangService.get(mataUangId));
        response = invoicePembelianService.save(invoiceModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        invoiceModel.setPerusahaan((Perusahaan) perusahaanService.get(pemilikId));
        invoiceModel.setOrang((Orang) penjualService.get(penjualId));
        invoiceModel.setMataUang((MataUang) mataUangService.get(mataUangId));
        ArrayList<ProdukInvoice> temp = invoiceModel.getProdukInvoices();
        produkInvoicesDeleted.stream().forEach((produkInvoice) -> {
            temp.add(produkInvoice);
        });
        invoiceModel.setProdukInvoices(temp);
        response = invoicePembelianService.update(invoiceModel).toString();

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
        invoicePembelianService.delete(id);
        list();
    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    public void refreshList() {
        invoiceModels = new InvoicePembelianLazy(pemilikId);
    }

    public void checkNo() {
        if (invoiceModel.getNo().equalsIgnoreCase(noBefore)
                && pemilikId == pemilikIdBefore) {
            noValid = true;
        } else {
            noValid = invoicePembelianService.checkNo(invoiceModel.getNo(), pemilikId);
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
        long produkInvoiceId = produkInvoiceModel.getId();
        if (produkInvoiceIndex >= 0) {
            temp.remove(produkInvoiceIndex);
        } else {
            int indexTemp = -1;
            for (ProdukInvoice produkInvoice : produkInvoicesDeleted) {
                if (produkInvoice.getProduk().getId() == produkId) {
                    produkInvoiceId = produkInvoice.getId();
                    indexTemp = produkInvoicesDeleted.indexOf(produkInvoice);
                }
            }
            if (indexTemp >= 0) {
                produkInvoicesDeleted.remove(indexTemp);
            }
        }
        produkInvoiceModel.setId(produkInvoiceId);
        produkInvoiceModel.setProduk(getProduk());
        temp.add(produkInvoiceModel);
        invoiceModel.setProdukInvoices(temp);
        refreshProduk();
    }

    public void editProduk(int index) {
        produkInvoiceIndex = index;
        ArrayList<ProdukInvoice> temp = invoiceModel.getProdukInvoices();
        produkInvoiceModel = temp.get(index);
        produkId = produkInvoiceModel.getProduk().getId();
        produkIdBefore = produkInvoiceModel.getProduk().getId();
        produkValid = true;
        rate = exchangeRatesService.getRate(getKodeMataUang(mataUangId), invoiceModel.getTanggal());
    }

    public void deleteProduk(int index) {
        ArrayList<ProdukInvoice> temp = invoiceModel.getProdukInvoices();
        produkInvoiceModel = temp.get(index);
        if (produkInvoiceModel.getId() != 0) {
            produkInvoiceModel.setRemoved(true);
            produkInvoicesDeleted.add(produkInvoiceModel);
        }
        temp.remove(index);
        invoiceModel.setProdukInvoices(temp);
        refreshProduk();
    }

    public void refreshProduk() {
        produkInvoiceModel = new ProdukInvoice();
        produkId = 0;
        produkIdBefore = 0;
        produkValid = false;
        produkInvoiceIndex = -1;
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

    public void setInvoiceModels(InvoicePembelianLazy invoiceModels) {
        this.invoiceModels = invoiceModels;
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
