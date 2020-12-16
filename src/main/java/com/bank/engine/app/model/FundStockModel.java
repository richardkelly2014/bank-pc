package com.bank.engine.app.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class FundStockModel extends RecursiveTreeObject<FundStockModel> {

    private String fundCode;
    private String fundName;

    private String stockName;
    private String stockCode;
    private Integer year;
    private Integer quarter;

    private String holdRatio;
    private Double holdNum;
    private Double holdValue;


    public StringProperty stockCodeProperty() {
        return new SimpleStringProperty(stockCode);
    }

    public StringProperty stockNameProperty() {
        return new SimpleStringProperty(stockName);
    }

    public StringProperty holdRatioProperty() {
        return new SimpleStringProperty(holdRatio);
    }

    public StringProperty holdValueProperty() {
        return new SimpleStringProperty(String.valueOf(holdValue));
    }
}
