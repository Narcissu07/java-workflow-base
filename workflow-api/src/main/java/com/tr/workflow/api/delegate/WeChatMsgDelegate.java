package com.tr.workflow.api.delegate;

import com.tr.biz.api.CustomTaskService;
import com.tr.biz.api.WeChatMsgService;
import com.tr.common.utils.DateUtil;
import com.tr.common.utils.HttpClientUtil;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by yangjie on 2018/3/9.
 */
@Component
@Scope("prototype")
public class WeChatMsgDelegate implements JavaDelegate {
    private Expression operation;
    @Resource
    private WeChatMsgService weChatMsgService;

    public void execute(DelegateExecution execution){
        try {
            String operationStr = (String)operation.getValue(execution);
            weChatMsgService.sendMsg(execution, operationStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
