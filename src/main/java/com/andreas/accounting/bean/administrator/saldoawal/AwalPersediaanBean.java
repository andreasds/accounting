package com.andreas.accounting.bean.administrator.saldoawal;

import com.andreas.accounting.lazy.administator.saldoawal.AwalPersediaanLazy;
import com.andreas.accounting.model.administrator.daftarproduk.Produk;
import com.andreas.accounting.service.administrator.daftarproduk.ProdukService;
import com.andreas.accounting.service.administrator.saldoawal.AwalPersediaanService;
import com.andreas.accounting.util.BaseBeanInterface;
import com.andreas.accounting.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
@ManagedBean
@ViewScoped
public class AwalPersediaanBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = -7117741740646907592L;

    private String pageName;
    private final String baseModule = "/modules/administrator/saldo-awal/persediaan/";

    private LazyDataModel<Produk> awalPersediaanModels;
    private ArrayList<Produk> produkModels;
    private Produk produkModel;
    private final AwalPersediaanService awalPersediaanService = new AwalPersediaanService();
    private final ProdukService produkService = new ProdukService();
    private long produkId;
    private long produkIdBefore;
    private boolean produkValid;

    @Override
    public void init() {
        pageName = "Saldo Awal Persediaan";
    }

    @Override
    public void viewInput() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            produkModel = new Produk();
            produkModels = (ArrayList<Produk>) produkService.listKode();
            produkId = 0;
            produkIdBefore = 0;
        }
    }

    @Override
    public void viewDetail(long id) {
        init();
        produkModel = (Produk) awalPersediaanService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            produkModel = (Produk) awalPersediaanService.get(id);
            produkModels = (ArrayList<Produk>) produkService.listKode();
            produkId = produkModel.getId();
            produkIdBefore = produkModel.getId();
            produkValid = true;
        }
    }

    @Override
    public void viewAll() {
        init();
        awalPersediaanModels = new AwalPersediaanLazy();
    }

    public void save() {
        String response;
        produkModel.setId(produkId);
        response = awalPersediaanService.save(produkModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        produkModel.setId(produkId);
        response = awalPersediaanService.update(produkModel).toString();

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
        awalPersediaanService.delete(id);
        list();
    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }
    
    public void checkProduk() {
        if (produkId == produkIdBefore) {
            produkValid = true;
        } else {
            produkValid = awalPersediaanService.checkProduk(produkId);
        }
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public LazyDataModel<Produk> getAwalPersediaanModels() {
        return awalPersediaanModels;
    }

    public void setAwalPersediaanModels(LazyDataModel<Produk> awalPersediaanModels) {
        this.awalPersediaanModels = awalPersediaanModels;
    }

    public ArrayList<Produk> getProdukModels() {
        return produkModels;
    }

    public void setProdukModels(ArrayList<Produk> produkModels) {
        this.produkModels = produkModels;
    }

    public Produk getProdukModel() {
        return produkModel;
    }

    public void setProdukModel(Produk produkModel) {
        this.produkModel = produkModel;
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
}
