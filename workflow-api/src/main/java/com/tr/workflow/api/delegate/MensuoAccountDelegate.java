package com.tr.workflow.api.delegate;

import com.tr.biz.api.AccountService;
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
public class MensuoAccountDelegate implements JavaDelegate {
    @Resource
    private AccountService accountService;

    public void execute(DelegateExecution execution){
        try {
            System.out.println("开始门锁初始化账务");
            accountService.initMensuo(execution);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
