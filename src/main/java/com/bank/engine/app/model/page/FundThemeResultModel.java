package com.bank.engine.app.model.page;

import com.bank.engine.app.model.FundThemeModel;
import com.bank.engine.app.model.base.ResultModel;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Created by jiangfei on 2020/12/9.
 */
@Data
public class FundThemeResultModel extends ResultModel {
    private List<FundThemeModel> data = Lists.newArrayList();
}
