package com.cskj.crawar.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryResult implements Serializable {
    private List<DrawHistory> result;
}
