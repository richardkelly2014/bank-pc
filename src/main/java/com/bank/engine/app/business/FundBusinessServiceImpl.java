package com.bank.engine.app.business;

import com.bank.engine.app.config.HttpClient;
import com.bank.engine.app.model.page.FundBasicPageModel;
import com.bank.engine.app.model.page.FundRankPageModel;
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
        return pageModel;
    }
}
