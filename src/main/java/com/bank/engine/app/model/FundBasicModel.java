package com.bank.engine.app.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

/**
 * Created by jiangfei on 2020/12/8.
 */
@Data
public class FundBasicModel extends RecursiveTreeObject<FundBasicModel> {
    private String fundCode;
    private String fundName;

    private Integer fundType;
    private String fundTypeName;

    private Integer fundInvestType;
    private String fundInvestTypeName;

    private String fundShortPy;
    private String fundPy;

    private String fundCompany;
    private String fundCompanyCode;

    private Integer fundRisk;
    private String fundRiskName;

    private String issueDate;
    private String setupDate;

    private String assetScale;
    private String shareScale;

    private Integer status;

    public StringProperty fundCodeProperty() {
        return new SimpleStringProperty(fundCode);
    }

    public StringProperty fundNameProperty() {
        return new SimpleStringProperty(fundName);
    }

    public StringProperty fundTypeProperty() {
        return new SimpleStringProperty(fundTypeName);
    }

    public StringProperty fundInvestTypeProperty() {
        return new SimpleStringProperty(fundInvestTypeName);
    }

    public StringProperty fundRiskProperty() {
        return new SimpleStringProperty(fundRiskName);
    }

    public StringProperty setupProperty() {
        return new SimpleStringProperty(setupDate);
    }

    public StringProperty assetProperty() {
        return new SimpleStringProperty(assetScale);
    }

    public StringProperty shareProperty() {
        return new SimpleStringProperty(shareScale);
    }

    public StringProperty fundCompanyProperty() {
        return new SimpleStringProperty(fundCompany);
    }

}
