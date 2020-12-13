package com.bank.engine.app.model.page;

import com.bank.engine.app.model.FundRealModel;
import com.bank.engine.app.model.base.ResultModel;
import lombok.Data;

import java.util.List;

/**
 * Created by jiangfei on 2020/12/13.
 */
@Data
public class FundRealResultModel extends ResultModel {
    private List<FundRealModel> data;
}
