package com.andreas.accounting.bean.administrator;

import com.andreas.accounting.lazy.administator.RekeningLazy;
import com.andreas.accounting.model.administrator.Rekening;
import com.andreas.accounting.service.administrator.RekeningService;
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
@ManagedBean(name = "rekeningBean")
@ViewScoped
public class RekeningBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = 8531819975611957446L;

    private String pageName;
    private final String baseModule = "/modules/administrator/rekening/";

    private LazyDataModel<Rekening> rekeningModels;
    private Rekening rekeningModel;
    private final RekeningService rekeningService = new RekeningService();
    private long rekeningId;

    @Override
    public void init() {
        pageName = "Rekening";
    }

    @Override
    public void viewInput() {
        init();
        rekeningModel = new Rekening();
    }

    @Override
    public void viewDetail(long id) {
        init();
        rekeningModel = (Rekening) rekeningService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        rekeningModel = (Rekening) rekeningService.get(id);
    }

    @Override
    public void viewAll() {
        init();
        rekeningModels = new RekeningLazy();
    }

    public void save() {
        String response;
        response = rekeningService.save(rekeningModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        response = rekeningService.update(rekeningModel).toString();

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
        rekeningService.delete(id);
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

    public LazyDataModel<Rekening> getRekeningModels() {
        return rekeningModels;
    }

    public void setRekeningModels(LazyDataModel<Rekening> rekeningModels) {
        this.rekeningModels = rekeningModels;
    }

    public Rekening getRekeningModel() {
        return rekeningModel;
    }

    public void setRekeningModel(Rekening rekeningModel) {
        this.rekeningModel = rekeningModel;
    }

    public long getRekeningId() {
        return rekeningId;
    }

    public void setRekeningId(long rekeningId) {
        this.rekeningId = rekeningId;
    }
}
