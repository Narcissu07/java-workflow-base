package com.tr.biz.impl;

import com.alibaba.fastjson.JSON;
import com.tr.biz.api.WeChatMsgService;
import com.tr.biz.dto.CommonDTO;
import com.tr.common.config.BizConfig;
import com.tr.common.framework.api.CommonHttpClient;
import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeChatMsgServiceImpl implements WeChatMsgService {
	@Resource
	private CommonHttpClient commonHttpClient;

	@Resource
	private BizConfig bizConfig;

	public void sendMsg(DelegateExecution execution, String operationStr) throws Exception {

//		String processInstanceBusinessKey = execution.getProcessInstanceBusinessKey();
//		if(StringUtils.isNotEmpty(operationStr)){
//			Date time = new Date();
//			String timeStr = DateUtil.convert2String(time, DateUtil.DATETIME_FORMAT_2);
//
//			Map<String, String> paramMap = new HashMap<String, String>();
//			paramMap.put("orderId", processInstanceBusinessKey);
//			String url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlFindOrderById;
//			String param = JSON.toJSONString(paramMap);
//			String msg = commonHttpClient.postBody(url, param);
//			CommonDTO commonDTO = JSON.parseObject(msg, CommonDTO.class);
//			Map<String, String> dataMap = commonDTO.getData();
//			String account = dataMap.get("account");
//			String termNo = dataMap.get("termNo");
//			String riskTerm = dataMap.get("riskTerm");
//			String userId = dataMap.get("userId");
//			String riskAccount = dataMap.get("riskAccount");
//			String rate = dataMap.get("rate");
//			rate = BigDecimal.valueOf(Double.valueOf(rate)).multiply(new BigDecimal(100)).toString() + "%";
//			String repayment = dataMap.get("repayment");
//
//
//			BigDecimal accountBD = BigDecimal.valueOf(Long.valueOf(account));
//			String accountStr = accountBD.divide(new BigDecimal(100)).toString() + "元";
//			BigDecimal riskAccountBD = BigDecimal.valueOf(Long.valueOf(riskAccount));
//			String riskAccountStr = riskAccountBD.divide(new BigDecimal(100)).toString() + "元";
//
//			Map<String, String> paramMap2 = new HashMap<String, String>();
//			paramMap2.put("userId", userId);
//			String url2 = bizConfig.integrationUsercenterUrlRoot + bizConfig.integrationUsercenterUrlUserFindUser;
//			String param2 = JSON.toJSONString(paramMap2);
//			String msg2 = commonHttpClient.postBody(url2, param2);
//			CommonDTO commonDTO2 = JSON.parseObject(msg2, CommonDTO.class);
//			Map<String, String> dataMap2 = commonDTO2.getData();
//			String openId = dataMap2.get("openId");
//
//
//			if(operationStr.equals("ACCEPT")){
//				Map<String, String> paramMap3 = new HashMap<String, String>();
//				paramMap3.put("openid", openId);
//				paramMap3.put("first", "您的贷款申请已通过审批。");
//				paramMap3.put("keyword1", riskAccountStr);
//				paramMap3.put("keyword2", DictHelper.getDictDescById("TERM_NO", riskTerm));
//				paramMap3.put("keyword3", rate);
//				paramMap3.put("keyword4", DictHelper.getDictDescById("REPAYMENT_METHOD", repayment));
//				paramMap3.put("remark", "感谢你的使用。");
//				String url3 = bizConfig.integrationWeixinUrlRoot + bizConfig.integrationWeixinUrlPushOrderApproved;
//				String param3 = JSON.toJSONString(paramMap3);
//				commonHttpClient.postBody(url3, param3);
//			}else if(operationStr.equals("REJECT")){
//				Map<String, String> paramMap3 = new HashMap<String, String>();
//				paramMap3.put("openid", openId);
//				paramMap3.put("first", "审批拒绝：您的贷款申请因“综合评分不足”被拒绝。");
//				paramMap3.put("keyword1", accountStr);
//				paramMap3.put("keyword2", DictHelper.getDictDescById("TERM_NO", termNo));
//				paramMap3.put("keyword3", timeStr);
//				paramMap3.put("remark", "感谢你的使用。");
//				String url3 = bizConfig.integrationWeixinUrlRoot + bizConfig.integrationWeixinUrlPushOrderDeny;
//				String param3 = JSON.toJSONString(paramMap3);
//				commonHttpClient.postBody(url3, param3);
//			}
//		}




		String processInstanceBusinessKey = execution.getProcessInstanceBusinessKey();
		if(StringUtils.isNotEmpty(operationStr)){
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderId", processInstanceBusinessKey);
			String url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlFindOrderById;
			String param = JSON.toJSONString(paramMap);
			String msg = commonHttpClient.postBody(url, param);
			CommonDTO commonDTO = JSON.parseObject(msg, CommonDTO.class);
			Map<String, String> dataMap = commonDTO.getData();
			String mobileNo = dataMap.get("mobileNo");
			String name = dataMap.get("name");
			String text = "尊敬的" + name + "，您好！经综合审批，您的申请已通过，感谢您的支持！";

			if(operationStr.equals("ACCEPT")){
				Map<String, String> paramMap2 = new HashMap<String, String>();
				paramMap2.put("apikey", "34167068917a1964b9236d38367d1c3f");
				paramMap2.put("text", text);
				paramMap2.put("mobile", mobileNo);
				String url2 = "https://sms.yunpian.com/v2/sms/single_send.json";
				commonHttpClient.post(url2, paramMap2);
			}
		}


	}


//	public static void main(String[] args) throws Exception {
//		Map<String, String> paramMap2 = new HashMap<String, String>();
//		paramMap2.put("apikey", "34167068917a1964b9236d38367d1c3f");
//		paramMap2.put("text", "尊敬的杨捷，您好！经综合审批，您的申请已通过，感谢您的支持！");
//		paramMap2.put("mobile", "18523380869");
//		String url2 = "https://sms.yunpian.com/v2/sms/single_send.json";
//		HttpClientUtil.post(url2, paramMap2);
//	}
}
