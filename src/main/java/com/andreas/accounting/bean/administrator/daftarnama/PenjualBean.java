package com.andreas.accounting.bean.administrator.daftarnama;

import com.andreas.accounting.lazy.administator.daftarnama.PenjualLazy;
import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.service.administrator.daftarnama.PenjualService;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import com.andreas.accounting.util.BaseBeanInterface;
import com.andreas.accounting.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
@ManagedBean(name = "penjualBean")
@ViewScoped
public class PenjualBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = -2952113071412694385L;

    private String pageName;
    private final String baseModule = "/modules/administrator/daftar-nama/penjual/";

    private LazyDataModel<Orang> penjualModels;
    private ArrayList<Perusahaan> perusahaanModels;
    private Orang penjualModel;
    private final PenjualService penjualService = new PenjualService();
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private long penjualId;
    private long perusahaanId;

    @Override
    public void init() {
        pageName = "Penjual";
    }

    @Override
    public void viewInput() {
        init();
        penjualModel = new Orang();
        perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNama();
    }

    @Override
    public void viewDetail(long id) {
        init();
        penjualModel = (Orang) penjualService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        penjualModel = (Orang) penjualService.get(id);
        perusahaanId = penjualModel.getPerusahaan().getId();
        perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNama();
    }

    @Override
    public void viewAll() {
        init();
        penjualModels = new PenjualLazy();
    }

    public void save() {
        String response;
        penjualModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        response = penjualService.save(penjualModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        penjualModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        response = penjualService.update(penjualModel).toString();

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
        penjualService.delete(id);
        list();
    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public LazyDataModel<Orang> getPenjualModels() {
        return penjualModels;
    }

    public void setPenjualModels(LazyDataModel<Orang> penjualModels) {
        this.penjualModels = penjualModels;
    }

    public ArrayList<Perusahaan> getPerusahaanModels() {
        return perusahaanModels;
    }

    public void setPerusahaanModels(ArrayList<Perusahaan> perusahaanModels) {
        this.perusahaanModels = perusahaanModels;
    }

    public Orang getPenjualModel() {
        return penjualModel;
    }

    public void setPenjualModel(Orang penjualModel) {
        this.penjualModel = penjualModel;
    }

    public long getPenjualId() {
        return penjualId;
    }

    public void setPenjualId(long penjualId) {
        this.penjualId = penjualId;
    }

    public long getPerusahaanId() {
        return perusahaanId;
    }

    public void setPerusahaanId(long perusahaanId) {
        this.perusahaanId = perusahaanId;
    }
}
