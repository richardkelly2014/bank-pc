package com.bank.engine.app.business;

import com.bank.engine.app.model.page.StockRankPageModel;

public interface StockBusinessService {

    /**
     * 查询
     *
     * @param stockCode
     * @param stockName
     * @param pageNo
     * @param pageSize
     * @return
     */
    StockRankPageModel queryStockRank(String stockCode, String stockName,
                                      Integer pageNo, Integer pageSize);
}
