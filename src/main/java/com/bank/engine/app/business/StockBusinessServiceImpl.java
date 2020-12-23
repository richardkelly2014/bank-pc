package com.bank.engine.app.business;

import com.bank.engine.app.config.HttpClient;
import com.bank.engine.app.model.page.StockRankPageModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockBusinessServiceImpl implements StockBusinessService {
    @Value("${bank.stock}")
    private String baseUrl;

    @Autowired
    private HttpClient httpClient;

    @Override
    public StockRankPageModel queryStockRank(String stockCode, String stockName,
                                             Integer pageNo, Integer pageSize) {
        String url = baseUrl + "/v1/search?pageNo=" + pageNo + "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(stockCode)) {
            url += "&dimCode=" + stockCode;
        }

        if (StringUtils.isNotBlank(stockName)) {
            url += "&dimName=" + stockName;
        }

        StockRankPageModel pageModel = httpClient.get(url, StockRankPageModel.class);
        if (pageModel == null) {
            pageModel = new StockRankPageModel();
        }

        return pageModel;
    }
}
