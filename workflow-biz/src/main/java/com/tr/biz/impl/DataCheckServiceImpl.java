package com.tr.biz.impl;

import com.alibaba.fastjson.JSON;
import com.tr.biz.api.DataCheckService;
import com.tr.common.config.BizConfig;
import com.tr.common.framework.api.CommonHttpClient;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataCheckServiceImpl implements DataCheckService {
	@Resource
	private CommonHttpClient commonHttpClient;

	@Resource
	private BizConfig bizConfig;

	public void dataCheck(DelegateExecution execution, RuntimeService runtimeService, TaskService taskService) throws Exception {
		String processInstanceBusinessKey = execution.getProcessInstanceBusinessKey();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderId", processInstanceBusinessKey);
		paramMap.put("operateName", "系统自动");
		paramMap.put("operation", "ACCEPT");
		paramMap.put("taskId", "-1");
		paramMap.put("taskName", "中智诚黑名单");
		paramMap.put("processInstanceId", execution.getProcessInstanceId());
		paramMap.put("comment", "");

		String url = bizConfig.integrationWorkflowUrlRoot + bizConfig.integrationWorkflowUrlApprovalRecordAdd;
		String param = JSON.toJSONString(paramMap);
		commonHttpClient.postBody(url, param);

		paramMap.put("orderId", processInstanceBusinessKey);
		paramMap.put("operateName", "系统自动");
		paramMap.put("operation", "ACCEPT");
		paramMap.put("taskId", "-1");
		paramMap.put("taskName", "获取同盾分");
		paramMap.put("processInstanceId", execution.getProcessInstanceId());
		paramMap.put("comment", String.valueOf(Math.round(Math.random()*50)) + "分");
		param = JSON.toJSONString(paramMap);
		commonHttpClient.postBody(url, param);

		paramMap.put("orderId", processInstanceBusinessKey);
		paramMap.put("operateName", "系统自动");
		paramMap.put("operation", "ACCEPT");
		paramMap.put("taskId", "-1");
		paramMap.put("taskName", "评分卡计算");
		paramMap.put("processInstanceId", execution.getProcessInstanceId());
		paramMap.put("comment", String.valueOf(Math.round(Math.random()*60)*5+500) + "分");
		param = JSON.toJSONString(paramMap);
		commonHttpClient.postBody(url, param);

		runtimeService.setVariable(execution.getId(),"operation","ACCEPT");

	}

	public static void main(String[] args) {
		System.out.println(Math.round(Math.random()*60)*5+500);

	}
}
