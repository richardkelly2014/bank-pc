package com.bank.engine.app.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FundTypeModel {

    private Integer id;
    private String name;
}
