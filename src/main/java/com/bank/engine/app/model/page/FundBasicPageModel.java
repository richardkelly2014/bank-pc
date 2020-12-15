package com.bank.engine.app.model.page;

import com.bank.engine.app.model.FundBasicModel;
import com.bank.engine.app.model.base.ResultPageModel;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Created by jiangfei on 2020/12/8.
 */
@Data
public class FundBasicPageModel extends ResultPageModel {
    private List<FundBasicModel> list = Lists.newArrayList();
}
