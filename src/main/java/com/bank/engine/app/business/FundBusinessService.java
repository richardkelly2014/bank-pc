package com.bank.engine.app.business;

import com.bank.engine.app.model.page.FundRankPageModel;

public interface FundBusinessService {

    /**
     * 查询 基金排行
     *
     * @return
     */
    FundRankPageModel queryFundRank(String sortName, String sortType, Integer pageNo, Integer pageSize);
}
