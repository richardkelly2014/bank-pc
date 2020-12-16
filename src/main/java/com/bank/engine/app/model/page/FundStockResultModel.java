package com.bank.engine.app.model.page;

import com.bank.engine.app.model.FundStockModel;
import com.bank.engine.app.model.base.ResultModel;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class FundStockResultModel extends ResultModel {

    private List<FundStockModel> data = Lists.newArrayList();
}
