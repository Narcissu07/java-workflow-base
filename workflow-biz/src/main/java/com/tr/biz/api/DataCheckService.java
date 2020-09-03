package com.tr.biz.api;


import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;

public interface DataCheckService {
	public void dataCheck(DelegateExecution execution, RuntimeService runtimeService, TaskService taskService) throws Exception;
}