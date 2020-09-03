package com.tr.biz.dto;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/26.
 */
public class ZhongzhichengBlacklistDTO {
    private String code;
    private String message;
    private ZhongzhichengBlacklistRO data;

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

    public ZhongzhichengBlacklistRO getData() {
        return data;
    }

    public void setData(ZhongzhichengBlacklistRO data) {
        this.data = data;
    }
}
