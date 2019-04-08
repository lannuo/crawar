package com.cskj.crawar.entity.ssq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prize implements Serializable {
    private Integer id;
    private String code;//期号
    private Integer type;//中奖名次
    private String num;//中奖注数
    private String money;//中奖金额

    @JsonProperty("num")
    public String getNum() {
        return num;
    }

    @JsonProperty("typenum")
    public void setNum(String num) {
        this.num = num;
    }

    @JsonProperty("money")
    public String getMoney() {
        return money;
    }

    @JsonProperty("typemoney")
    public void setMoney(String money) {
        this.money = money;
    }
}
