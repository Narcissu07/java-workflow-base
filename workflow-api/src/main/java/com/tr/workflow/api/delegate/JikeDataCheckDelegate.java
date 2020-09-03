package com.tr.workflow.api.delegate;

import com.tr.biz.api.JikeDataCheckService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Scope("prototype")
public class JikeDataCheckDelegate implements JavaDelegate {
    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private JikeDataCheckService jikeDataCheckService;
    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            jikeDataCheckService.dataCheck(delegateExecution,runtimeService,taskService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
