package com.andreas.accounting.lazy.administator.daftarproduk;

import com.andreas.accounting.model.administrator.daftarproduk.KategoriProduk;
import com.andreas.accounting.service.administrator.daftarproduk.KategoriProdukService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class KategoriProdukLazy extends LazyDataModel<KategoriProduk> {

    private static final long serialVersionUID = -9131584433104954227L;

    private final KategoriProdukService kategoriProdukService = new KategoriProdukService();

    @Override
    public List<KategoriProduk> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(kategoriProdukService.count(filters));
        return (List<KategoriProduk>) kategoriProdukService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
