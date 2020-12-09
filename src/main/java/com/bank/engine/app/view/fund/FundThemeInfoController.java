package com.bank.engine.app.view.fund;

import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.model.FundThemeModel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jiangfei on 2020/12/9.
 */
@Slf4j
@FXMLViewAndController(value = "template/fund/FundThemeInfoView.fxml", title = "基金主题详情")
public class FundThemeInfoController extends AbstractFxView {

    private FundThemeModel fundThemeModel;

    public FundThemeInfoController(FundThemeModel fundThemeModel) {
        this.fundThemeModel = fundThemeModel;
    }

    @Override
    public void initialize() {

    }

    @Override
    protected String getDefaultTitle() {
        
        return super.getDefaultTitle() + "-----" + fundThemeModel.getThemeName();
    }
}
