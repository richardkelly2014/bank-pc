package com.bank.engine.app.model.base;

import lombok.Data;

@Data
public class ResultPageModel {
    private Integer totalRecords = 0;
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private Integer totalPage = 0;
}
