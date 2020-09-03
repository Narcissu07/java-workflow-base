package com.tr.biz.dto;

/**
 * Created by Administrator on 2018/6/26.
 */
public class FahaiPersonDTO {
    private String code;
    private String message;
    private FahaiPersonRO data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FahaiPersonRO getData() {
        return data;
    }

    public void setData(FahaiPersonRO data) {
        this.data = data;
    }
}
