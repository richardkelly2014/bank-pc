package com.bank.engine.app.view.fund;

import com.bank.engine.app.business.FundBusinessService;
import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.model.FundBasicModel;
import com.bank.engine.app.model.base.ResultModel;
import com.bank.engine.app.model.page.FundBasicPageModel;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bank.engine.app.util.UiComponentUtil.createIconButton;
import static com.bank.engine.app.util.UiComponentUtil.setupCellValueFactory;

/**
 * Created by jiangfei on 2020/12/8.
 */
@Slf4j
@Component
@FXMLViewAndController(value = "template/fund/FundBasicView.fxml", title = "基金市场")
public class FundBasicController extends AbstractFxView implements InitializingBean {
    @Autowired
    private FundBusinessService fundBusinessService;

    @FXML
    private JFXTreeTableView<FundBasicModel> recordTable;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> fundTreeTableColumnCode;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> fundTreeTableColumnName;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> fundTreeTableColumnType;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> fundTreeTableColumnInvestType;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> fundTreeTableColumnRisk;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> fundTreeTableColumnIssue;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> fundTreeTableColumnSetup;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> fundTreeTableColumnAsset;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> fundTreeTableColumnShare;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> fundTreeTableColumnCompany;
    @FXML
    private JFXTreeTableColumn<FundBasicModel, String> columnOperation;

    @FXML
    private JFXTextField tf_fundCode, tf_fundName;

    @FXML
    private ToggleGroup fundTypeGroup;

    @FXML
    private JFXSpinner spinnerInfo;
    @FXML
    private Label totalLabel, pageNo, totalPage;
    @FXML
    private JFXComboBox<Integer> pageSize;
    @FXML
    private JFXButton btnPrev, btnNext;

    /**
     * 数据list
     */
    private ObservableList<FundBasicModel> dummyData = FXCollections.observableArrayList();

    @Override
    public void initialize() {
        setupCellValueFactory(fundTreeTableColumnCode, FundBasicModel::fundCodeProperty);
        setupCellValueFactory(fundTreeTableColumnName, FundBasicModel::fundNameProperty);
        setupCellValueFactory(fundTreeTableColumnType, FundBasicModel::fundTypeProperty);
        setupCellValueFactory(fundTreeTableColumnInvestType, FundBasicModel::fundInvestTypeProperty);
        setupCellValueFactory(fundTreeTableColumnRisk, FundBasicModel::fundRiskProperty);
        setupCellValueFactory(fundTreeTableColumnIssue, FundBasicModel::issueProperty);
        setupCellValueFactory(fundTreeTableColumnSetup, FundBasicModel::setupProperty);
        setupCellValueFactory(fundTreeTableColumnAsset, FundBasicModel::assetProperty);
        setupCellValueFactory(fundTreeTableColumnShare, FundBasicModel::shareProperty);
        setupCellValueFactory(fundTreeTableColumnCompany, FundBasicModel::fundCompanyProperty);

        this.columnOperation.setCellFactory((TreeTableColumn<FundBasicModel, String> param) -> {
            JFXTreeTableCell<FundBasicModel, String> cell = new JFXTreeTableCell<FundBasicModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox hBox = new HBox(1);
                        hBox.setAlignment(Pos.TOP_CENTER);

                        JFXButton info = createIconButton(FontAwesomeSolid.INFO, 15, Color.GREEN);
                        info.setTooltip(new Tooltip("基金详情"));

                        JFXButton add = createIconButton(FontAwesomeSolid.PLUS, 15, Color.GREEN);
                        add.setTooltip(new Tooltip("添加排行"));
                        add.setOnAction(action -> addToAnalyse(getTreeTableView().getTreeItem(getIndex()).getValue()));

                        hBox.getChildren().addAll(info, add);

                        setGraphic(hBox);
                    }
                }
            };
            return cell;
        });

        this.recordTable.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));
        this.recordTable.setShowRoot(false);

        this.btnPrev.setOnAction(action -> pageChange(1));
        this.btnNext.setOnAction(action -> pageChange(2));

        this.fundTypeGroup.selectedToggleProperty().addListener((val, oldVal, newVal) -> DefaultThreadFactory.runLater(() -> search(1)));

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

    @Override
    public void afterPropertiesSet() throws Exception {
        getView();
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
        String fundCode = tf_fundCode.getText();
        String fundName = tf_fundName.getText();
        String fundType = null;
        if (this.fundTypeGroup.getSelectedToggle() != null) {
            fundType = (String) this.fundTypeGroup.getSelectedToggle().getUserData();
        }

        FundBasicPageModel pageModel = fundBusinessService.queryFundBasic(fundCode, fundName, fundType, pn, psize);

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

    private void addToAnalyse(FundBasicModel model) {
        String fundCode = model.getFundCode();
        ResultModel resultModel = fundBusinessService.addToAnalyse(fundCode);

        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setTitle("提示");
        alertInfo.setHeaderText(null);

        if (resultModel.getCode() == 0) {
            alertInfo.setContentText("添加成功");
            alertInfo.showAndWait();
        } else {
            alertInfo.setContentText(resultModel.getMsg());
            alertInfo.showAndWait();
        }
    }
}
