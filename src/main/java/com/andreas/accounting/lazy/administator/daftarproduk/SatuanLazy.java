package com.andreas.accounting.lazy.administator.daftarproduk;

import com.andreas.accounting.model.administrator.daftarproduk.Satuan;
import com.andreas.accounting.service.administrator.daftarproduk.SatuanService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class SatuanLazy extends LazyDataModel<Satuan> {

    private static final long serialVersionUID = -1378278932172454475L;

    private final SatuanService satuanService = new SatuanService();

    @Override
    public List<Satuan> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(satuanService.count(filters));
        return (List<Satuan>) satuanService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
