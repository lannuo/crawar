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
    private String code;
    private String red;
    @JsonProperty("blue")
    private String blue;
    private String poolmoney;
    private String sales;
    private String date;
    private String week;


    private String adddmoney;
    private String addmoney2;

    private String blue2;

    private String content;

    private String datailsLink;
    private String name;



    private String videoLink;

    private List<Prizegrades> prizegrades;

}
