package com.tr.biz.impl;

import com.alibaba.fastjson.JSON;
import com.tr.biz.api.AccountService;
import com.tr.biz.api.PayService;
import com.tr.biz.dto.CommonDTO;
import com.tr.common.config.BizConfig;
import com.tr.common.framework.api.CommonHttpClient;
import com.tr.common.helper.DictHelper;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
	@Resource
	private CommonHttpClient commonHttpClient;

	@Resource
	private BizConfig bizConfig;

	public void initMensuo(DelegateExecution execution) throws Exception {
		String processInstanceBusinessKey = execution.getProcessInstanceBusinessKey();

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderId", processInstanceBusinessKey);
		String url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlFindOrderById;
		String param = JSON.toJSONString(paramMap);
		String msg = commonHttpClient.postBody(url, param);
		CommonDTO commonDTO = JSON.parseObject(msg, CommonDTO.class);
		Map<String, String> dataMap = commonDTO.getData();


		String userId = dataMap.get("userId");
		String orderAccount = dataMap.get("orderAccount");
		String riskTerm = dataMap.get("riskTerm");
		String periods = DictHelper.getDictOtherInfoById("TERM_NO", riskTerm);

		//添加测试1
		String name = dataMap.get("name");
		String idNo = dataMap.get("idNo");
		String mobileNo = dataMap.get("mobileNo");
		String bankCardNo = dataMap.get("bankCardNo");
		String bankCardName = dataMap.get("bankCardName");

		String rate = dataMap.get("rate");
		//String actMoneyDay = dataMap.get("actMoneyDay");
		String actMoneyDay = dataMap.get("createTime");
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long lt = new Long(actMoneyDay);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		String tenant = dataMap.get("tenant");
		String childProduct = dataMap.get("childProduct");
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("userId", userId);
		paramMap2.put("orderId", processInstanceBusinessKey);
		paramMap2.put("amount", orderAccount);
		paramMap2.put("periods", periods);
		paramMap2.put("yearRate", rate);
		paramMap2.put("startDate", res);
		paramMap2.put("tenant", tenant);

		paramMap2.put("name", name);
		paramMap2.put("bankCardNo", bankCardNo);
		paramMap2.put("idcardNo", idNo);
		paramMap2.put("bankName", bankCardName);
		paramMap2.put("mobilePhone", mobileNo);
		paramMap2.put("childProduct", childProduct);

		String url2 = "";
		if("mensuo".equals(childProduct)){
			url2 = bizConfig.integrationAccountUrlRoot + bizConfig.integrationAccountUrlAccountInitMensuo;
		}else if("nuantong".equals(childProduct)){
			url2 = bizConfig.integrationAccountUrlRoot+"/account/initNuantong";
		}
		String param2 = JSON.toJSONString(paramMap2);
		commonHttpClient.postBody(url2, param2);
	}
}
