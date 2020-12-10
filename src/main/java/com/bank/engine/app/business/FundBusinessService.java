package com.bank.engine.app.business;

import com.bank.engine.app.model.base.ResultModel;
import com.bank.engine.app.model.page.FundBasicPageModel;
import com.bank.engine.app.model.page.FundRankPageModel;
import com.bank.engine.app.model.page.FundThemeInfoResultModel;
import com.bank.engine.app.model.page.FundThemeResultModel;

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
     * 查询 基金 主题
     *
     * @param themeName
     * @param dataField
     * @return
     */
    FundThemeResultModel queryFundTheme(String themeName, String dataField);

    /**
     * 查询 主题 基金
     *
     * @param dataId
     * @return
     */
    FundThemeInfoResultModel queryFundThemeInfo(String dataId);

    /**
     * 同步
     *
     * @param fundCode
     * @param syncType
     * @return
     */
    ResultModel syncFundAnalyse(String fundCode, String syncType);

    /**
     * 同步 主题
     *
     * @param dataId
     * @return
     */
    ResultModel syncFundTheme(String dataId);

    /**
     * add
     *
     * @param fundCode
     * @return
     */
    ResultModel addToAnalyse(String fundCode);

    /**
     * add
     *
     * @param fundCode
     * @param type
     * @return
     */
    ResultModel addToAnalyse(String fundCode, Integer type);
}
