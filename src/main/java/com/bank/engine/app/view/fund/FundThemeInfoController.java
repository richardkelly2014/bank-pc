package com.bank.engine.app.view.fund;

import com.bank.engine.app.business.FundBusinessService;
import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.model.FundThemeInfoModel;
import com.bank.engine.app.model.FundThemeModel;
import com.bank.engine.app.model.base.ResultModel;
import com.bank.engine.app.model.page.FundThemeInfoResultModel;
import com.bank.engine.app.util.DefaultThreadFactory;
import com.bank.engine.app.view.UiComponent;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.springframework.beans.factory.annotation.Autowired;

import static com.bank.engine.app.util.UiComponentUtil.createIconButton;
import static com.bank.engine.app.util.UiComponentUtil.rateSetupCellFactory;
import static com.bank.engine.app.util.UiComponentUtil.setupCellValueFactory;

/**
 * Created by jiangfei on 2020/12/9.
 */
@Slf4j
@FXMLViewAndController(value = "template/fund/FundThemeInfoView.fxml", title = "基金主题详情")
public class FundThemeInfoController extends AbstractFxView {

    @Autowired
    private FundBusinessService fundBusinessService;
    @Autowired
    private UiComponent uiComponent;

    @FXML
    private JFXTreeTableView<FundThemeInfoModel> recordTable;
    @FXML
    private JFXTreeTableColumn<FundThemeInfoModel, String> fundTreeTableColumnCode;
    @FXML
    private JFXTreeTableColumn<FundThemeInfoModel, String> fundTreeTableColumnName;
    @FXML
    private JFXTreeTableColumn<FundThemeInfoModel, String> fundTreeTableColumnWeek;
    @FXML
    private JFXTreeTableColumn<FundThemeInfoModel, String> fundTreeTableColumnMonth;
    @FXML
    private JFXTreeTableColumn<FundThemeInfoModel, String> fundTreeTableColumn3Month;
    @FXML
    private JFXTreeTableColumn<FundThemeInfoModel, String> fundTreeTableColumn6Month;
    @FXML
    private JFXTreeTableColumn<FundThemeInfoModel, String> fundTreeTableColumnYear;
    @FXML
    private JFXTreeTableColumn<FundThemeInfoModel, String> columnOperation;
    @FXML
    private JFXSpinner spinnerInfo;
    @FXML
    private JFXButton btnRefresh;

    private FundThemeModel fundThemeModel;
    //数据绑定
    private ObservableList<FundThemeInfoModel> dummyData = FXCollections.observableArrayList();

    public FundThemeInfoController(FundThemeModel fundThemeModel) {

        this.fundThemeModel = fundThemeModel;
    }

    @Override
    public void initialize() {

        setupCellValueFactory(fundTreeTableColumnCode, FundThemeInfoModel::codeProperty);
        setupCellValueFactory(fundTreeTableColumnName, FundThemeInfoModel::nameProperty);
        setupCellValueFactory(fundTreeTableColumnWeek, FundThemeInfoModel::weekProperty);
        setupCellValueFactory(fundTreeTableColumnMonth, FundThemeInfoModel::monthProperty);
        setupCellValueFactory(fundTreeTableColumn3Month, FundThemeInfoModel::month3Property);
        setupCellValueFactory(fundTreeTableColumn6Month, FundThemeInfoModel::month6Property);
        setupCellValueFactory(fundTreeTableColumnYear, FundThemeInfoModel::yearProperty);

        rateSetupCellFactory(fundTreeTableColumnWeek);
        rateSetupCellFactory(fundTreeTableColumnMonth);
        rateSetupCellFactory(fundTreeTableColumn3Month);
        rateSetupCellFactory(fundTreeTableColumn6Month);
        rateSetupCellFactory(fundTreeTableColumnYear);

        this.recordTable.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));
        this.recordTable.setShowRoot(false);

        this.btnRefresh.setOnAction(action -> DefaultThreadFactory.runLater(this::search));

        this.columnOperation.setCellFactory(param -> {
            JFXTreeTableCell<FundThemeInfoModel, String> cell = new JFXTreeTableCell<FundThemeInfoModel, String>() {
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
                        info.setOnAction(a -> fundInfo(getTreeTableView().getTreeItem(getIndex()).getValue()));

                        JFXButton add = createIconButton(FontAwesomeSolid.PLUS_SQUARE, 15, Color.GREEN);
                        add.setTooltip(new Tooltip("加入排行"));
                        add.setOnAction(action -> btnAddToAnalyse(getTreeTableView().getTreeItem(getIndex()).getValue()));

                        hBox.getChildren().addAll(info, add);

                        setGraphic(hBox);
                    }
                }
            };
            return cell;
        });

        DefaultThreadFactory.runLater(this::search);
    }

    private void search() {
        this.spinnerInfo.setVisible(true);
        this.recordTable.setDisable(true);
        this.dummyData.clear();

        FundThemeInfoResultModel resultModel = fundBusinessService.queryFundThemeInfo(this.fundThemeModel.getDataId());

        DefaultThreadFactory.sleep(1000);

        dummyData.addAll(resultModel.getData());

        spinnerInfo.setVisible(false);
        recordTable.setDisable(false);
    }

    private void fundInfo(FundThemeInfoModel infoModel) {
        String code = infoModel.getFundCode();
        String fundName = infoModel.getFundName();
        uiComponent.showFundInfo(code, fundName);
    }

    /**
     * 添加到排行中
     *
     * @param infoModel
     */
    private void btnAddToAnalyse(FundThemeInfoModel infoModel) {
        String fundCode = infoModel.getFundCode();
        int type = 1;
        ResultModel resultModel = fundBusinessService.addToAnalyse(fundCode, type);

        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setTitle("提示");
        alertInfo.setHeaderText(null);

        if (resultModel.getCode() == 0) {
            alertInfo.setContentText("添加成功,稍后刷新数据！");
            alertInfo.showAndWait();
        } else {
            alertInfo.setContentText(resultModel.getMsg());
            alertInfo.showAndWait();
        }
    }

    @Override
    public String getDefaultTitle() {

        return super.getDefaultTitle() + "-----" + fundThemeModel.getThemeName();
    }
}
