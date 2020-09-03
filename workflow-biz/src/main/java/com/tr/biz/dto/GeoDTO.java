package com.tr.biz.dto;

/**
 * Created by Administrator on 2018/6/26.
 */
public class GeoDTO {
    private String code;
    private String message;
    private GeoRO data;

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

    public GeoRO getData() {
        return data;
    }

    public void setData(GeoRO data) {
        this.data = data;
    }
}
