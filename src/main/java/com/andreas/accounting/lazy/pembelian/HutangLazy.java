package com.andreas.accounting.lazy.pembelian;

import com.andreas.accounting.model.util.HutangPiutang;
import com.andreas.accounting.service.pembelian.HutangService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andreas Dharmawan <andreas.ds90@gmail.com>
 */
public class HutangLazy extends LazyDataModel<HutangPiutang> {

    private static final long serialVersionUID = -8125669690288425122L;

    private final HutangService hutangService = new HutangService();
    private long penjualId = 0;
    private long pemilikId = 0;

    public HutangLazy(long penjualId, long pemilikId) {
        this.penjualId = penjualId;
        this.pemilikId = pemilikId;
    }

    @Override
    public List<HutangPiutang> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        filters.put("penjualId", penjualId);
        filters.put("pemilikId", pemilikId);

        setRowCount(hutangService.count(filters));
        return (List<HutangPiutang>) hutangService.list(first, pageSize, sortField, sortOrder, filters);
    }
}
