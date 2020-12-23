package com.bank.engine.app.model;

import cn.hutool.core.util.NumberUtil;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class StockRankModel extends RecursiveTreeObject<StockRankModel> {
    private String stockCode;
    private String stockName;

    private String day;

    private Integer dayRate;
    private Integer threeDayRate;

    private Integer weekRate;
    private Integer monthRate;
    private Integer plate;

    public StringProperty codeProperty() {
        return new SimpleStringProperty(stockCode);
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(stockName);
    }

    public StringProperty dayProperty() {
        return new SimpleStringProperty(day);
    }

    public StringProperty dayRateProperty() {
        int rate = dayRate != null ? dayRate : 0;
        return rateProperty(dayRate);
    }

    public StringProperty threeDayRateProperty() {
        int rate = threeDayRate != null ? threeDayRate : 0;
        return rateProperty(rate);
    }

    public StringProperty weekProperty() {
        int rate = weekRate != null ? weekRate : 0;
        return rateProperty(rate);
    }

    public StringProperty monthProperty() {
        int rate = monthRate != null ? monthRate : 0;
        return rateProperty(rate);
    }

    public StringProperty plateProperty() {
        String value = "沪市";
        if (plate == 1) {
            value = "沪市";
        } else if (plate == 2) {
            value = "深市";
        } else if (plate == 3) {
            value = "中小";
        } else if (plate == 4) {
            value = "创业";
        } else if (plate == 5) {
            value = "科创";
        } else {
            value = "未知";
        }
        return new SimpleStringProperty(value);
    }

    private StringProperty rateProperty(int rate) {
        double v = NumberUtil.div(rate, 100, 2);
        String value = String.valueOf(v) + "%";
        if (v > 0) {
            value = "+" + value;
        }
        return new SimpleStringProperty(value);
    }
}
