package com.andreas.accounting.bean.administrator.daftarproduk;

import com.andreas.accounting.lazy.administator.daftarproduk.KategoriProdukLazy;
import com.andreas.accounting.model.administrator.daftarproduk.KategoriProduk;
import com.andreas.accounting.service.administrator.daftarproduk.KategoriProdukService;
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
@ManagedBean(name = "kategoriProdukBean")
@ViewScoped
public class KategoriProdukBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = 7277704564206570924L;

    private String pageName;
    private final String baseModule = "/modules/administrator/daftar-produk/kategori/";

    private LazyDataModel<KategoriProduk> kategoriProdukModels;
    private KategoriProduk kategoriProdukModel;
    private final KategoriProdukService kategoriProdukService = new KategoriProdukService();
    private long kategoriProdukId;

    @Override
    public void init() {
        pageName = "Kategori Produk";
    }

    @Override
    public void viewInput() {
        init();
        kategoriProdukModel = new KategoriProduk();
    }

    @Override
    public void viewDetail(long id) {
        init();
        kategoriProdukModel = (KategoriProduk) kategoriProdukService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        kategoriProdukModel = (KategoriProduk) kategoriProdukService.get(id);
    }

    @Override
    public void viewAll() {
        init();
        kategoriProdukModels = new KategoriProdukLazy();
    }

    public void save() {
        String response;
        response = kategoriProdukService.save(kategoriProdukModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        response = kategoriProdukService.update(kategoriProdukModel).toString();

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
        kategoriProdukService.delete(id);
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

    public LazyDataModel<KategoriProduk> getKategoriProdukModels() {
        return kategoriProdukModels;
    }

    public void setKategoriProdukModels(LazyDataModel<KategoriProduk> kategoriProdukModels) {
        this.kategoriProdukModels = kategoriProdukModels;
    }

    public KategoriProduk getKategoriProdukModel() {
        return kategoriProdukModel;
    }

    public void setKategoriProdukModel(KategoriProduk kategoriProdukModel) {
        this.kategoriProdukModel = kategoriProdukModel;
    }

    public long getKategoriProdukId() {
        return kategoriProdukId;
    }

    public void setKategoriProdukId(long kategoriProdukId) {
        this.kategoriProdukId = kategoriProdukId;
    }
}
