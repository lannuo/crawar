package com.cskj.crawar.entity.ssq;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class History {
    private Integer id;
    private Long code;
    private String red;
    private String blue;
    private Long poolmoney;
    private Long salemoney;
    private Date lotteryDate;
    private Date withdrawDate;
    private String week;
    private List<Prize> prizes;

    public History() {
    }

    public History(Long code, String red, String blue, Long poolmoney, Long salemoney, Date lotteryDate, Date withdrawDate, String week) {
        this.code = code;
        this.red = red;
        this.blue = blue;
        this.poolmoney = poolmoney;
        this.salemoney = salemoney;
        this.lotteryDate = lotteryDate;
        this.withdrawDate = withdrawDate;
        this.week = week;
    }
}
