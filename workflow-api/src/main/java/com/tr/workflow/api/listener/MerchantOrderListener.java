package com.tr.workflow.api.listener;

import com.alibaba.fastjson.JSONObject;
import com.tr.common.config.BizConfig;
import com.tr.common.utils.HttpClientUtil;
import com.tr.common.utils.SpringContextUtil;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单审批通过结束事件
 */
public class MerchantOrderListener implements ExecutionListener {
    private Logger logger = LoggerFactory.getLogger(MerchantOrderListener.class);
    @Override
    public void notify(DelegateExecution delegateExecution) {
        BizConfig config = SpringContextUtil.getBean(BizConfig.class);
        String businessKey = delegateExecution.getProcessInstanceBusinessKey();
        logger.info("流程通过结束事件businessKey={}",businessKey);
        if(ExecutionListener.EVENTNAME_END.equals(delegateExecution.getEventName())){
            JSONObject params = new JSONObject();
            params.put("orderNo",businessKey);
            try {
                String result = HttpClientUtil.postBody(config.integrationOrderUrlSearchRoot+"/merchantOrder/joinLoanApprovalPass",params.toJSONString());
                logger.info("流程审批通过，订单处理结果：{}",result);
            }catch (Exception e){
                e.printStackTrace();
                logger.error("流程审批通过，订单处理失败",e);
            }
        }
    }
}
