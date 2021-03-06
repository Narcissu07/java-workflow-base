package com.tr.biz.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2018/2/2.
 */
public class CommodityOrderDTO {
    private String code;
    private String message;
    List<Map<String, String>> data;
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

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }
}
