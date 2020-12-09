package com.bank.engine.app.view.fund;

import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FXMLViewAndController(value = "template/fund/FundThemeView.fxml", title = "基金主题")
public class FundThemeController extends AbstractFxView implements InitializingBean {
    @Override
    public void initialize() {

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        getView();
    }
}
