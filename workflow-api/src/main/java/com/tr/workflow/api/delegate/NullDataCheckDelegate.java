package com.tr.workflow.api.delegate;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 空任务，自动跳过的时候使用
 */
@Component
@Scope("prototype")
public class NullDataCheckDelegate implements JavaDelegate {
    @Resource
    RuntimeService runtimeService;
    @Override
    public void execute(DelegateExecution delegateExecution) {
        runtimeService.setVariable(delegateExecution.getProcessInstanceId(),"operation","ACCEPT");
    }
}
