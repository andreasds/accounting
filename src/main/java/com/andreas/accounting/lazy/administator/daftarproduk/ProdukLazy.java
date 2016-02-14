package com.andreas.accounting.lazy.administator.daftarproduk;

import com.andreas.accounting.model.administrator.daftarproduk.Produk;
import com.andreas.accounting.service.administrator.daftarproduk.ProdukService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class ProdukLazy extends LazyDataModel<Produk> {

    private static final long serialVersionUID = 6400237149211088963L;

    private final ProdukService produkService = new ProdukService();

    @Override
    public List<Produk> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(produkService.count(filters));
        return (List<Produk>) produkService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
