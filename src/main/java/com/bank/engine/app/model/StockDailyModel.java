package com.bank.engine.app.model;

import lombok.Data;

@Data
public class StockDailyModel {

    private String stockCode;
    private String day;

    private Integer open;
    private Integer high;
    private Integer low;
    private Integer close;
    private Integer prevClose;

    private Integer change;
    private String pctChange;

    private Integer vol;
    private Integer amount;

    private String amplitude;
    private String turnoverRate;
}
