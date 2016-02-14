package com.andreas.accounting.bean.administrator;

import com.andreas.accounting.lazy.administator.SatuanLazy;
import com.andreas.accounting.model.administrator.Satuan;
import com.andreas.accounting.service.administrator.SatuanService;
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
@ManagedBean(name = "satuanBean")
@ViewScoped
public class SatuanBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = 5532664622488889325L;

    private String pageName;
    private final String baseModule = "/modules/administrator/satuan-pengukuran/";

    private LazyDataModel<Satuan> satuanModels;
    private Satuan satuanModel;
    private final SatuanService satuanService = new SatuanService();
    private long satuanId;

    @Override
    public void init() {
        pageName = "Satuan";
    }

    @Override
    public void viewInput() {
        init();
        satuanModel = new Satuan();
    }

    @Override
    public void viewDetail(long id) {
        init();
        satuanModel = (Satuan) satuanService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        satuanModel = (Satuan) satuanService.get(id);
    }

    @Override
    public void viewAll() {
        init();
        satuanModels = new SatuanLazy();
    }

    public void save() {
        String response;
        response = satuanService.save(satuanModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        response = satuanService.update(satuanModel).toString();

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
        satuanService.delete(id);
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

    public LazyDataModel<Satuan> getSatuanModels() {
        return satuanModels;
    }

    public void setSatuanModels(LazyDataModel<Satuan> satuanModels) {
        this.satuanModels = satuanModels;
    }

    public Satuan getSatuanModel() {
        return satuanModel;
    }

    public void setSatuanModel(Satuan satuanModel) {
        this.satuanModel = satuanModel;
    }

    public long getSatuanId() {
        return satuanId;
    }

    public void setSatuanId(long satuanId) {
        this.satuanId = satuanId;
    }
}
