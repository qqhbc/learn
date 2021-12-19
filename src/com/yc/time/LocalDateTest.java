package com.yc.time;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class LocalDateTest {
    /**
     * 采用TemporalAdjusters实现复杂的日期操作<br>
     * 预定义方法，比如下一个星期5，当月的最后一天
     */
    @Test
    public void temporalAdjustsTest() {
        LocalDate date = LocalDate.of(2020, 4, 3);
        LocalDate with = date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));// 2020-04-10
        LocalDate lastDayMonth = date.with(TemporalAdjusters.lastDayOfMonth()); // 2020-04-30
        System.out.println(lastDayMonth);
    }

    /**
     * 通过TemporalAdjuster(函数式接口)实现日期后14天的日期
     *
     * @see java.time.temporal.TemporalAdjuster
     */
    @Test
    public void temporalAdjustTest() {
        LocalDate now = LocalDate.now();
        TemporalAdjuster temporalAdjuster = temporal -> temporal.plus(Period.ofDays(14));
        LocalDate with = now.with(temporalAdjuster);// 2020-04-19
        System.out.println(with);
    }

    /**
     * 通过TemporalAdjusters的时间校正器实现某个日期后的工作日情况<br>
     * ofDateAdjuster 采用一元函数式编程 return r -> r
     *
     * @see UnaryOperator identity 一致
     */
    @Test
    public void temporalAdjustWordDay() {
        LocalDate now = LocalDate.now();
        TemporalAdjuster temporalAdjuster = TemporalAdjusters.ofDateAdjuster(localDate -> {
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            int daysToAdd;
            switch (dayOfWeek) {
                case FRIDAY:
                    daysToAdd = 3;
                    break;
                case SATURDAY:
                    daysToAdd = 2;
                    break;
                default:
                    daysToAdd = 1;
            }
            return localDate.plusDays(daysToAdd);
        });
        LocalDate with = now.with(temporalAdjuster);  // 2020-04-06
        System.out.println(with);
    }

    @Test
    public void fun() throws Exception {
        LocalDateTime now = LocalDateTime.of(2020, 4, 5, 23, 22, 11);

        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH));
        System.out.println(now);
        System.out.println(format);
    }
}
