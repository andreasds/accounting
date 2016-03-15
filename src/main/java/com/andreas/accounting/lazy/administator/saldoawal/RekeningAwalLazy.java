package com.andreas.accounting.lazy.administator.saldoawal;

import com.andreas.accounting.model.administrator.saldoawal.RekeningAwal;
import com.andreas.accounting.service.administrator.saldoawal.RekeningAwalService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class RekeningAwalLazy extends LazyDataModel<RekeningAwal> {

    private static final long serialVersionUID = -6622830373075800646L;

    private final RekeningAwalService rekeningAwalService = new RekeningAwalService();

    @Override
    public List<RekeningAwal> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(rekeningAwalService.count(filters));
        return (List<RekeningAwal>) rekeningAwalService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
