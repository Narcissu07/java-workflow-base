package com.tr.biz.impl;

import com.alibaba.fastjson.JSON;
import com.tr.biz.api.PayService;
import com.tr.biz.dto.CommonDTO;
import com.tr.common.config.BizConfig;
import com.tr.common.framework.api.CommonHttpClient;
import com.tr.common.helper.DictHelper;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PayServiceImpl implements PayService {
	@Resource
	private CommonHttpClient commonHttpClient;

	@Resource
	private BizConfig bizConfig;

	public void send(DelegateExecution execution) throws Exception {
		String processInstanceBusinessKey = execution.getProcessInstanceBusinessKey();

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderId", processInstanceBusinessKey);
		String url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlFindOrderById;
		String param = JSON.toJSONString(paramMap);
		String msg = commonHttpClient.postBody(url, param);
		CommonDTO commonDTO = JSON.parseObject(msg, CommonDTO.class);
		Map<String, String> dataMap = commonDTO.getData();

		String orderAccount = dataMap.get("orderAccount");
		String name = dataMap.get("name");
		String idNo = dataMap.get("idNo");
		String bankCardNo = dataMap.get("bankCardNo");
		String bankCardName = dataMap.get("bankCardName");
		String field1 = dataMap.get("field1");


		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("requestNo", UUID.randomUUID().toString().replace("-", ""));
		paramMap2.put("invokeSystem","1001");
		paramMap2.put("bankCardNo",bankCardNo);
		paramMap2.put("amount",orderAccount);
		paramMap2.put("idCardNo",idNo);
		paramMap2.put("name",name);
		paramMap2.put("provinceCity","重庆");
		paramMap2.put("bankName",bankCardName);
		paramMap2.put("kaihuhang",field1);
		paramMap2.put("orderId",processInstanceBusinessKey);
		paramMap2.put("userId",dataMap.get("userId"));
		String url2 = bizConfig.integrationPaymentUrlRoot + bizConfig.integrationPaymentUrlPaymentPaySend;
		String param2 = JSON.toJSONString(paramMap2);
		String msg2 = commonHttpClient.postBody(url2, param2);
		System.out.println(msg2);
//
//		String userId = dataMap.get("userId");
//		String orderAccount = dataMap.get("orderAccount");
//		String riskTerm = dataMap.get("riskTerm");
//		String periods = DictHelper.getDictOtherInfoById("TERM_NO", riskTerm);
//
//		String name = dataMap.get("name");
//		String idNo = dataMap.get("idNo");
//		String mobileNo = dataMap.get("mobileNo");
//		String bankCardNo = dataMap.get("bankCardNo");
//		String bankCardName = dataMap.get("bankCardName");
//
//
//		String rate = dataMap.get("rate");
//		String actMoneyDay = dataMap.get("actMoneyDay");
//		String tenant = dataMap.get("tenant");
//		Map<String, String> paramMap2 = new HashMap<String, String>();
//		paramMap2.put("userId", userId);
//		paramMap2.put("orderId", processInstanceBusinessKey);
//		paramMap2.put("amount", orderAccount);
//		paramMap2.put("periods", periods);
//		paramMap2.put("yearRate", rate);
//		paramMap2.put("startDate", actMoneyDay);
//		paramMap2.put("tenant", tenant);
//
//		paramMap2.put("name", name);
//		paramMap2.put("bankCardNo", bankCardNo);
//		paramMap2.put("idcardNo", idNo);
//		paramMap2.put("bankName", bankCardName);
//		paramMap2.put("mobilePhone", mobileNo);

//		String url2 = bizConfig.integrationAccountUrlRoot + bizConfig.integrationAccountUrlAccountInit;
//		String param2 = JSON.toJSONString(paramMap2);
//		commonHttpClient.postBody(url2, param2);
	}
}
