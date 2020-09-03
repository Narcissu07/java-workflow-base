package com.tr.workflow.api.delegate;

import com.tr.biz.api.DataCheckService;
import com.tr.biz.api.MensuoDataCheckService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by yangjie on 2018/3/9.
 */
@Component
@Scope("prototype")
public class MensuoDataCheckDelegate implements JavaDelegate {
    @Resource
    TaskService taskService;

    @Resource
    RuntimeService runtimeService;

    @Resource
    private MensuoDataCheckService mensuoDataCheckService;

    public void execute(DelegateExecution execution){
        try {
            mensuoDataCheckService.dataCheck(execution, runtimeService, taskService);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
