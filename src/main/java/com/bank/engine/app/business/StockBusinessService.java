package com.bank.engine.app.business;

import com.bank.engine.app.model.page.StockDailyResultModel;
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

    /**
     * 查询 股票 日常 K线
     *
     * @param stockCode
     * @return
     */
    StockDailyResultModel queryStockDaily(String stockCode, int pageNo, int pageSize);
}
