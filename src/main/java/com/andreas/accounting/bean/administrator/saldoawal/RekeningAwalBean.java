package com.andreas.accounting.bean.administrator.saldoawal;

import com.andreas.accounting.lazy.administator.saldoawal.RekeningAwalLazy;
import com.andreas.accounting.model.administrator.Rekening;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.model.administrator.saldoawal.RekeningAwal;
import com.andreas.accounting.service.administrator.RekeningService;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import com.andreas.accounting.service.administrator.saldoawal.RekeningAwalService;
import com.andreas.accounting.util.BaseBeanInterface;
import com.andreas.accounting.util.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
@ManagedBean(name = "rekeningAwalBean")
@ViewScoped
public class RekeningAwalBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = 2470444548577758236L;

    private String pageName;
    private final String baseModule = "/modules/administrator/saldo-awal/rekening/";

    private LazyDataModel<RekeningAwal> rekeningAwalModels;
    private ArrayList<Perusahaan> perusahaanModels;
    private ArrayList<Rekening> rekeningModels;
    private RekeningAwal rekeningAwalModel;
    private final RekeningAwalService rekeningAwalService = new RekeningAwalService();
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private final RekeningService rekeningService = new RekeningService();
    private long rekeningAwalId;
    private long perusahaanId;
    private long perusahaanIdBefore;
    private long rekeningId;
    private long rekeningIdBefore;
    private boolean rekeningValid;
    private BigDecimal total;

    @Override
    public void init() {
        pageName = "Rekening Awal";
    }

    @Override
    public void viewInput() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            rekeningAwalModel = new RekeningAwal();
            perusahaanIdBefore = 0;
            rekeningIdBefore = 0;
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            rekeningModels = (ArrayList<Rekening>) rekeningService.listNama();
            rekeningValid = false;
        }
    }

    @Override
    public void viewDetail(long id) {
        init();
        rekeningAwalModel = (RekeningAwal) rekeningAwalService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            rekeningAwalModel = (RekeningAwal) rekeningAwalService.get(id);
            perusahaanId = rekeningAwalModel.getPerusahaan().getId();
            perusahaanIdBefore = rekeningAwalModel.getPerusahaan().getId();
            rekeningId = rekeningAwalModel.getRekening().getId();
            rekeningIdBefore = rekeningAwalModel.getRekening().getId();
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNamaPemilik();
            rekeningModels = (ArrayList<Rekening>) rekeningService.listNama();
            rekeningValid = true;
        }
    }

    @Override
    public void viewAll() {
        init();
        rekeningAwalModels = new RekeningAwalLazy();
        perusahaanId = 0;
        total = rekeningAwalService.getTotal(perusahaanId);
    }

    public void save() {
        String response;
        rekeningAwalModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        rekeningAwalModel.setRekening((Rekening) rekeningService.get(rekeningId));
        response = rekeningAwalService.save(rekeningAwalModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        rekeningAwalModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        rekeningAwalModel.setRekening((Rekening) rekeningService.get(rekeningId));
        response = rekeningAwalService.update(rekeningAwalModel).toString();

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
        rekeningAwalService.delete(id);
        list();
    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    public void checkRekening() {
        if (rekeningId == rekeningIdBefore
                && perusahaanId == perusahaanIdBefore) {
            rekeningValid = true;
        } else {
            rekeningValid = rekeningAwalService.checkRekening(rekeningId, perusahaanId);
        }
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public LazyDataModel<RekeningAwal> getRekeningAwalModels() {
        return rekeningAwalModels;
    }

    public void setRekeningAwalModels(LazyDataModel<RekeningAwal> rekeningAwalModels) {
        this.rekeningAwalModels = rekeningAwalModels;
    }

    public ArrayList<Perusahaan> getPerusahaanModels() {
        return perusahaanModels;
    }

    public void setPerusahaanModels(ArrayList<Perusahaan> perusahaanModels) {
        this.perusahaanModels = perusahaanModels;
    }

    public ArrayList<Rekening> getRekeningModels() {
        return rekeningModels;
    }

    public void setRekeningModels(ArrayList<Rekening> rekeningModels) {
        this.rekeningModels = rekeningModels;
    }

    public RekeningAwal getRekeningAwalModel() {
        return rekeningAwalModel;
    }

    public void setRekeningAwalModel(RekeningAwal rekeningAwalModel) {
        this.rekeningAwalModel = rekeningAwalModel;
    }

    public long getRekeningAwalId() {
        return rekeningAwalId;
    }

    public void setRekeningAwalId(long rekeningAwalId) {
        this.rekeningAwalId = rekeningAwalId;
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

    public boolean getRekeningValid() {
        return rekeningValid;
    }

    public void setRekeningValid(boolean rekeningValid) {
        this.rekeningValid = rekeningValid;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
