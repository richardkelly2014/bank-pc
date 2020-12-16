package com.bank.engine.app.business;

import com.bank.engine.app.config.HttpClient;
import com.bank.engine.app.model.base.ResultModel;
import com.bank.engine.app.model.page.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 基金 业务交互接口
 */
@Service
public class FundBusinessServiceImpl implements FundBusinessService {

    @Value("${bank.fund}")
    private String baseUrl;

    @Autowired
    private HttpClient httpClient;

    @Override
    public FundRankPageModel queryFundRank(String fundCode, String fundName,
                                           String sortName, String sortType,
                                           Integer pageNo, Integer pageSize) {

        String url = baseUrl + "/rank/v1/search?pageNo=" + pageNo + "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortType)) {
            url += "&sortName=" +
                    sortName + "&sortType=" + sortType;
        }
        if (StringUtils.isNotBlank(fundCode)) {
            url += "&fundCode=" + fundCode;
        }
        if (StringUtils.isNotBlank(fundName)) {
            url += "&fundName=" + fundName;
        }

        FundRankPageModel pageModel = httpClient.get(url, FundRankPageModel.class);
        if (pageModel == null) {
            pageModel = new FundRankPageModel();
        }
        return pageModel;
    }

    @Override
    public FundBasicPageModel queryFundBasic(String fundCode, String fundName,
                                             String fundType,
                                             Integer pageNo, Integer pageSize) {
        String url = baseUrl + "/v1/search?pageNo=" + pageNo + "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(fundCode)) {
            url += "&fundCode=" + fundCode;
        }
        if (StringUtils.isNotBlank(fundName)) {
            url += "&fundName=" + fundName;
        }
        if (StringUtils.isNotBlank(fundType) && StringUtils.isNumeric(fundType)) {
            url += "&fundType=" + fundType;
        }

        FundBasicPageModel pageModel = httpClient.get(url, FundBasicPageModel.class);
        if (pageModel == null) {
            pageModel = new FundBasicPageModel();
        }
        return pageModel;
    }

    @Override
    public FundThemeResultModel queryFundTheme(String themeName, String dataField) {
        String url = baseUrl + "/theme/v1/search?t=_1";
        if (StringUtils.isNotBlank(themeName)) {
            url += "&themeName=" + themeName;
        }
        if (StringUtils.isNotBlank(dataField)) {
            url += "&dataField=" + dataField;
        }
        FundThemeResultModel resultModel = httpClient.get(url, FundThemeResultModel.class);
        if (resultModel == null) {
            resultModel = new FundThemeResultModel();
        }
        return resultModel;
    }

    @Override
    public FundThemeInfoResultModel queryFundThemeInfo(String dataId) {
        String url = baseUrl + "/theme/v1/searchInfo?dataId=" + dataId;
        FundThemeInfoResultModel resultModel = httpClient.get(url, FundThemeInfoResultModel.class);
        if (resultModel == null) {
            resultModel = new FundThemeInfoResultModel();
        }
        return resultModel;
    }

    @Override
    public FundRatePageModel queryFundRate(String fundCode, Integer pageNo, Integer pageSize) {
        String url = baseUrl + "/rate/v1/search?fundCode=" + fundCode + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
        FundRatePageModel pageModel = httpClient.get(url, FundRatePageModel.class);
        return pageModel;
    }

    @Override
    public FundInfoResultModel queryFundInfo(String fundCode) {
        String url = baseUrl + "/v1/info?fundCode=" + fundCode;
        FundInfoResultModel resultModel = httpClient.get(url, FundInfoResultModel.class);
        return resultModel;
    }

    @Override
    public FundRealResultModel queryRealList() {
        String url = baseUrl + "/v1/realList";
        FundRealResultModel realResultModel = httpClient.get(url, FundRealResultModel.class);
        return realResultModel;
    }

    @Override
    public FundRealSingleResultModel queryFundReal(String fundCode) {
        String url = baseUrl + "/rank/v1/real?fundCode=" + fundCode;
        FundRealSingleResultModel realResultModel = httpClient.get(url, FundRealSingleResultModel.class);
        return realResultModel;
    }

    @Override
    public FundStockResultModel queryFundStock(String fundCode, Integer year) {
        String url = baseUrl + "/stock/v1/search?fundCode=" + fundCode + "&year=" + year;
        FundStockResultModel resultModel = httpClient.get(url, FundStockResultModel.class);
        if (resultModel == null) {
            resultModel = new FundStockResultModel();
        }
        return resultModel;
    }

    @Override
    public ResultModel syncFundAnalyse(String fundCode, String syncType) {

        String url = baseUrl + "/rank/v1/sync?fundCode=" + fundCode;
        if (StringUtils.isNotBlank(syncType) && StringUtils.isNumeric(syncType)) {
            url += "&syncType=" + syncType;
        }

        ResultModel resultModel = httpClient.get(url, ResultModel.class);

        return resultModel;
    }

    @Override
    public ResultModel syncFundTheme(String dataId) {
        String url = baseUrl + "/theme/v1/sync?dataId=" + dataId;
        return httpClient.get(url, ResultModel.class);
    }

    @Override
    public ResultModel addToAnalyse(String fundCode) {
        String url = baseUrl + "/v1/addToAnalyse?fundCode=" + fundCode;

        return httpClient.get(url, ResultModel.class);
    }

    @Override
    public ResultModel addToAnalyse(String fundCode, Integer type) {
        String url = baseUrl + "/v1/addToAnalyse?fundCode=" + fundCode + "&type=" + type;

        return httpClient.get(url, ResultModel.class);
    }

    @Override
    public ResultModel syncFundStock(String fundCode, String fundName) {

        String url = baseUrl + "/v1/syncStock?fundCode=" + fundCode + "&fundName=" + fundName;

        return httpClient.get(url, ResultModel.class);
    }
}
