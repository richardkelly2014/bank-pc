package com.bank.engine.app.model.page;

import com.bank.engine.app.model.FundRankModel;
import com.bank.engine.app.model.base.ResultPageModel;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class FundRankPageModel extends ResultPageModel {

    private List<FundRankModel> list = Lists.newArrayList();
}
