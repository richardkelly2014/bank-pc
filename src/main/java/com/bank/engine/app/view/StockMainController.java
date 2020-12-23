package com.bank.engine.app.view;

import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.view.stock.StockBasicController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FXMLViewAndController(value = "template/StockMainView.fxml", title = "股票")
public class StockMainController extends AbstractFxView {

    @Autowired
    private StockBasicController stockBasicController;

    @FXML
    private BorderPane mainPane;
    @FXML
    private JFXButton btnStockRank, btnStockMonitor;
    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize() {

        btnStockRank.setOnAction(action -> {
            drawer.setContent(stockBasicController.getView());
            stockBasicController.refresh();
        });

    }
}
