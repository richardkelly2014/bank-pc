package com.bank.engine.app.util;

import cn.hutool.core.date.Quarter;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

@UtilityClass
public class DateTimeUtil {

    /**
     * 获取 quarter
     *
     * @return
     */
    public static int getQuarter() {
        LocalDate now = LocalDate.now();
        return now.query(new Quarter());
    }

    /**
     * 获取 year
     *
     * @return
     */
    public static int getYear() {
        LocalDate now = LocalDate.now();
        return now.getYear();
    }


    public static class Quarter implements TemporalQuery<Integer> {

        @Override
        public Integer queryFrom(TemporalAccessor temporal) {

            LocalDate now = LocalDate.from(temporal);
            LocalDate l1 = now.with(Month.APRIL).withDayOfMonth(1);
            LocalDate l2 = now.with(Month.JULY).withDayOfMonth(1);
            LocalDate l3 = now.with(Month.OCTOBER).withDayOfMonth(1);

            if (now.isBefore(l1)) {
                return 1;
            } else if (now.isBefore(l2)) {
                return 2;
            } else if (now.isBefore(l3)) {
                return 3;
            } else {
                return 4;
            }
        }
    }
}
