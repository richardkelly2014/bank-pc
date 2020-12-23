package com.bank.engine.app.model;

import lombok.Data;

@Data
public class StockDailyModel {

    private String day;

    private Integer open;
    private Integer high;
    private Integer low;
    private Integer close;

    private Integer vol;
}
