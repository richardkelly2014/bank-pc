package com.bank.engine.app.view.stock;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.bank.engine.app.business.StockBusinessService;
import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.model.StockDailyModel;
import com.bank.engine.app.model.page.StockDailyResultModel;
import com.bank.engine.app.util.DefaultThreadFactory;
import com.bank.engine.app.util.FileUtil;
import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Collections;
import java.util.List;

@Slf4j
@FXMLViewAndController(value = "template/stock/StockDailyView.fxml", title = "股票K线")
public class StockDailyController extends AbstractFxView {

    @Autowired
    private StockBusinessService stockBusinessService;

    private String stockCode;
    private String stockName;

    @FXML
    private WebView dailyView;

    /**
     * 用于与Javascript引擎通信。
     */
    private JSObject javascriptConnector;

    public StockDailyController(String code, String name) {
        this.stockCode = code;
        this.stockName = name;
    }

    @Override
    public void initialize() {

        WebEngine webEngine = this.dailyView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        // 加载指示器
        webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // 获取Javascript连接器对象。
                javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
                DefaultThreadFactory.runLater(this::showEchart);
            }
        });

        URL url = FileUtil.createURL("template/html/stockDaily.html");
        if (url != null) {
            webEngine.load(url.toExternalForm());
        }

    }

    private void showEchart() {
        String code = this.stockCode;
        StockDailyResultModel resultModel = stockBusinessService.queryStockDaily(code, 1, 300);
        List<StockDailyModel> list = resultModel.getData();

        Collections.reverse(list);

        List<List<Object>> result = Lists.newArrayList();

        for (StockDailyModel model : list) {
            List<Object> val = Lists.newArrayList();

            String day = model.getDay();
            //open close ,low,high
            Integer open = model.getOpen();
            Integer close = model.getClose();
            Integer low = model.getLow();
            Integer high = model.getHigh();
            Integer vol = model.getVol();

            val.add(day);
            val.add(priceRate(open));
            val.add(priceRate(close));
            val.add(priceRate(low));
            val.add(priceRate(high));
            val.add(vol);

            result.add(val);
        }
        Platform.runLater(() -> javascriptConnector.call("showResult", JSON.toJSONString(result)));
    }

    private double priceRate(int rate) {
        double v = NumberUtil.div(rate, 100, 2);
        return v;
    }
}
