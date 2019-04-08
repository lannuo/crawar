package com.cskj.crawar.entity.ssq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class History implements Serializable {

    private Integer id;

    private String code;//开奖期号

    private String red;//红球

    @JsonProperty("blue")
    private String blue;//蓝球

    private String poolmoney;//奖池金额

    private String salemoney;//销售金额

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lotteryDate;//开奖日期

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date withdrawDate;//兑换截止日期

    private String week;//星期几

    private String content;//各地中奖注数

    private List<Prize> prizes;//奖金和比例

    private String date;

    private List<String> reds;//红球数组

    public History() {
    }

    public History(String code, String red, String blue, String poolmoney, String salemoney, Date lotteryDate, Date withdrawDate, String week) {
        this.code = code;
        this.red = red;
        this.blue = blue;
        this.poolmoney = poolmoney;
        this.salemoney = salemoney;
        this.lotteryDate = lotteryDate;
        this.withdrawDate = withdrawDate;
        this.week = week;
    }
    @JsonProperty("salemoney")
    public String getSalemoney() {
        return salemoney;
    }
    @JsonProperty("sales")
    public void setSalemoney(String salemoney) {
        this.salemoney = salemoney;
    }
    @JsonProperty("prizes")
    public List<Prize> getPrizes() {
        return prizes;
    }

    @JsonProperty("prizegrades")
    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }

    @JsonIgnore
    public String getDate() {
        return date;
    }
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }
}
