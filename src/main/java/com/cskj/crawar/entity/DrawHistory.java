package com.cskj.crawar.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrawHistory implements Serializable {
    private Integer id;
    private String adddmoney;
    private String addmoney2;
    @JsonProperty("blue")
    private String blue;
    private String blue2;
    private String code;
    private String content;
    private String date;
    private String datailsLink;
    private String name;
    private String poolmoney;
    private String red;
    private String sales;
    private String videoLink;
    private String week;
    private List<Prizegrades> prizegrades;

}
