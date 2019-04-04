package com.cskj.crawar.entity.ssq;

import lombok.Data;

import java.util.Date;
@Data
public class Task {
    private Integer id;
    private Long code;
    private String url;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public Task(Long code, String url, Integer status) {
        this.code = code;
        this.url = url;
        this.status = status;
    }
}
