package com.andreas.accounting.lazy.administator.daftarnama;

import com.andreas.accounting.model.administrator.daftarnama.Perusahaan;
import com.andreas.accounting.service.administrator.daftarnama.PerusahaanService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class PerusahaanLazy extends LazyDataModel<Perusahaan> {

    private static final long serialVersionUID = 4569146809456497310L;

    private final PerusahaanService perusahaanService = new PerusahaanService();

    @Override
    public List<Perusahaan> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(perusahaanService.count(filters));
        return (List<Perusahaan>) perusahaanService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
