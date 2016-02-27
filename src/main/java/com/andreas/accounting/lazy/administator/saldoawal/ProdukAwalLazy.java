package com.andreas.accounting.lazy.administator.saldoawal;

import com.andreas.accounting.model.administrator.saldoawal.ProdukAwal;
import com.andreas.accounting.service.administrator.saldoawal.ProdukAwalService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class ProdukAwalLazy extends LazyDataModel<ProdukAwal> {

    private static final long serialVersionUID = -6979367993853606623L;

    private final ProdukAwalService produkAwalService = new ProdukAwalService();

    @Override
    public List<ProdukAwal> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(produkAwalService.count(filters));
        return (List<ProdukAwal>) produkAwalService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
