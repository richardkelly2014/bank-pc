package com.bank.engine.app.model.page;

import com.bank.engine.app.model.StockRankModel;
import com.bank.engine.app.model.base.ResultPageModel;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class StockRankPageModel extends ResultPageModel {

    private List<StockRankModel> list = Lists.newArrayList();
}
