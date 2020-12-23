package com.bank.engine.app.view;

import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiangfei on 2020/12/20.
 */
@FXMLViewAndController(value = "template/AppView.fxml", title = "家庭知识库")
@Component
public class AppController extends AbstractFxView {

    @Autowired
    private FundMainController fundMainController;
    @Autowired
    private StockMainController stockMainController;

    @FXML
    private JFXButton btnFund, btnStock;

    @Override
    public void initialize() {
        btnFund.setOnAction(action -> fundMainController.showView(Modality.WINDOW_MODAL));

        btnStock.setOnAction(action -> stockMainController.showView(Modality.WINDOW_MODAL));
    }
}
