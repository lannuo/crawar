package com.cskj.crawar.entity.opencai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpencaiHistory implements Serializable {

    private String expect;
    private String opencode;
    private String opentime;
}
