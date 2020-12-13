package com.bank.engine.app.ui;

import com.bank.engine.app.business.FundBusinessService;
import com.bank.engine.app.model.FundRealModel;
import com.bank.engine.app.model.page.FundRealSingleResultModel;
import com.jfoenix.effects.JFXDepthManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jiangfei on 2020/12/13.
 */
@Slf4j
public class RealFundNode<T> extends StackPane {

    private FundBusinessService fundBusinessService;

    private T data;
    private String code;
    private String name;
    private EventHandler<T> handler;

    private MyScheduledService mss;

    private StackPane header;
    private Label label;

    public RealFundNode(String name) {
        this.setPrefWidth(200);
        this.setPrefHeight(60);

        JFXDepthManager.setDepth(this, 1);

        VBox content = new VBox();

        StackPane header = new StackPane();
        this.header = header;
        String color = getColor(0);
        header.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + color);
        header.getChildren().add(new Label(name));

        VBox.setVgrow(header, Priority.ALWAYS);
        header.setOnMouseClicked(event -> {
            if (handler != null) {
                handler.handler(data);
            }
        });
        header.setOnMousePressed(event -> JFXDepthManager.setDepth(this, 0));
        header.setOnMouseReleased(event -> JFXDepthManager.setDepth(this, 1));

        StackPane body = new StackPane();
        body.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");
        Label label = new Label(getRate(0));
        this.label = label;
        body.getChildren().add(label);
        body.setMinHeight(30);
        body.setOnMouseClicked(event -> {
            if (handler != null) {
                handler.handler(data);
            }
        });
        body.setOnMousePressed(event -> JFXDepthManager.setDepth(this, 0));
        body.setOnMouseReleased(event -> JFXDepthManager.setDepth(this, 1));

        content.getChildren().addAll(header, body);

        this.getChildren().add(content);
    }

    public RealFundNode(String name, String code, T data, EventHandler<T> handler, FundBusinessService fundBusinessService) {
        this(name);
        this.name = name;
        this.code = code;
        this.data = data;
        this.handler = handler;
        this.fundBusinessService = fundBusinessService;

        this.mss = new MyScheduledService(fundBusinessService, code);
        //等待5s开始、
        mss.setDelay(Duration.seconds(5));
        //程序执行时间
        mss.setPeriod(Duration.seconds(600));
        //启动失败重新启动
        mss.setRestartOnFailure(true);
        //程序启动失败后重新启动次数
        mss.setMaximumFailureCount(4);

        mss.valueProperty().addListener(new ChangeListener<FundRealModel>() {
            @Override
            public void changed(ObservableValue<? extends FundRealModel> observable, FundRealModel oldValue, FundRealModel newValue) {
                if (newValue != null) {
                    float rate = newValue.getGszzl();
                    String strRate = getRate(rate);
                    label.setText(strRate);

                    String color = getColor(rate);
                    header.setBackground(new Background(new BackgroundFill(Color.valueOf(color), null, null)));
                }
            }
        });

        mss.start();
    }

    public void dispose() {
        mss.cancel();
    }

    private String getRate(float rate) {
        String value = String.valueOf(rate) + "%";
        if (rate > 0) {
            value = "+" + value;
        }
        return value;
    }

    private String getColor(float rate) {
        if (rate >= 0) {
            return "#B5305F";
        } else {
            return "#01A05E";
        }
    }

    class MyScheduledService extends ScheduledService<FundRealModel> {
        private FundBusinessService fundBusinessService;
        private String code;

        public MyScheduledService(FundBusinessService fundBusinessService, String code) {
            this.fundBusinessService = fundBusinessService;
            this.code = code;
        }

        @Override
        protected Task<FundRealModel> createTask() {

            Task<FundRealModel> task = new Task<FundRealModel>() {
                @Override
                protected FundRealModel call() throws Exception {
                    FundRealSingleResultModel realSingleResultModel = fundBusinessService.queryFundReal(code);
                    return realSingleResultModel.getData();
                }
            };
            return task;
        }
    }


}
