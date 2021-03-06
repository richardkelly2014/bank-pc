package com.bank.engine.app.view.fund;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.bank.engine.app.business.FundBusinessService;
import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.model.FundBasicModel;
import com.bank.engine.app.model.FundRankModel;
import com.bank.engine.app.model.FundRateModel;
import com.bank.engine.app.model.FundStockModel;
import com.bank.engine.app.model.page.FundInfoResultModel;
import com.bank.engine.app.model.page.FundRatePageModel;
import com.bank.engine.app.model.page.FundStockResultModel;
import com.bank.engine.app.util.DateTimeUtil;
import com.bank.engine.app.util.DefaultThreadFactory;
import com.bank.engine.app.util.FileUtil;
import com.google.common.collect.Lists;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.bank.engine.app.util.UiComponentUtil.setupCellValueFactory;

/**
 * Created by jiangfei on 2020/12/12.
 */
@Slf4j
@FXMLViewAndController(value = "template/fund/FundInfoView.fxml", title = "基金详情")
public class FundInfoController extends AbstractFxView {
    @Autowired
    private FundBusinessService fundBusinessService;

    private String fundCode;
    private String fundName;
    @FXML
    private VBox vBoxContent;
    @FXML
    private BorderPane mainPane;
    @FXML
    private WebView unitWebView;
    @FXML
    private Label lblFundName, lblFundCode, lblFundTypeName, lblFundRiskName;
    @FXML
    private Label lblUnitDay, lblUnitValue, lblZF, lblGrandValue;
    @FXML
    private Label lblMonth, lblYear, lblMonth3, lblYear2, lblMonth6, lblYear3;
    @FXML
    private Label lblasset, lblshare, lblissue, lblsetup, lblcompany;
    @FXML
    private JFXButton btnSyncStock;
    @FXML
    private JFXTreeTableView<FundStockModel> jfxFundStock, jfxtbv1;
    @FXML
    private JFXTreeTableColumn<FundStockModel, String> jfxtc1, jfxtc2, jfxtc3, jfxtc4, jfxtc11, jfxtc12, jfxtc13, jfxtc14;


    private ObservableList<FundStockModel> dummyData1 = FXCollections.observableArrayList();
    private ObservableList<FundStockModel> dummyData2 = FXCollections.observableArrayList();

    /**
     * 用于与Javascript引擎通信。
     */
    private JSObject javascriptConnector;

    public FundInfoController(String code, String name) {
        this.fundCode = code;
        this.fundName = name;
    }

    @Override
    public void initialize() {
        vBoxContent.prefWidthProperty().bind(mainPane.widthProperty().subtract(30));

        setupCellValueFactory(jfxtc1, FundStockModel::stockNameProperty);
        setupCellValueFactory(jfxtc2, FundStockModel::stockCodeProperty);
        setupCellValueFactory(jfxtc3, FundStockModel::holdRatioProperty);
        setupCellValueFactory(jfxtc4, FundStockModel::holdValueProperty);

        setupCellValueFactory(jfxtc11, FundStockModel::stockNameProperty);
        setupCellValueFactory(jfxtc12, FundStockModel::stockCodeProperty);
        setupCellValueFactory(jfxtc13, FundStockModel::holdRatioProperty);
        setupCellValueFactory(jfxtc14, FundStockModel::holdValueProperty);

        this.jfxFundStock.setRoot(new RecursiveTreeItem<>(dummyData1, RecursiveTreeObject::getChildren));
        this.jfxFundStock.setShowRoot(false);

        this.jfxtbv1.setRoot(new RecursiveTreeItem<>(dummyData2, RecursiveTreeObject::getChildren));
        this.jfxtbv1.setShowRoot(false);

        WebEngine webEngine = this.unitWebView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        // 加载指示器
        webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // 获取Javascript连接器对象。
                javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
                DefaultThreadFactory.runLater(this::showEchart);
            }
        });

        URL url = FileUtil.createURL("template/html/fundUnit.html");
        if (url != null) {
            webEngine.load(url.toExternalForm());
        }

        DefaultThreadFactory.runLater(this::showInfoBase);

        DefaultThreadFactory.runLater(this::showFundStock);

        this.btnSyncStock.setOnAction(action -> {
            DefaultThreadFactory.runLater(() -> {
                fundBusinessService.syncFundStock(fundCode, fundName);
            });
        });
    }

    /**
     * 查询基本信息
     */
    private void showInfoBase() {
        FundInfoResultModel resultModel = fundBusinessService.queryFundInfo(fundCode);
        FundBasicModel basicModel = resultModel.getData().getBasic();
        FundRankModel rankModel = resultModel.getData().getRate();
        if (rankModel != null) {
            int unitValue = rankModel.getFundUnit();
            double uvalue = NumberUtil.div(unitValue, 10000, 4);

            int grandValue = rankModel.getFundGrand();
            double gvalue = NumberUtil.div(grandValue, 10000, 4);

            int dayRate = rankModel.getDayRate();
            int month = rankModel.getMonthRate();
            int month3 = rankModel.getThreeMonthRate();
            int month6 = rankModel.getSixMonthRate();

            int year = rankModel.getYearRate();
            int year2 = rankModel.getTwoYearRate();
            int year3 = rankModel.getThreeYearRate();

            Platform.runLater(() -> {
                lblFundName.setText(basicModel.getFundName());
                lblFundCode.setText(basicModel.getFundCode());
                lblFundTypeName.setText(basicModel.getFundTypeName() + "->" + basicModel.getFundInvestTypeName());
                lblFundRiskName.setText(basicModel.getFundRiskName());

                lblUnitDay.setText("(" + rankModel.getFundDay() + ")");

                lblUnitValue.setText(String.valueOf(uvalue));
                lblGrandValue.setText(String.valueOf(gvalue));
                lblZF.setText(rateString(dayRate));

                if (dayRate > 0) {
                    lblUnitValue.setTextFill(Color.RED);
                    lblGrandValue.setTextFill(Color.RED);
                    lblZF.setTextFill(Color.RED);
                } else {
                    lblUnitValue.setTextFill(Color.GREEN);
                    lblGrandValue.setTextFill(Color.GREEN);
                    lblZF.setTextFill(Color.GREEN);
                }

                lblMonth.setText(rateString(month));
                lblMonth3.setText(rateString(month3));
                lblMonth6.setText(rateString(month6));
                lblYear.setText(rateString(year));
                lblYear2.setText(rateString(year2));
                lblYear3.setText(rateString(year3));

                setlabfill(lblMonth, month);
                setlabfill(lblMonth3, month3);
                setlabfill(lblMonth6, month6);
                setlabfill(lblYear, year);
                setlabfill(lblYear2, year2);
                setlabfill(lblYear3, year3);

                lblasset.setText(basicModel.getAssetScale() + " 亿元");
                lblshare.setText(basicModel.getShareScale() + " 亿份");

                lblissue.setText(basicModel.getIssueDate());
                lblsetup.setText(basicModel.getSetupDate());

                lblcompany.setText(basicModel.getFundCompany());
            });
        }
    }

    /**
     * 查询基金持仓
     */
    private void showFundStock() {
        int quarter = DateTimeUtil.getQuarter();
        int year = DateTimeUtil.getYear();

        FundStockResultModel resultModel = fundBusinessService.queryFundStock(fundCode, year);

        List<FundStockModel> list = resultModel.getData();

        dummyData2.addAll(list);

        final int fq = quarter;
        List<FundStockModel> quarterList = list.stream().filter(model -> model.getQuarter() == fq).collect(Collectors.toList());
        if (quarterList.size() == 0) {
            quarter = quarter - 1;
            final int fq1 = quarter;
            quarterList = list.stream().filter(model -> model.getQuarter() == fq1).collect(Collectors.toList());
        }

        dummyData1.addAll(quarterList);

    }

    private void showEchart() {

        FundRatePageModel pageModel = fundBusinessService.queryFundRate(fundCode, 1, 900);
        List<FundRateModel> list = pageModel.getList();

        List<String> days = Lists.newArrayList();
        List<Double> rates = Lists.newArrayList();
        int size = list.size();

        for (int i = size - 1; i >= 0; i--) {
            FundRateModel rateModel = list.get(i);
            days.add(rateModel.getDay());
            int unitValue = rateModel.getUnitValue();
            double value = NumberUtil.div(unitValue, 10000, 4);
            rates.add(value);
        }

        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("category", days);
        map.put("data", rates);
        Platform.runLater(() -> javascriptConnector.call("showResult", JSON.toJSONString(map)));
    }

    @Override
    public String getDefaultTitle() {

        return super.getDefaultTitle() + "---" + fundName + "(" + fundCode + ")";
    }

    private String rateString(int rate) {
        double v = NumberUtil.div(rate, 100, 2);
        String value = String.valueOf(v) + "%";
        if (v > 0) {
            value = "+" + value;
        }
        return String.valueOf(value);
    }

    private void setlabfill(Label label, int rate) {
        if (rate > 0) {
            label.setTextFill(Color.RED);
        } else if (rate < 0) {
            label.setTextFill(Color.GREEN);
        }
    }
}
