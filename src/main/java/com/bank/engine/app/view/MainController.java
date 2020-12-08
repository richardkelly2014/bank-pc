package com.bank.engine.app.view;

import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.view.fund.FundRankController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@FXMLViewAndController(value = "template/MainView.fxml", title = "钱包")
@Component
@Slf4j
public class MainController extends AbstractFxView {

    @Autowired
    private FundRankController fundRankController;

    @FXML
    private JFXButton btnFundRank, btnFundTheme, btnFundMark;
    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize() {
        btnFundRank.setOnAction(action -> {
            drawer.setContent(fundRankController.getView());
            fundRankController.refresh();
        });
    }
}
