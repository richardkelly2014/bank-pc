package com.bank.engine.app.model.page;

import com.bank.engine.app.model.FundRateModel;
import com.bank.engine.app.model.base.ResultPageModel;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Created by jiangfei on 2020/12/12.
 */
@Data
public class FundRatePageModel extends ResultPageModel {
    private List<FundRateModel> list = Lists.newArrayList();
}
