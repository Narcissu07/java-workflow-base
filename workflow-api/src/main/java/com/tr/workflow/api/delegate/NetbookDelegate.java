package com.tr.workflow.api.delegate;

import com.tr.biz.api.JikeService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by pj on 2018/9/6.
 * 网签上报
 */
@Component
@Scope("prototype")
public class NetbookDelegate implements JavaDelegate {
    private Expression operation;
    @Resource
    private JikeService jikeService;

    @Resource
    RuntimeService runtimeService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public void execute(DelegateExecution execution){
        try {
            logger.info("放款审查通过，开始处理数据上报");
            //String operationStr = jikeService.getOperation(execution.getProcessInstanceId());
            //String operationStr = (String)operation.getValue(execution);
            String operationStr = (String)runtimeService.getVariable(execution.getId(), "operation");
            jikeService.report(execution,operationStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
