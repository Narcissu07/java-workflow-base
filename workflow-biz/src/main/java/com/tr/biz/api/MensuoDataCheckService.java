package com.tr.biz.api;


import com.tr.biz.dto.ApprovalResultDTO;
import com.tr.biz.request.MensuoDataCheckRequest;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;

public interface MensuoDataCheckService {
	public void dataCheck(DelegateExecution execution, RuntimeService runtimeService, TaskService taskService) throws Exception;

	/**
	 * 测试校验
	 * @param request
	 * @throws Exception
	 */
	public ApprovalResultDTO testDataCheck(MensuoDataCheckRequest request) throws Exception;
}