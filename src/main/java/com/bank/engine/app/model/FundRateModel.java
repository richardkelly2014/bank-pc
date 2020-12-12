package com.bank.engine.app.model;

import lombok.Data;

/**
 * Created by jiangfei on 2020/12/12.
 */
@Data
public class FundRateModel {
    private String fundCode;
    private Integer unitValue;
    private Integer preUnitValue;
    private Integer grandValue;

    private Integer rateType;
    private Integer rate;

    private String day;
    private Integer week;
}
