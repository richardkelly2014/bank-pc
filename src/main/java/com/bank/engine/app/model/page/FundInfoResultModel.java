package com.bank.engine.app.model.page;

import com.bank.engine.app.model.FundBasicModel;
import com.bank.engine.app.model.FundRankModel;
import com.bank.engine.app.model.base.ResultModel;
import lombok.Data;

/**
 * Created by jiangfei on 2020/12/12.
 */
@Data
public class FundInfoResultModel extends ResultModel {
    private Data data;

    @lombok.Data
    public static class Data{
        private FundBasicModel basic;
        private FundRankModel rate;
    }
}
