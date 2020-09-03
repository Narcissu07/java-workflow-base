package com.tr.biz.dto;

import com.tr.biz.bo.OrderInfoPO;

import java.util.Map;

/**
 * Created by yangjie on 2018/2/2.
 */
public class CommonOrderInfoDTO {
    private String code;
    private String message;
    private OrderInfoPO data;
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

    public OrderInfoPO getData() {
        return data;
    }

    public void setData(OrderInfoPO data) {
        this.data = data;
    }
}
