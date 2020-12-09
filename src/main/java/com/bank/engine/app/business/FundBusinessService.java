package com.bank.engine.app.business;

import com.bank.engine.app.model.base.ResultModel;
import com.bank.engine.app.model.page.FundBasicPageModel;
import com.bank.engine.app.model.page.FundRankPageModel;

public interface FundBusinessService {

    /**
     * 查询 基金排行
     *
     * @return
     */
    FundRankPageModel queryFundRank(String fundCode, String fundName,
                                    String sortName, String sortType,
                                    Integer pageNo, Integer pageSize);

    /**
     * 查询 基金
     *
     * @return
     */
    FundBasicPageModel queryFundBasic(String fundCode, String fundName,
                                      String fundType,
                                      Integer pageNo, Integer pageSize);

    /**
     * 同步
     *
     * @param fundCode
     * @param syncType
     * @return
     */
    ResultModel syncFundAnalyse(String fundCode, String syncType);

    /**
     * add
     *
     * @param fundCode
     * @return
     */
    ResultModel addToAnalyse(String fundCode);
}
