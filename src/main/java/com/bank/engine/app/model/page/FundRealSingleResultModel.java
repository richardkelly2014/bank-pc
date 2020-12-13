package com.bank.engine.app.model.page;

import com.bank.engine.app.model.FundRealModel;
import com.bank.engine.app.model.base.ResultModel;
import lombok.Data;

/**
 * Created by jiangfei on 2020/12/13.
 */
@Data
public class FundRealSingleResultModel extends ResultModel {
    private FundRealModel data;
}
