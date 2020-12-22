package com.bank.engine.app.view;

import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FXMLViewAndController(value = "template/StockMainView.fxml", title = "股票")
public class StockMainController extends AbstractFxView {
    @Override
    public void initialize() {

    }
}
