package com.cskj.crawar.entity.opencai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpencaiResult implements Serializable {

    private List<OpencaiHistory> data;

}
