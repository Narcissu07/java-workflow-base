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
 * 订单审批拒绝结束事件
 */
public class MerchantOrderRejectListener implements ExecutionListener {
    private Logger logger = LoggerFactory.getLogger(MerchantOrderRejectListener.class);
    @Override
    public void notify(DelegateExecution delegateExecution) {
        BizConfig config = SpringContextUtil.getBean(BizConfig.class);
        if(ExecutionListener.EVENTNAME_END.equals(delegateExecution.getEventName())){
            //修改订单状态为“取消”
            JSONObject params = new JSONObject();
            params.put("orderNo",delegateExecution.getProcessInstanceBusinessKey());
            params.put("orderState",600);
            try {
                String result = HttpClientUtil.postBody(config.integrationOrderUrlSearchRoot+"/merchantOrder/changeState",params.toJSONString());
                logger.info("流程审批拒绝，修改订单状态结果：{}",result);
            }catch (Exception e){
                e.printStackTrace();
                logger.error("流程审批拒绝，修改订单状态失败",e);
            }
        }
    }
}
