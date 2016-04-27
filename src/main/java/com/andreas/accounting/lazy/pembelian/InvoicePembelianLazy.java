package com.andreas.accounting.lazy.pembelian;

import com.andreas.accounting.model.util.Invoice;
import com.andreas.accounting.service.pembelian.InvoicePembelianService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class InvoicePembelianLazy extends LazyDataModel<Invoice> {

    private static final long serialVersionUID = -1332135337120472778L;

    private final InvoicePembelianService invoicePembelianService = new InvoicePembelianService();
    private long pemilikId = 0;

    public InvoicePembelianLazy(long pemilikId) {
        this.pemilikId = pemilikId;
    }

    @Override
    public List<Invoice> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        filters.put("pemilikId", pemilikId);

        setRowCount(invoicePembelianService.count(filters));
        return (List<Invoice>) invoicePembelianService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
