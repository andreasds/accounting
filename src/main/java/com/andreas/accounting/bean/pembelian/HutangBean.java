package com.andreas.accounting.bean.pembelian;

import com.andreas.accounting.lazy.pembelian.HutangBayarLazy;
import com.andreas.accounting.lazy.pembelian.HutangLazy;
import com.andreas.accounting.model.administrator.Rekening;
import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.model.util.HutangPiutang;
import com.andreas.accounting.model.util.Invoice;
import com.andreas.accounting.model.util.MataUang;
import com.andreas.accounting.model.util.Pembayaran;
import com.andreas.accounting.model.util.PembayaranHutangPiutang;
import com.andreas.accounting.service.administrator.RekeningService;
import com.andreas.accounting.service.administrator.daftarnama.PenjualService;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import com.andreas.accounting.service.pembelian.HutangService;
import com.andreas.accounting.service.pembelian.InvoicePembelianService;
import com.andreas.accounting.service.util.ExchangeRatesService;
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
@ManagedBean(name = "hutangBean")
@ViewScoped
public class HutangBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = -926094607091815501L;

    private String pageName;
    private final String baseModule = "/modules/pembelian/hutang/";

    private HutangLazy hutangModels;
    private HutangBayarLazy pembayaranModels;
    private ArrayList<Perusahaan> perusahaanModels;
    private ArrayList<Orang> penjualModels;
    private ArrayList<Rekening> rekeningModels;
    private ArrayList<Invoice> invoiceModels;
    private Orang penjualModel;
    private Pembayaran pembayaranModel;
    private PembayaranHutangPiutang pembayaranHutangModel;
    private final HutangService hutangService = new HutangService();
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private final PenjualService penjualService = new PenjualService();
    private final RekeningService rekeningService = new RekeningService();
    private final InvoicePembelianService invoicePembelianService = new InvoicePembelianService();
    private final ExchangeRatesService exchangeRatesService = new ExchangeRatesService();
    private long pembayaranId;
    private long pemilikId;
    private long pemilikIdBefore;
    private long penjualId;
    private long penjualIdBefore;
    private long penjualIdTemp;
    private long rekeningId;
    private long rekeningIdBefore;
    private long invoiceId;
    private long invoiceIdBefore;
    private boolean invoiceValid;
    private boolean bayarValid;
    private BigDecimal total;
    private BigDecimal totalBayar;
    private BigDecimal rate;

    private Date currentDate = new Date();
    private int invoiceIndex;

    @Override
    public void init() {
        pageName = "Hutang";
    }

    @Override
    public void viewInput() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            penjualModels = (ArrayList<Orang>) penjualService.listNamaHutang(0, pemilikId);
            rekeningModels = (ArrayList<Rekening>) rekeningService.listNama();
            invoiceModels = (ArrayList<Invoice>) invoicePembelianService.listHutang(0, penjualId, pemilikId);
            pembayaranHutangModel = new PembayaranHutangPiutang();
            pemilikIdBefore = 0;
            penjualIdBefore = 0;
            rekeningIdBefore = 0;
            calculateTotalBayar();
            refreshInvoice();
        }
    }

    public void viewDetailOrang(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pemilikId = 0;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            hutangModels = new HutangLazy(id, pemilikId);
            penjualModel = (Orang) penjualService.get(id);
            total = hutangService.getTotal(penjualId, pemilikId);
        }
    }

    @Override
    public void viewDetail(long id) {
        init();
        pembayaranModel = (Pembayaran) hutangService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pembayaranModel = (Pembayaran) hutangService.get(id);
            pemilikId = pembayaranModel.getInvoice().getPerusahaan().getId();
            pemilikIdBefore = pembayaranModel.getInvoice().getPerusahaan().getId();
            penjualId = pembayaranModel.getInvoice().getOrang().getId();
            penjualIdBefore = pembayaranModel.getInvoice().getOrang().getId();
            penjualIdTemp = pembayaranModel.getInvoice().getOrang().getId();
            rekeningId = pembayaranModel.getRekening().getId();
            rekeningIdBefore = pembayaranModel.getRekening().getId();
            invoiceId = pembayaranModel.getInvoice().getId();
            invoiceIdBefore = pembayaranModel.getInvoice().getId();
            invoiceValid = true;
            bayarValid = true;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            penjualModels = (ArrayList<Orang>) penjualService.listNamaHutang(penjualIdTemp, pemilikId);
            rekeningModels = (ArrayList<Rekening>) rekeningService.listNama();
            invoiceModels = (ArrayList<Invoice>) invoicePembelianService.listHutang(pembayaranId, penjualId, pemilikId);
            rate = exchangeRatesService.getRate(pembayaranModel.getMataUang().getKode(), pembayaranModel.getTanggal());
        }
    }

    @Override
    public void viewAll() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pemilikId = 0;
            penjualId = 0;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            hutangModels = new HutangLazy(penjualId, pemilikId);
            total = hutangService.getTotal(penjualId, pemilikId);
        }
    }

    public void viewBayar() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pemilikId = 0;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            pembayaranModels = new HutangBayarLazy(pemilikId);
        }
    }

    public void save() {
        String response;
        Rekening rekeningTemp = new Rekening();
        rekeningTemp.setId(rekeningId);
        pembayaranHutangModel.setRekening(rekeningTemp);
        response = hutangService.save(pembayaranHutangModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "listBayar.xhtml");
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        Invoice invoiceTemp = new Invoice();
        invoiceTemp.setId(invoiceId);
        pembayaranModel.setInvoice(invoiceTemp);
        Rekening rekeningTemp = new Rekening();
        rekeningTemp.setId(rekeningId);
        pembayaranModel.setRekening(rekeningTemp);
        String response = hutangService.update(pembayaranModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void detailOrang(long id) {
        Util.redirectToPage(baseModule + "details.xhtml?id=" + id);
    }

    public void detailInvoice(long id) {
        Util.redirectToPage(baseModule + "detailInvoice.xhtml?id=" + id);
    }

    public void detail(long id) {
        Util.redirectToPage(baseModule + "detail.xhtml?id=" + id);
    }

    public void edit(long id) {
        Util.redirectToPage(baseModule + "edit.xhtml?id=" + id);
    }

    public void delete(long id) {
        hutangService.delete(id);
        listBayar();
    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    public void listBayar() {
        Util.redirectToPage(baseModule + "listBayar.xhtml");
    }

    public void refreshList() {
        hutangModels = new HutangLazy(penjualId, pemilikId);
    }

    public void refreshListPenjual() {
        penjualId = 0;
        penjualModels = (ArrayList<Orang>) penjualService.listNamaHutang(penjualIdTemp, pemilikId);
        refreshListInvoice();
    }

    public void refreshListBayar() {
        pembayaranModels = new HutangBayarLazy(pemilikId);
    }

    public void refreshListInvoice() {
        invoiceModels = (ArrayList<Invoice>) invoicePembelianService.listHutang(pembayaranId, penjualId, pemilikId);
        invoiceId = 0;

        if (pembayaranId > 0) {
            pembayaranModel.setMataUang(new MataUang());
            pembayaranModel.setJumlah(new BigDecimal(0.0));
            pembayaranModel.setRate(new BigDecimal(1.0));
            rate = new BigDecimal(1.0);
            pembayaranModel.setBayar(new BigDecimal(0.0));
        } else {
            pembayaranHutangModel.setPembayarans(new ArrayList<>());
            calculateTotalBayar();
        }
    }

    public void checkInvoice() {
        if (invoiceId == invoiceIdBefore) {
            invoiceValid = true;
        } else {
            invoiceValid = true;
            ArrayList<Pembayaran> temp = pembayaranHutangModel.getPembayarans();
            for (Pembayaran pembayaran : temp) {
                if (pembayaran.getInvoice().getId() == invoiceId) {
                    invoiceValid = false;
                    break;
                }
            }
        }
        refreshInvoiceDetail();
    }

    public void refreshInvoiceDetail() {
        if (invoiceValid && invoiceId != 0) {
            if (invoiceId == invoiceIdBefore) {
                pembayaranModel = (Pembayaran) hutangService.get(pembayaranId);
                rate = exchangeRatesService.getRate(pembayaranModel.getMataUang().getKode(), pembayaranModel.getTanggal());
            } else {
                pembayaranModel.setMataUang(getInvoice().getMataUang());
                pembayaranModel.setJumlah(getInvoice().getJumlah());
                checkRates();
                pembayaranModel.setBayar(getInvoice().getJumlah());
            }
            bayarValid = true;
        } else {
            pembayaranModel.setMataUang(new MataUang());
            rate = new BigDecimal(1.0);
            pembayaranModel.setRate(new BigDecimal(1.0));
            pembayaranModel.setJumlah(new BigDecimal(0.0));
            pembayaranModel.setBayar(new BigDecimal(0.0));
            bayarValid = false;
        }
    }

    public void addInvoice() {
        ArrayList<Pembayaran> temp = pembayaranHutangModel.getPembayarans();
        if (invoiceIndex >= 0) {
            temp.remove(invoiceIndex);
        }
        pembayaranModel.setInvoice(getInvoice());
        temp.add(pembayaranModel);
        pembayaranHutangModel.setPembayarans(temp);
        refreshInvoice();
    }

    public void editInvoice(int index) {
        invoiceIndex = index;
        ArrayList<Pembayaran> temp = pembayaranHutangModel.getPembayarans();
        pembayaranModel = temp.get(index);
        invoiceId = pembayaranModel.getInvoice().getId();
        invoiceIdBefore = pembayaranModel.getInvoice().getId();
        invoiceValid = true;
        rate = exchangeRatesService.getRate(pembayaranModel.getMataUang().getKode(), pembayaranModel.getTanggal());
    }

    public void deleteInvoice(int index) {
        ArrayList<Pembayaran> temp = pembayaranHutangModel.getPembayarans();
        temp.remove(index);
        pembayaranHutangModel.setPembayarans(temp);
        refreshInvoice();
    }

    public void refreshInvoice() {
        pembayaranModel = new Pembayaran();
        invoiceId = 0;
        invoiceIdBefore = 0;
        invoiceValid = false;
        bayarValid = false;
        rate = new BigDecimal(1.0);
        calculateTotalBayar();
        invoiceIndex = -1;
    }

    public void checkRates() {
        rate = exchangeRatesService.getRate(pembayaranModel.getMataUang().getKode(), pembayaranModel.getTanggal());
        pembayaranModel.setRate(rate);
    }

    public void checkBayar() {
        bayarValid = (pembayaranModel.getJumlah().subtract(pembayaranModel.getBayar()).intValue() >= 0);
    }

    public Invoice getInvoice() {
        for (Invoice invoice : invoiceModels) {
            if (invoice.getId() == invoiceId) {
                return invoice;
            }
        }
        return null;
    }

    private void calculateTotalBayar() {
        ArrayList<Pembayaran> temp = pembayaranHutangModel.getPembayarans();
        totalBayar = new BigDecimal(0.0);
        temp.stream().map((pembayaran) -> pembayaran.getBayar().multiply(pembayaran.getRate())).forEach((totalTemp) -> {
            totalBayar = totalBayar.add(totalTemp);
        });
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public LazyDataModel<HutangPiutang> getHutangModels() {
        return hutangModels;
    }

    public void setHutangModels(HutangLazy hutangModels) {
        this.hutangModels = hutangModels;
    }

    public LazyDataModel<Pembayaran> getPembayaranModels() {
        return pembayaranModels;
    }

    public void setPembayaranModels(HutangBayarLazy pembayaranModels) {
        this.pembayaranModels = pembayaranModels;
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

    public ArrayList<Rekening> getRekeningModels() {
        return rekeningModels;
    }

    public void setRekeningModels(ArrayList<Rekening> rekeningModels) {
        this.rekeningModels = rekeningModels;
    }

    public ArrayList<Invoice> getInvoiceModels() {
        return invoiceModels;
    }

    public void setInvoiceModels(ArrayList<Invoice> invoiceModels) {
        this.invoiceModels = invoiceModels;
    }

    public Orang getPenjualModel() {
        return penjualModel;
    }

    public void setPenjualModel(Orang penjualModel) {
        this.penjualModel = penjualModel;
    }

    public Pembayaran getPembayaranModel() {
        return pembayaranModel;
    }

    public void setPembayaranModel(Pembayaran pembayaranModel) {
        this.pembayaranModel = pembayaranModel;
    }

    public PembayaranHutangPiutang getPembayaranHutangModel() {
        return pembayaranHutangModel;
    }

    public void setPembayaranHutangModel(PembayaranHutangPiutang pembayaranHutangModel) {
        this.pembayaranHutangModel = pembayaranHutangModel;
    }

    public long getPembayaranId() {
        return pembayaranId;
    }

    public void setPembayaranId(long pembayaranId) {
        this.pembayaranId = pembayaranId;
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

    public long getRekeningId() {
        return rekeningId;
    }

    public void setRekeningId(long rekeningId) {
        this.rekeningId = rekeningId;
    }

    public long getRekeningIdBefore() {
        return rekeningIdBefore;
    }

    public void setRekeningIdBefore(long rekeningIdBefore) {
        this.rekeningIdBefore = rekeningIdBefore;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getInvoiceIdBefore() {
        return invoiceIdBefore;
    }

    public void setInvoiceIdBefore(long invoiceIdBefore) {
        this.invoiceIdBefore = invoiceIdBefore;
    }

    public boolean getInvoiceValid() {
        return invoiceValid;
    }

    public void setInvoiceValid(boolean invoiceValid) {
        this.invoiceValid = invoiceValid;
    }

    public boolean getBayarValid() {
        return bayarValid;
    }

    public void setBayarValid(boolean bayarValid) {
        this.bayarValid = bayarValid;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(BigDecimal totalBayar) {
        this.totalBayar = totalBayar;
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
