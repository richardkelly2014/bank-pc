package com.bank.engine.app.view.fund;

import com.bank.engine.app.business.FundBusinessService;
import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.model.FundRankModel;
import com.bank.engine.app.model.page.FundRankPageModel;
import com.bank.engine.app.util.DefaultThreadFactory;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bank.engine.app.util.UiComponentUtil.setupCellValueFactory;

@Slf4j
@Component
@FXMLViewAndController(value = "template/fund/FundRankView.fxml", title = "基金排行")
public class FundRankController extends AbstractFxView {

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

        setupCellValueFactory(fundTreeTableColumnWeek, FundRankModel::weekProperty);

        setupCellValueFactory(fundTreeTableColumnMonth, FundRankModel::monthProperty);
        setupCellValueFactory(fundTreeTableColumn3Month, FundRankModel::month3Property);
        setupCellValueFactory(fundTreeTableColumn6Month, FundRankModel::month6Property);

        setupCellValueFactory(fundTreeTableColumnYear, FundRankModel::yearProperty);
        setupCellValueFactory(fundTreeTableColumn2Year, FundRankModel::year2Property);
        setupCellValueFactory(fundTreeTableColumn3Year, FundRankModel::year3Property);

        setupCellFactory(fundTreeTableColumnWeek);
        setupCellFactory(fundTreeTableColumnMonth);
        setupCellFactory(fundTreeTableColumn3Month);
        setupCellFactory(fundTreeTableColumn6Month);
        setupCellFactory(fundTreeTableColumnYear);
        setupCellFactory(fundTreeTableColumn2Year);
        setupCellFactory(fundTreeTableColumn3Year);

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

    public void search(int pn) {

        this.spinnerInfo.setVisible(true);
        this.recordRankTable.setDisable(true);
        this.dummyData.clear();

        int psize = pageSize.getValue();
        String sortName = null;
        String sortType = null;

        if (recordRankTable.getSortOrder() != null && recordRankTable.getSortOrder().size() > 0) {
            TreeTableColumn<FundRankModel, ?> column = recordRankTable.getSortOrder().get(0);
            sortName = (String) column.getUserData();
            sortType = column.getSortType() == TreeTableColumn.SortType.ASCENDING ? "asc" : "desc";
        }

        FundRankPageModel pageModel = fundBusinessService.queryFundRank(sortName, sortType, pn, psize);

        int totalCount = pageModel.getTotalRecords();
        int no = pageModel.getPageNo();
        int page = pageModel.getTotalPage();

        dummyData.setAll(pageModel.getList());

        spinnerInfo.setVisible(false);
        recordRankTable.setDisable(false);

        Platform.runLater(() -> {
            pageNo.setText(String.valueOf(no));
            totalPage.setText(String.valueOf(page));
            totalLabel.setText(String.valueOf(totalCount));
        });
    }

    private void setupCellFactory(JFXTreeTableColumn<FundRankModel, String> column) {
        column.setCellFactory((TreeTableColumn<FundRankModel, String> param) -> {
            JFXTreeTableCell<FundRankModel, String> cell = new JFXTreeTableCell<FundRankModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Label label = new Label(item);
                        if (item.startsWith("+")) {
                            label.setTextFill(Paint.valueOf("#FF2043"));
                        } else if (item.startsWith("-")) {
                            label.setTextFill(Paint.valueOf("#15FF94"));
                        }
                        setGraphic(label);
                    }
                }
            };
            return cell;
        });
    }
}
