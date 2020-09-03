package com.tr.workflow.api.delegate;

import com.tr.biz.api.PayService;
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
public class PayDelegate implements JavaDelegate {
    @Resource
    private PayService payService;

    public void execute(DelegateExecution execution){
        try {
            payService.send(execution);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
