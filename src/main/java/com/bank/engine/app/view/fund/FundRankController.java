package com.bank.engine.app.view.fund;

import com.bank.engine.app.business.FundBusinessService;
import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.model.FundRankModel;
import com.bank.engine.app.model.base.ResultModel;
import com.bank.engine.app.model.page.FundRankPageModel;
import com.bank.engine.app.util.DefaultThreadFactory;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.bank.engine.app.util.UiComponentUtil.createIconButton;
import static com.bank.engine.app.util.UiComponentUtil.rateSetupCellFactory;
import static com.bank.engine.app.util.UiComponentUtil.setupCellValueFactory;

@Slf4j
@Component
@FXMLViewAndController(value = "template/fund/FundRankView.fxml", title = "基金排行")
public class FundRankController extends AbstractFxView implements InitializingBean {

    @Autowired
    private FundBusinessService fundBusinessService;

    @FXML
    private JFXTreeTableView<FundRankModel> recordRankTable;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumnCode;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumnName;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumnDay;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumnUnit;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumnGrand;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumnRate;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumnWeek;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumnMonth;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumn3Month;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumn6Month;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumnYear;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumn2Year;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> fundTreeTableColumn3Year;
    @FXML
    private JFXTreeTableColumn<FundRankModel, String> columnOperation;

    @FXML
    private JFXTextField tf_fundCode, tf_fundName;
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

    //数据
    private ObservableList<FundRankModel> dummyData = FXCollections.observableArrayList();

    @Override
    public void initialize() {
        setupCellValueFactory(fundTreeTableColumnCode, FundRankModel::codeProperty);
        setupCellValueFactory(fundTreeTableColumnName, FundRankModel::nameProperty);
        setupCellValueFactory(fundTreeTableColumnDay, FundRankModel::dayProperty);

        setupCellValueFactory(fundTreeTableColumnUnit, FundRankModel::unitProperty);
        setupCellValueFactory(fundTreeTableColumnGrand, FundRankModel::grandProperty);

        setupCellValueFactory(fundTreeTableColumnRate, FundRankModel::dayRateProperty);
        setupCellValueFactory(fundTreeTableColumnWeek, FundRankModel::weekProperty);

        setupCellValueFactory(fundTreeTableColumnMonth, FundRankModel::monthProperty);
        setupCellValueFactory(fundTreeTableColumn3Month, FundRankModel::month3Property);
        setupCellValueFactory(fundTreeTableColumn6Month, FundRankModel::month6Property);

        setupCellValueFactory(fundTreeTableColumnYear, FundRankModel::yearProperty);
        setupCellValueFactory(fundTreeTableColumn2Year, FundRankModel::year2Property);
        setupCellValueFactory(fundTreeTableColumn3Year, FundRankModel::year3Property);

        rateSetupCellFactory(fundTreeTableColumnRate);
        rateSetupCellFactory(fundTreeTableColumnWeek);
        rateSetupCellFactory(fundTreeTableColumnMonth);
        rateSetupCellFactory(fundTreeTableColumn3Month);
        rateSetupCellFactory(fundTreeTableColumn6Month);
        rateSetupCellFactory(fundTreeTableColumnYear);
        rateSetupCellFactory(fundTreeTableColumn2Year);
        rateSetupCellFactory(fundTreeTableColumn3Year);

        this.columnOperation.setCellFactory((TreeTableColumn<FundRankModel, String> param) -> {
            JFXTreeTableCell<FundRankModel, String> cell = new JFXTreeTableCell<FundRankModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox hBox = new HBox(1);
                        hBox.setAlignment(Pos.TOP_CENTER);

                        JFXButton sync = createIconButton(FontAwesomeSolid.SYNC, 15, Color.GREEN);

                        sync.setOnAction(action -> sync(getTreeTableView().getTreeItem(getIndex()).getValue(), "2"));

                        JFXButton recy = createIconButton(FontAwesomeSolid.RECYCLE, 15, Color.BLUE);
                        recy.setOnAction(action -> sync(getTreeTableView().getTreeItem(getIndex()).getValue(), "1"));

                        hBox.getChildren().addAll(sync, recy);
                        setGraphic(hBox);
                    }
                }
            };
            return cell;
        });

        this.recordRankTable.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));
        this.recordRankTable.setShowRoot(false);

        this.btnPrev.setOnAction(action -> pageChange(1));
        this.btnNext.setOnAction(action -> pageChange(2));

        this.recordRankTable.setSortPolicy(tv -> {
            //有选择排序字段
            if (recordRankTable.getSortOrder() != null && recordRankTable.getSortOrder().size() > 0) {
                DefaultThreadFactory.runLater(() -> search(1));
            }
            return true;
        });

        this.btnSearch.setOnAction(action -> DefaultThreadFactory.runLater(() -> search(1)));

        this.btnRest.setOnAction(action -> {
            tf_fundCode.setText(null);
            tf_fundName.setText(null);
        });

        this.tf_fundCode.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                DefaultThreadFactory.runLater(() -> search(1));
            }
        });

        this.tf_fundName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                DefaultThreadFactory.runLater(() -> search(1));
            }
        });

    }

    /**
     * 刷新
     */
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
        this.recordRankTable.setDisable(true);
        this.dummyData.clear();

        int psize = pageSize.getValue();

        String fundCode = tf_fundCode.getText();
        String fundName = tf_fundName.getText();

        String sortName = null;
        String sortType = null;

        if (recordRankTable.getSortOrder() != null && recordRankTable.getSortOrder().size() > 0) {
            TreeTableColumn<FundRankModel, ?> column = recordRankTable.getSortOrder().get(0);
            sortName = (String) column.getUserData();
            sortType = column.getSortType() == TreeTableColumn.SortType.ASCENDING ? "asc" : "desc";
        }

        FundRankPageModel pageModel = fundBusinessService.queryFundRank(fundCode, fundName, sortName, sortType, pn, psize);

        int totalCount = pageModel.getTotalRecords();
        int no = pageModel.getPageNo();
        int page = pageModel.getTotalPage();

        DefaultThreadFactory.sleep(1000);

        dummyData.addAll(pageModel.getList());

        spinnerInfo.setVisible(false);
        recordRankTable.setDisable(false);

        Platform.runLater(() -> {
            pageNo.setText(String.valueOf(no));
            totalPage.setText(String.valueOf(page));
            totalLabel.setText(String.valueOf(totalCount));
        });
    }

    /**
     * 同步
     *
     * @param model
     * @param type
     */
    private void sync(FundRankModel model, String type) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("同步数据提示");
        alert.setHeaderText(null);
        alert.setContentText("是否确认同步数据?");
        String fundCode = model.getFundCode();

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            ResultModel resultModel = fundBusinessService.syncFundAnalyse(fundCode, type);

            Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
            alertInfo.setTitle("提示");
            alertInfo.setHeaderText(null);

            if (resultModel.getCode() == 0) {
                alertInfo.setContentText("已经提交后台同步，请稍后刷新页面！");
                alertInfo.showAndWait();
            } else {
                alertInfo.setContentText("提交失败，请与管理人员联系!");
                alertInfo.showAndWait();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        getView();
    }
}
