package com.tr.biz.api;

import com.tr.biz.dto.MerchantOrderDTO;
import com.tr.biz.dto.OperatorDTO;

public interface OutSystemService {
    OperatorDTO getOperatorByUserName(String userName);
    MerchantOrderDTO getMerchantOrderByOrderNo(String orderNo);

    /**
     * 发邮件
     * @param to
     * @param title
     * @param content
     */
    void sendEmail(String to,String title,String content);
}
