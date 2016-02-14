package com.andreas.accounting.bean.administrator.daftarproduk;

import com.andreas.accounting.lazy.administator.daftarproduk.ProdukLazy;
import com.andreas.accounting.model.administrator.daftarproduk.Produk;
import com.andreas.accounting.model.administrator.daftarproduk.Satuan;
import com.andreas.accounting.service.administrator.daftarproduk.ProdukService;
import com.andreas.accounting.service.administrator.daftarproduk.SatuanService;
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
@ManagedBean(name="produkBean")
@ViewScoped
public class ProdukBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = -8817166846454916108L;

    private String pageName;
    private final String baseModule = "/modules/administrator/daftar-produk/produk/";

    private LazyDataModel<Produk> produkModels;
    private ArrayList<Satuan> satuanModels;
    private Produk produkModel;
    private final ProdukService produkService = new ProdukService();
    private final SatuanService satuanService = new SatuanService();
    private long produkId;
    private long satuanId;

    @Override
    public void init() {
        pageName = "Produk";
    }

    @Override
    public void viewInput() {
        init();
        produkModel = new Produk();
        satuanModels = (ArrayList<Satuan>) satuanService.listAll();
    }

    @Override
    public void viewDetail(long id) {
        init();
        produkModel = (Produk) produkService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        produkModel = (Produk) produkService.get(id);
        satuanId = produkModel.getSatuan().getId();
        satuanModels = (ArrayList<Satuan>) satuanService.listAll();
    }

    @Override
    public void viewAll() {
        init();
        produkModels = new ProdukLazy();
    }

    public void save() {
        String response;
        produkModel.setSatuan((Satuan) satuanService.get(satuanId));
        response = produkService.save(produkModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        produkModel.setSatuan((Satuan) satuanService.get(satuanId));
        response = produkService.update(produkModel).toString();

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
        produkService.delete(id);
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

    public LazyDataModel<Produk> getProdukModels() {
        return produkModels;
    }

    public void setProdukModels(LazyDataModel<Produk> produkModels) {
        this.produkModels = produkModels;
    }

    public ArrayList<Satuan> getSatuanModels() {
        return satuanModels;
    }

    public void setSatuanModels(ArrayList<Satuan> satuanModels) {
        this.satuanModels = satuanModels;
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

    public long getSatuanId() {
        return satuanId;
    }

    public void setSatuanId(long satuanId) {
        this.satuanId = satuanId;
    }
}
