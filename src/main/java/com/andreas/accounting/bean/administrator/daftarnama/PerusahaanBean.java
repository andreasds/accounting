package com.andreas.accounting.bean.administrator.daftarnama;

import com.andreas.accounting.lazy.administator.daftarnama.PerusahaanLazy;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import com.andreas.accounting.util.BaseBeanInterface;
import com.andreas.accounting.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
@ManagedBean(name = "perusahaanBean")
@ViewScoped
public class PerusahaanBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = 7573637609459406109L;

    private String pageName;
    private final String baseModule = "/modules/administrator/daftar-nama/perusahaan/";

    private PerusahaanLazy perusahaanModels;
    private Perusahaan perusahaanModel;
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private long perusahaanId;
    private String namaBefore;
    private boolean namaValid;

    @Override
    public void init() {
        pageName = "Perusahaan";
    }

    @Override
    public void viewInput() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            perusahaanModel = new Perusahaan();
            namaBefore = "";
            namaValid = false;
        }
    }

    @Override
    public void viewDetail(long id) {
        init();
        perusahaanModel = (Perusahaan) perusahaanService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            perusahaanModel = (Perusahaan) perusahaanService.get(id);
            namaBefore = perusahaanModel.getNama();
            namaValid = true;
        }
    }

    @Override
    public void viewAll() {
        init();
        perusahaanModels = new PerusahaanLazy();
    }

    public void save() {
        String response;
        response = perusahaanService.save(perusahaanModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        response = perusahaanService.update(perusahaanModel).toString();

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
        perusahaanService.delete(id);
        list();
    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    public void checkNama() {
        if (perusahaanModel.getNama().equalsIgnoreCase(namaBefore)) {
            namaValid = true;
        } else {
            namaValid = perusahaanService.checkNama(perusahaanModel.getNama());
        }
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public LazyDataModel<Perusahaan> getPerusahaanModels() {
        return perusahaanModels;
    }

    public void setPerusahaanModels(PerusahaanLazy perusahaanModels) {
        this.perusahaanModels = perusahaanModels;
    }

    public Perusahaan getPerusahaanModel() {
        return perusahaanModel;
    }

    public void setPerusahaanModel(Perusahaan perusahaanModel) {
        this.perusahaanModel = perusahaanModel;
    }

    public long getPerusahaanId() {
        return perusahaanId;
    }

    public void setPerusahaanId(long perusahaanId) {
        this.perusahaanId = perusahaanId;
    }

    public String getNamaBefore() {
        return namaBefore;
    }

    public void setNamaBefore(String namaBefore) {
        this.namaBefore = namaBefore;
    }

    public boolean getNamaValid() {
        return namaValid;
    }

    public void setNamaValid(boolean namaValid) {
        this.namaValid = namaValid;
    }
}
