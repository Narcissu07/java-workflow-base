package com.tr.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import com.tr.biz.api.JikeService;
import com.tr.biz.bo.OrderInfoPO;
import com.tr.biz.dto.CommonDTO;
import com.tr.biz.dto.CommonOrderInfoDTO;
import com.tr.common.config.BizConfig;
import com.tr.common.framework.api.CommonHttpClient;
import com.tr.common.utils.DateUtil;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pj on 2018/9/6.
 * cs
 */
@Service
public class JikieServiceImpl implements JikeService {
    @Resource
    private CommonHttpClient commonHttpClient;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private BizConfig bizConfig;

    @Override
    public String getOperation(String instanceId) throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();

        String url = bizConfig.integrationWorkflowUrlRoot +"/callback/audit";
        String msg = commonHttpClient.get(url, paramMap);

        return "";
    }

    public void send(DelegateExecution execution, String operationStr) throws Exception {
        String processInstanceBusinessKey = execution.getProcessInstanceBusinessKey();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("orderId", processInstanceBusinessKey);

        if(operationStr.equals("ACCEPT")){
            paramMap.put("resultCode", "1");
            paramMap.put("message","审批通过");
        }else if(operationStr.equals("REJECT")){
            paramMap.put("resultCode", "2");
            paramMap.put("message","审批拒绝");
        }else if(operationStr.equals("CLOSE")){
            paramMap.put("resultCode", "3");
            paramMap.put("message","关闭");

        }

        Thread thread1 = new Thread(){
            public void run() {
                String url = bizConfig.integrationInstitutionUrlRoot +"/callback/audit";
                String param = JSON.toJSONString(paramMap);
                String msg = null;
                try {
                    msg = commonHttpClient.postBody(url, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(msg);

            }
        };
        thread1.start();

    }

    @Override
    public void report(DelegateExecution execution, String operationStr) throws Exception {

        String processInstanceBusinessKey = execution.getProcessInstanceBusinessKey();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("orderId", processInstanceBusinessKey);

        String url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlFindOrderById;
        String param = JSON.toJSONString(paramMap);
        String msg = commonHttpClient.postBody(url, param);
        CommonOrderInfoDTO commonDTO = JSON.parseObject(msg, CommonOrderInfoDTO.class);
        OrderInfoPO orderInfoPO = commonDTO.getData();


        String contractNo = orderInfoPO.getContractNo();
        String customerName = orderInfoPO.getName();
        String certificateNo = orderInfoPO.getIdNo();//身份证号

        Double contractAmount =  BigDecimal.valueOf(Double.parseDouble(orderInfoPO.getAccount())).divide(new BigDecimal(100)).doubleValue();

        //String intRate = ""+Double.parseDouble(orderInfoPO.getRate())/12*1000;
        BigDecimal intRate =new BigDecimal(orderInfoPO.getRate()).multiply(new BigDecimal("1000")).divide(new BigDecimal("12"));

        String contractSignDate = DateUtil.convert2String(new Date(), DateUtil.DATE_FORMAT_1);
        //String issueDate =
        paramMap.put("contractNo",contractNo);
        paramMap.put("customerName",customerName);
        paramMap.put("certificateNo",certificateNo);
        paramMap.put("contractAmount",contractAmount+"");
        paramMap.put("intRate",intRate.toString());
        paramMap.put("contractSignDate",contractSignDate);
        String issueDate = DateUtil.convert2String(new Date(),DateUtil.DATE_FORMAT_1);
        paramMap.put("issueDate",issueDate);
        logger.info("请求参数："+paramMap);
        logger.info("url:"+url);
        String url1 = bizConfig.integrationReportUrlRoot+"/netsign/dataToWareHouse";
        String paramJson = JSONObject.toJSONString(paramMap);
        String response = commonHttpClient.postBody(url1,paramJson);

    }



}
