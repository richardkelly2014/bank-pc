package com.bank.engine.app.model;

import cn.hutool.core.util.NumberUtil;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class FundRankModel extends RecursiveTreeObject<FundRankModel> {
    private String fundName;
    private String fundCode;

    private String fundDay;

    private Integer fundUnit;
    private Integer fundGrand;

    private Integer dayRate;
    private Integer weekRate;
    private Integer monthRate;
    private Integer threeMonthRate;
    private Integer sixMonthRate;

    private Integer yearRate;
    private Integer twoYearRate;
    private Integer threeYearRate;

    public StringProperty codeProperty() {

        return new SimpleStringProperty(fundCode);
    }

    public StringProperty nameProperty() {

        return new SimpleStringProperty(fundName);
    }

    public StringProperty dayProperty() {

        return new SimpleStringProperty(fundDay);
    }


    public StringProperty unitProperty() {
        int unitValue = fundUnit;
        double value = NumberUtil.div(unitValue, 10000, 4);
        return new SimpleStringProperty(String.valueOf(value));
    }

    public StringProperty grandProperty() {
        int grandValue = fundGrand;
        double value = NumberUtil.div(grandValue, 10000, 4);
        return new SimpleStringProperty(String.valueOf(value));
    }

    public StringProperty dayRateProperty() {
        int rate = dayRate != null ? dayRate : 0;

        return fundRateProperty(rate);
    }

    public StringProperty weekProperty() {
        return fundRateProperty(weekRate);
    }

    public StringProperty monthProperty() {
        return fundRateProperty(monthRate);
    }

    public StringProperty month3Property() {
        return fundRateProperty(threeMonthRate);
    }

    public StringProperty month6Property() {
        return fundRateProperty(sixMonthRate);
    }

    public StringProperty yearProperty() {
        return fundRateProperty(yearRate);
    }

    public StringProperty year2Property() {
        return fundRateProperty(twoYearRate);
    }

    public StringProperty year3Property() {
        return fundRateProperty(threeYearRate);
    }

    private StringProperty fundRateProperty(int rate) {
        double v = NumberUtil.div(rate, 100, 2);
        String value = String.valueOf(v) + "%";
        if (v > 0) {
            value = "+" + value;
        }
        return new SimpleStringProperty(value);
    }
}
