package com.andreas.accounting.lazy.administator.saldoawal;

import com.andreas.accounting.model.administrator.daftarproduk.Produk;
import com.andreas.accounting.service.administrator.saldoawal.AwalPersediaanService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class AwalPersediaanLazy extends LazyDataModel<Produk> {

    private static final long serialVersionUID = -6979367993853606623L;

    private final AwalPersediaanService awalPersediaanService = new AwalPersediaanService();

    @Override
    public List<Produk> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(awalPersediaanService.count(filters));
        return (List<Produk>) awalPersediaanService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
