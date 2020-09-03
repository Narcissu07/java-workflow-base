package com.tr.workflow.api.listener;

import com.sun.source.util.TaskEvent;
import com.tr.biz.api.OutSystemService;
import com.tr.biz.dto.MerchantOrderDTO;
import com.tr.biz.dto.OperatorDTO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Scope("prototype")
public class SendEmailTaskListener implements TaskListener {
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private OutSystemService outSystemService;
    @Override
    public void notify(DelegateTask delegateTask) {
        String userName = delegateTask.getAssignee();
        OperatorDTO operatorDTO = outSystemService.getOperatorByUserName(userName);
        if(operatorDTO!=null && StringUtils.isNotBlank(operatorDTO.getEmail())){
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
            String orderNo;
            if(processInstance!=null){
                orderNo = processInstance.getBusinessKey();
            }else{
                orderNo = (String) runtimeService.getVariable(delegateTask.getExecutionId(), "orderNo");
            }
            MerchantOrderDTO orderDTO = outSystemService.getMerchantOrderByOrderNo(orderNo);
            if(orderDTO!=null){
                String merchantName = orderDTO.getMerchantInfo().get("merchantName").toString();
                String loanName = orderDTO.getUserBasicInfo().get("name").toString();
                BigDecimal amount = new BigDecimal(orderDTO.getOrder().get("orderAmount").toString()).divide(BigDecimal.valueOf(100L),2, RoundingMode.HALF_UP);
                //发送邮件提醒
                String title = "审批提醒";
                String content = "【"+operatorDTO.getName()+"】：你好，请前去审核【"+merchantName+"】的【"+loanName+"】的【"+amount+"】的订单";
                String to = operatorDTO.getEmail();
                outSystemService.sendEmail(to,title,content);
            }
        }

    }
}
