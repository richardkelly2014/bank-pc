package com.bank.engine.app.view.stock;

import com.bank.engine.app.business.StockBusinessService;
import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.model.StockRankModel;
import com.bank.engine.app.model.page.StockRankPageModel;
import com.bank.engine.app.util.DefaultThreadFactory;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bank.engine.app.util.UiComponentUtil.createIconButton;
import static com.bank.engine.app.util.UiComponentUtil.rateSetupCellFactory;
import static com.bank.engine.app.util.UiComponentUtil.setupCellValueFactory;

@Slf4j
@Component
@FXMLViewAndController(value = "template/stock/StockBasicView.fxml", title = "股票排行")
public class StockBasicController extends AbstractFxView implements InitializingBean {

    @Autowired
    private StockBusinessService stockBusinessService;

    @FXML
    private JFXTreeTableView<StockRankModel> recordTable;
    @FXML
    private JFXTreeTableColumn<StockRankModel, String> treeTableColumnPlate, treeTableColumnName, treeTableColumnCode, treeTableColumnDay;
    @FXML
    private JFXTreeTableColumn<StockRankModel, String> treeTableColumnDayRate, treeTableColumnThreeDayRate, treeTableColumnWeek, treeTableColumnMonth;
    @FXML
    private JFXTreeTableColumn<StockRankModel, String> columnOperation;

    @FXML
    private JFXTextField tf_stockCode, tf_stockName;
    @FXML
    private JFXButton btnSearch, btnRest;


    @FXML
    private JFXSpinner spinnerInfo;

    @FXML
    private Label totalLabel, pageNo, totalPage;
    @FXML
    private JFXComboBox<Integer> pageSize;
    @FXML
    private JFXButton btnPrev, btnNext;

    private ObservableList<StockRankModel> dummyData = FXCollections.observableArrayList();

    @Override
    public void initialize() {

        setupCellValueFactory(treeTableColumnPlate, StockRankModel::plateProperty);
        setupCellValueFactory(treeTableColumnName, StockRankModel::nameProperty);
        setupCellValueFactory(treeTableColumnCode, StockRankModel::codeProperty);
        setupCellValueFactory(treeTableColumnDay, StockRankModel::dayProperty);

        setupCellValueFactory(treeTableColumnDayRate, StockRankModel::dayRateProperty);
        setupCellValueFactory(treeTableColumnThreeDayRate, StockRankModel::threeDayRateProperty);
        setupCellValueFactory(treeTableColumnWeek, StockRankModel::weekProperty);
        setupCellValueFactory(treeTableColumnMonth, StockRankModel::monthProperty);

        rateSetupCellFactory(treeTableColumnDayRate);
        rateSetupCellFactory(treeTableColumnThreeDayRate);
        rateSetupCellFactory(treeTableColumnWeek);
        rateSetupCellFactory(treeTableColumnMonth);

        this.recordTable.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));
        this.recordTable.setShowRoot(false);

        this.columnOperation.setCellFactory((TreeTableColumn<StockRankModel, String> param) -> {
            JFXTreeTableCell<StockRankModel, String> cell = new JFXTreeTableCell<StockRankModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox hBox = new HBox(1);
                        hBox.setAlignment(Pos.TOP_CENTER);

                        JFXButton info = createIconButton(FontAwesomeSolid.INFO, 15, Color.GREEN);
                        JFXButton daily = createIconButton(FontAwesomeSolid.CHART_LINE, 15, Color.GREEN);

                        hBox.getChildren().addAll(info, daily);
                        setGraphic(hBox);
                    }
                }
            };
            return cell;
        });

        this.btnPrev.setOnAction(action -> pageChange(1));
        this.btnNext.setOnAction(action -> pageChange(2));

        this.btnSearch.setOnAction(action -> DefaultThreadFactory.runLater(() -> search(1)));

        this.btnRest.setOnAction(action -> {
            tf_stockCode.setText(null);
            tf_stockName.setText(null);
        });

        this.tf_stockCode.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                DefaultThreadFactory.runLater(() -> search(1));
            }
        });

        this.tf_stockName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                DefaultThreadFactory.runLater(() -> search(1));
            }
        });

    }


    public void refresh() {

        DefaultThreadFactory.runLater(() -> search(1));
    }

    private void pageChange(int type) {
        Integer no = Integer.valueOf(pageNo.getText());
        if (type == 1) {
            no = no - 1;
            no = no <= 0 ? 1 : no;
        } else {
            no = no + 1;
        }
        final int n = no;
        DefaultThreadFactory.runLater(() -> search(n));
    }


    private void search(int pn) {

        this.spinnerInfo.setVisible(true);
        this.recordTable.setDisable(true);
        this.dummyData.clear();

        int psize = pageSize.getValue();

        String stockCode = tf_stockCode.getText();
        String stockName = tf_stockName.getText();

        StockRankPageModel pageModel = stockBusinessService.queryStockRank(stockCode, stockName, pn, psize);

        int totalCount = pageModel.getTotalRecords();
        int no = pageModel.getPageNo();
        int page = pageModel.getTotalPage();

        DefaultThreadFactory.sleep(1000);

        dummyData.addAll(pageModel.getList());

        spinnerInfo.setVisible(false);
        recordTable.setDisable(false);

        Platform.runLater(() -> {
            pageNo.setText(String.valueOf(no));
            totalPage.setText(String.valueOf(page));
            totalLabel.setText(String.valueOf(totalCount));
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        getView();
    }
}
