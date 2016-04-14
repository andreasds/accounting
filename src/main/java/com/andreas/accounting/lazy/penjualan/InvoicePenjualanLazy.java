package com.andreas.accounting.lazy.penjualan;

import com.andreas.accounting.model.util.Invoice;
import com.andreas.accounting.service.penjualan.InvoicePenjualanService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class InvoicePenjualanLazy extends LazyDataModel<Invoice> {
    
    private static final long serialVersionUID = -1332135337120472778L;

    private final InvoicePenjualanService invoicePenjualanService = new InvoicePenjualanService();

    @Override
    public List<Invoice> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount(invoicePenjualanService.count(filters));
        return (List<Invoice>) invoicePenjualanService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
