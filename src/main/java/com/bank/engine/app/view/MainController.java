package com.bank.engine.app.view;

import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.util.DefaultThreadFactory;
import com.bank.engine.app.view.fund.FundBasicController;
import com.bank.engine.app.view.fund.FundRankController;
import com.bank.engine.app.view.fund.FundRealController;
import com.bank.engine.app.view.fund.FundThemeController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@FXMLViewAndController(value = "template/MainView.fxml", title = "钱包")
@Component
@Slf4j
public class MainController extends AbstractFxView {

    @Autowired
    private FundRankController fundRankController;
    @Autowired
    private FundBasicController fundBasicController;
    @Autowired
    private FundThemeController fundThemeController;
    @Autowired
    private FundRealController fundRealController;

    @FXML
    private BorderPane mainPane;

    @FXML
    private JFXButton btnFundRank, btnFundTheme, btnFundMark, btnFundMonitor;
    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize() {
        btnFundRank.setOnAction(action -> {
            ((Stage) mainPane.getScene().getWindow()).setTitle(fundRankController.getDefaultTitle());
            drawer.setContent(fundRankController.getView());
            fundRankController.refresh();

        });

        btnFundMark.setOnAction(action -> {
            ((Stage) mainPane.getScene().getWindow()).setTitle(fundBasicController.getDefaultTitle());
            drawer.setContent(fundBasicController.getView());
            fundBasicController.refresh();
        });

        btnFundTheme.setOnAction(action -> {
            ((Stage) mainPane.getScene().getWindow()).setTitle(fundThemeController.getDefaultTitle());
            drawer.setContent(fundThemeController.getView());
            fundThemeController.refresh();
        });

        btnFundMonitor.setOnAction(action -> {
            ((Stage) mainPane.getScene().getWindow()).setTitle(fundRealController.getDefaultTitle());
            drawer.setContent(fundRealController.getView());
        });

        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                return 1;
            }
        };
        task.stateProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == Worker.State.SUCCEEDED) {
                ((Stage) mainPane.getScene().getWindow()).setTitle(fundRealController.getDefaultTitle());
                drawer.setContent(fundRealController.getView());
                fundRealController.refresh();
            }
        });

        DefaultThreadFactory.runLater(task);
    }

    public void showNetWarn() {
        Platform.runLater(() -> {
            Alert alertInfo = new Alert(Alert.AlertType.WARNING);
            alertInfo.setTitle("提示");
            alertInfo.setHeaderText(null);
            alertInfo.setContentText("当前网络有误,请联系管理员！");
            alertInfo.show();
        });
    }
}
