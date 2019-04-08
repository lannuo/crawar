package com.cskj.crawar.entity.common;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class OperInfo implements Serializable {
    private int code;
    private String msg;
    private Map<String, Object> data = new HashMap<>();
    public OperInfo() {
        this.code = 200;
        this.msg = "success";
    }

    public OperInfo(@NonNull Map<String, Object> data) {
        this();
        this.data = data;
    }

    public OperInfo(@NonNull int code, @NonNull String msg) {
        this.code = code;
        this.msg = msg;
    }

    public OperInfo(@NonNull String key, @NonNull Object value) {
        this();
        this.data = new HashMap() {{
            put(key, value);
        }};
    }
}
