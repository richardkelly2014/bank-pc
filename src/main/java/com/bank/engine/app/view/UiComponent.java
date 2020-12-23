package com.bank.engine.app.view;

import com.bank.engine.app.view.fund.FundInfoController;
import com.bank.engine.app.view.stock.StockDailyController;
import javafx.stage.Modality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jiangfei on 2020/12/12.
 */
@Component
public class UiComponent {
    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    /**
     * 显示基金详情
     *
     * @param code
     * @param name
     */
    public void showFundInfo(String code, String name) {
        FundInfoController infoController = new FundInfoController(code, name);
        capableBeanFactory.autowireBean(infoController);
        infoController.showView(Modality.WINDOW_MODAL);
    }

    /**
     * 显示股票K线
     *
     * @param code
     * @param name
     */
    public void showStockDaily(String code, String name) {
        StockDailyController dailyController = new StockDailyController(code, name);
        capableBeanFactory.autowireBean(dailyController);
        dailyController.showView(Modality.WINDOW_MODAL);
    }
}
