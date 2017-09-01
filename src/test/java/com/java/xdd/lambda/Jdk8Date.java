package com.java.xdd.lambda;

import org.junit.Test;

import java.time.*;
import java.util.Date;

/**
 * Created by qw on 2017/9/1.
 */
public class Jdk8Date {

    //操作时间  LocalDateTime LocalTime LocalDate
    @Test
    public void test() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDateTime of = LocalDateTime.of(2016, 10, 10, 10, 10, 10, 111000000);
        System.out.println(of);

    }

    //时间戳   Instant
    //计算两个时间间隔  Duration
    //计算两个日期间隔  Period
    @Test
    public void test2() throws Exception{
        Instant instant = Instant.now();
        Thread.sleep(1000);
        Instant now = Instant.now();
        Duration between = Duration.between(instant, now);
        System.out.println(between.toMillis());
        System.out.println(between.toHours());


        LocalDate localDate = LocalDate.now();
        LocalDate of = LocalDate.of(2016, 6, 10);
        Period between1 = Period.between(of, localDate);
        System.out.println(between1.getYears());
        System.out.println(between1.getMonths());
    }

    //Date LocalDateTime LocalTime LocalDate 转换
    @Test
    public void test3() {
        Date date = new Date();
        Instant instant = date.toInstant();


    }

}
