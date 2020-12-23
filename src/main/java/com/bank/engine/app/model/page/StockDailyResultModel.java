package com.bank.engine.app.model.page;

import com.bank.engine.app.model.StockDailyModel;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class StockDailyResultModel {
    
    private List<StockDailyModel> data = Lists.newArrayList();
}
