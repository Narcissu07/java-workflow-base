package com.tr.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tr.biz.api.OutSystemService;
import com.tr.biz.dto.MerchantOrderDTO;
import com.tr.biz.dto.OperatorDTO;
import com.tr.common.config.BizConfig;
import com.tr.common.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OutSystemServiceImpl implements OutSystemService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private BizConfig bizConfig;
    @Override
    public OperatorDTO getOperatorByUserName(String userName){
        logger.info("查询boss用户参数：{}",userName);
        JSONObject params = new JSONObject();
        params.put("userName",userName);
        try {
            String result = HttpClientUtil.postBody(bizConfig.permissionUrlRoot+"/operator/getByUserName",params.toJSONString());
            logger.info("查询boss用户信息返回：{}",result);
            JSONObject object = JSON.parseObject(result);
            if(object.getString("code").equals("0") && object.getJSONObject("data")!=null){
                OperatorDTO dto = JSON.parseObject(object.getJSONObject("data").toJSONString(),OperatorDTO.class);
                return dto;
            }
        }catch (Exception e){
            logger.error("查询boss用户信息失败",e);
        }
        return null;
    }

    @Override
    public MerchantOrderDTO getMerchantOrderByOrderNo(String orderNo) {
        logger.info("查订单参数：{}",orderNo);
        JSONObject params = new JSONObject();
        params.put("orderNo",orderNo);
        try{
            String result = HttpClientUtil.postBody(bizConfig.integrationOrderUrlSearchRoot+"/merchantOrder/getOrderById", params.toJSONString());
            logger.info("查询订单详情结果：{}",result);
            JSONObject object = JSON.parseObject(result);
            if("200".equals(object.getString("code"))){
                MerchantOrderDTO orderDTO = JSON.parseObject(object.getJSONObject("data").toJSONString(),MerchantOrderDTO.class);
                return orderDTO;
            }
        }catch (Exception e){
            logger.error("获取订单详情失败",e);
        }
        return null;
    }

    @Override
    public void sendEmail(String to, String title, String content) {
        JSONObject params = new JSONObject();
        params.put("to",to);
        params.put("title",title);
        params.put("content",content);
        logger.info("发送邮件参数：{}",params.toJSONString());
        try{
            String result = HttpClientUtil.postBody(bizConfig.integrationDataresourceUrlRoot+"/email/send", params.toJSONString());
            logger.info("发送邮件结果：{}",result);
        }catch (Exception e){
            logger.error("发送邮件失败",e);
        }
    }
}
