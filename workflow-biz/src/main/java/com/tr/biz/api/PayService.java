package com.tr.biz.api;


import org.activiti.engine.delegate.DelegateExecution;

public interface PayService {
	public void send(DelegateExecution execution) throws Exception;
}