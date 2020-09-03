package com.tr.biz.api;


import org.activiti.engine.delegate.DelegateExecution;

public interface AccountService {
	public void initMensuo(DelegateExecution execution) throws Exception;
}