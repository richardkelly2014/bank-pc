package com.bank.engine.app.view.fund;

import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jiangfei on 2020/12/12.
 */
@Slf4j
@FXMLViewAndController(value = "template/fund/FundInfoView.fxml", title = "基金详情")
public class FundInfoController extends AbstractFxView {

    private String fundCode;
    private String fundName;
    @FXML
    private VBox vBoxContent;
    @FXML
    private BorderPane mainPane;

    public FundInfoController(String code, String name) {
        this.fundCode = code;
        this.fundName = name;
    }

    @Override
    public void initialize() {
        vBoxContent.prefWidthProperty().bind(mainPane.widthProperty().subtract(30));
    }

    @Override
    protected String getDefaultTitle() {
        return super.getDefaultTitle() + "---" + fundName + "(" + fundCode + ")";
    }
}
