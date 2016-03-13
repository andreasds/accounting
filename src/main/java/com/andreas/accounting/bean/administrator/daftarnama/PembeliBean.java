package com.andreas.accounting.bean.administrator.daftarnama;

import com.andreas.accounting.lazy.administator.daftarnama.PembeliLazy;
import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.service.administrator.daftarnama.PembeliService;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
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
@ManagedBean(name = "pembeliBean")
@ViewScoped
public class PembeliBean implements BaseBeanInterface, Serializable {

    private static final long serialVersionUID = 5539574700892750832L;

    private String pageName;
    private final String baseModule = "/modules/administrator/daftar-nama/pembeli/";

    private LazyDataModel<Orang> pembeliModels;
    private ArrayList<Perusahaan> perusahaanModels;
    private Orang pembeliModel;
    private final PembeliService pembeliService = new PembeliService();
    private final PerusahaanService perusahaanService = new PerusahaanService();
    private long pembeliId;
    private long perusahaanId;
    private long perusahaanIdBefore;
    private String namaBefore;
    private boolean namaValid;

    @Override
    public void init() {
        pageName = "Pembeli";
    }

    @Override
    public void viewInput() {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pembeliModel = new Orang();
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNama();
            perusahaanIdBefore = 0;
            namaBefore = "";
            namaValid = false;
        }
    }

    @Override
    public void viewDetail(long id) {
        init();
        pembeliModel = (Orang) pembeliService.get(id);
    }

    @Override
    public void viewEdit(long id) {
        init();
        if (!FacesContext.getCurrentInstance().isPostback()) {
            pembeliModel = (Orang) pembeliService.get(id);
            perusahaanId = pembeliModel.getPerusahaan().getId();
            perusahaanIdBefore = pembeliModel.getPerusahaan().getId();
            perusahaanModels = (ArrayList<Perusahaan>) perusahaanService.listNama();
            namaBefore = pembeliModel.getNama();
            namaValid = true;
        }
    }

    @Override
    public void viewAll() {
        init();
        pembeliModels = new PembeliLazy();
    }

    public void save() {
        String response;
        pembeliModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        response = pembeliService.save(pembeliModel).toString();

        if (response != null) {
            Util.redirectToPage(baseModule + "detail.xhtml?id=" + response);
        } else {
            Util.redirectToPage(baseModule + "list.xhtml");
        }
    }

    public void update() {
        String response;
        pembeliModel.setPerusahaan((Perusahaan) perusahaanService.get(perusahaanId));
        response = pembeliService.update(pembeliModel).toString();

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
        pembeliService.delete(id);
        list();
    }

    public void list() {
        Util.redirectToPage(baseModule + "list.xhtml");
    }

    public void checkNama() {
        if (pembeliModel.getNama().equalsIgnoreCase(namaBefore)
                && perusahaanId == perusahaanIdBefore) {
            namaValid = true;
        } else {
            namaValid = pembeliService.checkNama(pembeliModel.getNama(), perusahaanId);
        }
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public LazyDataModel<Orang> getPembeliModels() {
        return pembeliModels;
    }

    public void setPembeliModels(LazyDataModel<Orang> pembeliModels) {
        this.pembeliModels = pembeliModels;
    }

    public ArrayList<Perusahaan> getPerusahaanModels() {
        return perusahaanModels;
    }

    public void setPerusahaanModels(ArrayList<Perusahaan> perusahaanModels) {
        this.perusahaanModels = perusahaanModels;
    }

    public Orang getPembeliModel() {
        return pembeliModel;
    }

    public void setPembeliModel(Orang pembeliModel) {
        this.pembeliModel = pembeliModel;
    }

    public long getPembeliId() {
        return pembeliId;
    }

    public void setPembeliId(long pembeliId) {
        this.pembeliId = pembeliId;
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
