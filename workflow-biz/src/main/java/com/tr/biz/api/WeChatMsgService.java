package com.tr.biz.api;


import com.tr.biz.bo.TaskBO;
import com.tr.biz.request.TaskSearchRequest;
import org.activiti.engine.delegate.DelegateExecution;

public interface WeChatMsgService {
	public void sendMsg(DelegateExecution execution, String operationStr) throws Exception;
}