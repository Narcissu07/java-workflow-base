package com.tr.workflow.api.delegate;

import com.tr.biz.api.JikeService;
import com.tr.biz.api.PayService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by pj on 2018/9/6.
 * cs1
 */
@Component
@Scope("prototype")
public class JikeDelegate implements JavaDelegate {
    private Expression operation;
    @Resource
    private JikeService jikeService;

    @Resource
    RuntimeService runtimeService;

    public void execute(DelegateExecution execution){
        try {
            //String operationStr = jikeService.getOperation(execution.getProcessInstanceId());
            //String operationStr = (String)operation.getValue(execution);
            String operationStr = (String)runtimeService.getVariable(execution.getId(), "operation");
            jikeService.send(execution,operationStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
