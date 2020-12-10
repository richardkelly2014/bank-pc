package com.bank.engine.app.model.page;

import com.bank.engine.app.model.FundThemeInfoModel;
import com.bank.engine.app.model.base.ResultModel;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class FundThemeInfoResultModel extends ResultModel {
    private List<FundThemeInfoModel> data = Lists.newArrayList();
}
