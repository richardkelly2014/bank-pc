package com.bank.engine.app.util;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Dictionary {

    public final static List<FundTypeModel> fundType = Lists.newArrayList();
    public final static List<FundTypeModel> fundInvestType = Lists.newArrayList();

    public final static List<FundTypeModel> weekType = Lists.newArrayList();
    public final static List<FundTypeModel> monthType = Lists.newArrayList();

    static {
        fundType.add(FundTypeModel.builder().id(1).name("保本型").build());
        fundType.add(FundTypeModel.builder().id(2).name("债券型").build());
        fundType.add(FundTypeModel.builder().id(3).name("基金型").build());
        fundType.add(FundTypeModel.builder().id(4).name("封闭式基金").build());
        fundType.add(FundTypeModel.builder().id(5).name("混合型").build());
        fundType.add(FundTypeModel.builder().id(6).name("短期理财").build());
        fundType.add(FundTypeModel.builder().id(7).name("股票型").build());
        fundType.add(FundTypeModel.builder().id(8).name("货币型").build());
        fundType.add(FundTypeModel.builder().id(9).name("黄金型").build());


        fundInvestType.add(FundTypeModel.builder().id(1).name("保本型").build());
        fundInvestType.add(FundTypeModel.builder().id(2).name("债券型").build());
        fundInvestType.add(FundTypeModel.builder().id(3).name("基金型").build());
        fundInvestType.add(FundTypeModel.builder().id(4).name("指数型").build());
        fundInvestType.add(FundTypeModel.builder().id(5).name("混合型").build());
        fundInvestType.add(FundTypeModel.builder().id(6).name("股票型").build());
        fundInvestType.add(FundTypeModel.builder().id(7).name("货币型").build());
        fundInvestType.add(FundTypeModel.builder().id(8).name("黄金型").build());


        weekType.add(FundTypeModel.builder().id(1).name("星期一").build());
        weekType.add(FundTypeModel.builder().id(2).name("星期二").build());
        weekType.add(FundTypeModel.builder().id(3).name("星期三").build());
        weekType.add(FundTypeModel.builder().id(4).name("星期四").build());
        weekType.add(FundTypeModel.builder().id(5).name("星期五").build());

        monthType.add(FundTypeModel.builder().id(1).name("01").build());
        monthType.add(FundTypeModel.builder().id(2).name("02").build());
        monthType.add(FundTypeModel.builder().id(3).name("03").build());
        monthType.add(FundTypeModel.builder().id(4).name("04").build());
        monthType.add(FundTypeModel.builder().id(5).name("05").build());
        monthType.add(FundTypeModel.builder().id(6).name("06").build());
        monthType.add(FundTypeModel.builder().id(7).name("07").build());
        monthType.add(FundTypeModel.builder().id(8).name("08").build());
        monthType.add(FundTypeModel.builder().id(9).name("09").build());
        monthType.add(FundTypeModel.builder().id(10).name("10").build());
        monthType.add(FundTypeModel.builder().id(11).name("11").build());
        monthType.add(FundTypeModel.builder().id(12).name("12").build());
        monthType.add(FundTypeModel.builder().id(13).name("13").build());
        monthType.add(FundTypeModel.builder().id(14).name("14").build());
        monthType.add(FundTypeModel.builder().id(15).name("15").build());
        monthType.add(FundTypeModel.builder().id(16).name("16").build());
        monthType.add(FundTypeModel.builder().id(17).name("17").build());
        monthType.add(FundTypeModel.builder().id(18).name("18").build());
        monthType.add(FundTypeModel.builder().id(19).name("19").build());
        monthType.add(FundTypeModel.builder().id(20).name("20").build());
        monthType.add(FundTypeModel.builder().id(21).name("21").build());
        monthType.add(FundTypeModel.builder().id(22).name("22").build());
        monthType.add(FundTypeModel.builder().id(23).name("23").build());
        monthType.add(FundTypeModel.builder().id(24).name("24").build());
        monthType.add(FundTypeModel.builder().id(25).name("25").build());
        monthType.add(FundTypeModel.builder().id(26).name("26").build());
        monthType.add(FundTypeModel.builder().id(27).name("27").build());
        monthType.add(FundTypeModel.builder().id(28).name("28").build());
    }
}
