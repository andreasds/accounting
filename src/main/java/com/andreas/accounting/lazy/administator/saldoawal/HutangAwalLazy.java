package com.andreas.accounting.lazy.administator.saldoawal;

import com.andreas.accounting.model.administrator.saldoawal.InvoiceAwal;
import com.andreas.accounting.service.administrator.saldoawal.HutangAwalService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class HutangAwalLazy extends LazyDataModel<InvoiceAwal> {

    private static final long serialVersionUID = 2604960264587031858L;

    private final HutangAwalService hutangAwalService = new HutangAwalService();
    private long perusahaanId = 0;
    private long pemilikId = 0;

    public HutangAwalLazy(long perusahaanId, long pemilikId) {
        this.perusahaanId = perusahaanId;
        this.pemilikId = pemilikId;
    }

    @Override
    public List<InvoiceAwal> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        filters.put("perusahaanId", perusahaanId);
        filters.put("pemilikId", pemilikId);

        setRowCount(hutangAwalService.count(filters));
        return (List<InvoiceAwal>) hutangAwalService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
