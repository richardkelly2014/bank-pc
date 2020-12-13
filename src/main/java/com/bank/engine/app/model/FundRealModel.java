package com.bank.engine.app.model;

import lombok.Data;

/**
 * Created by jiangfei on 2020/12/13.
 */
@Data
public class FundRealModel {
    private String fundCode;
    private String fundName;

    //昨日净值
    private float dwjz;
    //今日估算值
    private float gsz;
    //今日估算涨幅
    private float gszzl;
}
