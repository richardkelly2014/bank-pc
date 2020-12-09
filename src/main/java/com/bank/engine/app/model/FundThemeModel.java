package com.bank.engine.app.model;

import cn.hutool.core.util.NumberUtil;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

/**
 * Created by jiangfei on 2020/12/9.
 */
@Data
public class FundThemeModel extends RecursiveTreeObject<FundThemeModel> {
    private String dataId;
    private String dataField;
    private String themeName;

    private Integer dayRate;
    private Integer weekRate;
    private Integer monthRate;
    private Integer threeMonthRate;
    private Integer sixMonthRate;

    private Integer yearRate;
    private Integer twoYearRate;
    private Integer threeYearRate;

    public StringProperty nameProperty() {
        return new SimpleStringProperty(themeName);
    }

    public StringProperty typeProperty() {
        return new SimpleStringProperty(dataField);
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
