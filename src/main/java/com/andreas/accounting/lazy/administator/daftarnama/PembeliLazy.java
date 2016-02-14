package com.andreas.accounting.lazy.administator.daftarnama;

import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.service.administrator.daftarnama.PembeliService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class PembeliLazy extends LazyDataModel<Orang> {

    private static final long serialVersionUID = -6568721467432217252L;

    private final PembeliService pembeliService = new PembeliService();

    @Override
    public List<Orang> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(pembeliService.count(filters));
        return (List<Orang>) pembeliService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
