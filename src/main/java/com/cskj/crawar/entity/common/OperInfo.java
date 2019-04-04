package com.cskj.crawar.entity.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class OperInfo implements Serializable {
    private boolean ok = true;// 响应成功
    private int code = RespCode.SUCCESS;// 响应状态码
    private String msg = "操作成功";// 响应提示信息
    private Object vo;// 响应数据
}
