package com.andreas.accounting.bean.administrator.daftarnama;

import com.andreas.accounting.lazy.administator.daftarnama.PerusahaanLazy;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import com.andreas.accounting.util.BaseBeanInterface;
import com.andreas.accounting.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
    private boolean editMode;
    private final String baseModule = "/modules/administrator/daftar-nama/perusahaan/";

    private LazyDataModel<Perusahaan> perusahaanModels;
    private Perusahaan perusahaanModel;
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private long perusahaanId;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
    
    public LazyDataModel<Perusahaan> getPerusahaanModels() {
        return perusahaanModels;
    }
    
    public void setPerusahaanModels(LazyDataModel<Perusahaan> perusahaanModels) {
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

    @Override
    public void init() {
        pageName = "Perusahaan";
        perusahaanModels = new PerusahaanLazy();
    }

    @Override
    public void viewInput() {
        pageName = "Perusahaan";
        editMode = false;

        perusahaanModel = new Perusahaan();
    }

    @Override
    public void viewDetail(long id) {
        pageName = "Perusahaan";

        perusahaanModel = (Perusahaan) perusahaanService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        pageName = "Perusahaan";
        editMode = true;

        perusahaanModel = (Perusahaan) perusahaanService.get(id);
    }

    @Override
    public void viewDelete(long id) {
        perusahaanService.delete(id);
    }

    @Override
    public void viewAll() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    @Override
    public void save() {
        String response;
        if (!editMode) {
            response = perusahaanService.save(perusahaanModel).toString();
        } else {
            response = perusahaanService.update(perusahaanModel).toString();
        }

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    @Override
    public void search() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
