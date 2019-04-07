package com.cskj.crawar.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Prizegrades implements Serializable {
    private Integer id;
    private Integer type;
    private String typemoney;
    private String typenum;
    private String code;
}
