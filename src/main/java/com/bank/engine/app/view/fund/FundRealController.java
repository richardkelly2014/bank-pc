package com.bank.engine.app.view.fund;

import com.bank.engine.app.business.FundBusinessService;
import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.model.FundRealModel;
import com.bank.engine.app.model.page.FundRealResultModel;
import com.bank.engine.app.ui.RealFundNode;
import com.bank.engine.app.util.DefaultThreadFactory;
import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jiangfei on 2020/12/13.
 */
@Slf4j
@Component
@FXMLViewAndController(value = "template/fund/FundRealView.fxml", title = "实时监控")
public class FundRealController extends AbstractFxView implements InitializingBean {
    @Autowired
    private FundBusinessService fundBusinessService;

    @FXML
    private FlowPane realPane;
    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize() {

    }

    public void refresh() {
        DefaultThreadFactory.runLater(this::loadReal);
    }

    private void loadReal() {
        FundRealResultModel realResultModel = fundBusinessService.queryRealList();

        if (realResultModel != null && realResultModel.getData() != null) {

            List<FundRealModel> list = realResultModel.getData();

            List<Node> realNodes = Lists.newArrayList();

            for (FundRealModel model : list) {
                RealFundNode<FundRealModel> ui = new RealFundNode<FundRealModel>(model.getFundName(), model.getFundCode(),
                        model, this::realClick, fundBusinessService);
                realNodes.add(ui);
            }

            Platform.runLater(() -> {
                realPane.getChildren().addAll(realNodes);
                scrollPane.requestLayout();
            });
        }

    }

    private void realClick(FundRealModel model) {

        log.info("{}", model);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        getView();
    }


}
