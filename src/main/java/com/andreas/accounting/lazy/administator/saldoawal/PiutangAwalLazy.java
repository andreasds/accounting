package com.andreas.accounting.lazy.administator.saldoawal;

import com.andreas.accounting.model.administrator.saldoawal.InvoiceAwal;
import com.andreas.accounting.service.administrator.saldoawal.PiutangAwalService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class PiutangAwalLazy extends LazyDataModel<InvoiceAwal> {

    private static final long serialVersionUID = -1670418951015153276L;

    private final PiutangAwalService piutangAwalService = new PiutangAwalService();

    @Override
    public List<InvoiceAwal> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(piutangAwalService.count(filters));
        return (List<InvoiceAwal>) piutangAwalService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
