package com.andreas.accounting.lazy.pembelian;

import com.andreas.accounting.model.util.Pembayaran;
import com.andreas.accounting.service.pembelian.HutangService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class HutangBayarLazy extends LazyDataModel<Pembayaran> {

    private static final long serialVersionUID = -7193473996747228647L;

    private final HutangService hutangService = new HutangService();
    private long pemilikId = 0;

    public HutangBayarLazy(long pemilikId) {
        this.pemilikId = pemilikId;
    }

    @Override
    public List<Pembayaran> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        filters.put("pemilikId", pemilikId);

        setRowCount(hutangService.countBayar(filters));
        return (List<Pembayaran>) hutangService.listBayar(first, pageSize, sortField, sortOrder, filters);
    }
}
