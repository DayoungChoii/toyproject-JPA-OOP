package com.dayoung.ginseng.message.dto;

import lombok.Data;

@Data
public class Result<T> {
    private T data;
    private String resultCode;
}
