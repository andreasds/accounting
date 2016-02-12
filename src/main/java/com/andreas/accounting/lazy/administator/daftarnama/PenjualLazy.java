package com.andreas.accounting.lazy.administator.daftarnama;

import com.andreas.accounting.model.administrator.daftarnama.Orang;
import com.andreas.accounting.service.administrator.daftarnama.PenjualService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class PenjualLazy extends LazyDataModel<Orang> {

    private static final long serialVersionUID = 7862643672713771188L;

    private final PenjualService penjualService = new PenjualService();

    @Override
    public List<Orang> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(penjualService.count(filters));
        return (List<Orang>) penjualService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
