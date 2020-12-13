package com.bank.engine.app.business;

import com.bank.engine.app.model.base.ResultModel;
import com.bank.engine.app.model.page.*;

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
     * 查询 净值
     *
     * @param fundCode
     * @param pageNo
     * @param pageSize
     * @return
     */
    FundRatePageModel queryFundRate(final String fundCode,
                                    final Integer pageNo,
                                    final Integer pageSize);

    /**
     * 查询基金基本信息
     *
     * @param fundCode
     * @return
     */
    FundInfoResultModel queryFundInfo(final String fundCode);

    /**
     * 查询需要监控的基金
     *
     * @return
     */
    FundRealResultModel queryRealList();

    /**
     * 查询基金实时数据
     *
     * @param fundCode
     * @return
     */
    FundRealSingleResultModel queryFundReal(String fundCode);

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
