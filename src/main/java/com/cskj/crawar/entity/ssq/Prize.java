package com.cskj.crawar.entity.ssq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prize implements Serializable {
    private Integer id;
    private String code;
    private Integer type;
    @JsonProperty("typenum")
    private String num;
    @JsonProperty("typemoney")
    private String money;
}
