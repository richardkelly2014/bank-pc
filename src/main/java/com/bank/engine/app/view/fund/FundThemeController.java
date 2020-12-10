package com.bank.engine.app.view.fund;

import com.bank.engine.app.business.FundBusinessService;
import com.bank.engine.app.config.AbstractFxView;
import com.bank.engine.app.config.FXMLViewAndController;
import com.bank.engine.app.model.FundThemeModel;
import com.bank.engine.app.model.base.ResultModel;
import com.bank.engine.app.model.page.FundThemeResultModel;
import com.bank.engine.app.util.DefaultThreadFactory;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.bank.engine.app.util.UiComponentUtil.createIconButton;
import static com.bank.engine.app.util.UiComponentUtil.rateSetupCellFactory;
import static com.bank.engine.app.util.UiComponentUtil.setupCellValueFactory;

@Slf4j
@Component
@FXMLViewAndController(value = "template/fund/FundThemeView.fxml", title = "基金主题")
public class FundThemeController extends AbstractFxView implements InitializingBean {

    @Autowired
    private FundBusinessService fundBusinessService;
    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @FXML
    private JFXTreeTableView<FundThemeModel> recordThemeTable;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> fundTreeTableColumnName;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> fundTreeTableColumnType;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> fundTreeTableColumnRate;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> fundTreeTableColumnWeek;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> fundTreeTableColumnMonth;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> fundTreeTableColumn3Month;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> fundTreeTableColumn6Month;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> fundTreeTableColumnYear;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> fundTreeTableColumn2Year;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> fundTreeTableColumn3Year;
    @FXML
    private JFXTreeTableColumn<FundThemeModel, String> columnOperation;

    @FXML
    private JFXSpinner spinnerInfo;

    @FXML
    private ToggleGroup themeGroup;
    @FXML
    private JFXTextField themeName;

    private ObservableList<FundThemeModel> dummyData = FXCollections.observableArrayList();

    @Override
    public void initialize() {
        setupCellValueFactory(fundTreeTableColumnName, FundThemeModel::nameProperty);
        setupCellValueFactory(fundTreeTableColumnType, FundThemeModel::typeProperty);
        setupCellValueFactory(fundTreeTableColumnRate, FundThemeModel::dayRateProperty);
        setupCellValueFactory(fundTreeTableColumnWeek, FundThemeModel::weekProperty);

        setupCellValueFactory(fundTreeTableColumnMonth, FundThemeModel::monthProperty);
        setupCellValueFactory(fundTreeTableColumn3Month, FundThemeModel::month3Property);
        setupCellValueFactory(fundTreeTableColumn6Month, FundThemeModel::month6Property);

        setupCellValueFactory(fundTreeTableColumnYear, FundThemeModel::yearProperty);
        setupCellValueFactory(fundTreeTableColumn2Year, FundThemeModel::year2Property);
        setupCellValueFactory(fundTreeTableColumn3Year, FundThemeModel::year3Property);

        rateSetupCellFactory(fundTreeTableColumnRate);
        rateSetupCellFactory(fundTreeTableColumnWeek);
        rateSetupCellFactory(fundTreeTableColumnMonth);
        rateSetupCellFactory(fundTreeTableColumn3Month);
        rateSetupCellFactory(fundTreeTableColumn6Month);
        rateSetupCellFactory(fundTreeTableColumnYear);
        rateSetupCellFactory(fundTreeTableColumn2Year);
        rateSetupCellFactory(fundTreeTableColumn3Year);

        this.recordThemeTable.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));
        this.recordThemeTable.setShowRoot(false);

        this.columnOperation.setCellFactory(param -> {
            JFXTreeTableCell<FundThemeModel, String> cell = new JFXTreeTableCell<FundThemeModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox hBox = new HBox(1);
                        hBox.setAlignment(Pos.TOP_CENTER);

                        JFXButton info = createIconButton(FontAwesomeSolid.INFO, 15, Color.GREEN);
                        info.setTooltip(new Tooltip("主题详情"));
                        info.setOnAction(action -> btnInfo(getTreeTableView().getTreeItem(getIndex()).getValue()));

                        JFXButton sync = createIconButton(FontAwesomeSolid.SYNC, 15, Color.GREEN);
                        sync.setOnAction(action -> btnSync(getTreeTableView().getTreeItem(getIndex()).getValue()));
                        hBox.getChildren().addAll(info, sync);

                        setGraphic(hBox);
                    }
                }
            };
            return cell;
        });

        this.themeGroup.selectedToggleProperty().addListener((ob, oldVal, newVal) -> DefaultThreadFactory.runLater(this::search));
        this.themeName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                DefaultThreadFactory.runLater(this::search);
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        getView();
    }

    public void refresh() {
        DefaultThreadFactory.runLater(this::search);
    }

    private void search() {

        this.spinnerInfo.setVisible(true);
        this.recordThemeTable.setDisable(true);
        this.dummyData.clear();

        String tName = this.themeName.getText();
        String tField = null;
        if (this.themeGroup.getSelectedToggle() != null) {
            tField = (String) this.themeGroup.getSelectedToggle().getUserData();
        }

        FundThemeResultModel resultModel = fundBusinessService.queryFundTheme(tName, tField);

        DefaultThreadFactory.sleep(1000);

        dummyData.addAll(resultModel.getData());

        spinnerInfo.setVisible(false);
        recordThemeTable.setDisable(false);

    }

    private void btnInfo(FundThemeModel model) {
        FundThemeInfoController infoController = new FundThemeInfoController(model);
        capableBeanFactory.autowireBean(infoController);
        infoController.showView(Modality.WINDOW_MODAL);
    }

    private void btnSync(FundThemeModel model) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("同步数据提示");
        alert.setHeaderText(null);
        alert.setContentText("是否确认同步数据?");
        String dataId = model.getDataId();

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            ResultModel resultModel = fundBusinessService.syncFundTheme(dataId);

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
}
