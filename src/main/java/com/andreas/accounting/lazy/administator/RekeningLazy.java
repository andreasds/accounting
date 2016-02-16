package com.andreas.accounting.lazy.administator;

import com.andreas.accounting.model.administrator.Rekening;
import com.andreas.accounting.service.administrator.RekeningService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class RekeningLazy extends LazyDataModel<Rekening> {

    private static final long serialVersionUID = -423205104426888395L;

    private final RekeningService rekeningService = new RekeningService();

    @Override
    public List<Rekening> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(rekeningService.count(filters));
        return (List<Rekening>) rekeningService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
