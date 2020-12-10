package com.bank.engine.app.model;

import cn.hutool.core.util.NumberUtil;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class FundThemeInfoModel extends RecursiveTreeObject<FundThemeInfoModel> {

    private String dataId;
    private String dataField;
    private String themeName;
    private String fundCode;
    private String fundName;

    private Integer weekRate;
    private Integer monthRate;
    private Integer threeMonthRate;
    private Integer sixMonthRate;

    private Integer yearRate;

    public StringProperty codeProperty() {
        return new SimpleStringProperty(fundCode);
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(fundName);
    }

    public StringProperty weekProperty() {
        int rate = weekRate != null ? weekRate : 0;
        return fundRateProperty(rate);
    }

    public StringProperty monthProperty() {
        int rate = monthRate != null ? monthRate : 0;
        return fundRateProperty(rate);
    }

    public StringProperty month3Property() {
        int rate = threeMonthRate != null ? threeMonthRate : 0;
        return fundRateProperty(rate);
    }

    public StringProperty month6Property() {
        int rate = sixMonthRate != null ? sixMonthRate : 0;
        return fundRateProperty(rate);
    }

    public StringProperty yearProperty() {
        int rate = yearRate != null ? yearRate : 0;
        return fundRateProperty(rate);
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
