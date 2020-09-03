package com.tr.biz.api;

import org.activiti.engine.delegate.DelegateExecution;

/**
 * Created by pj on 2018/9/6.
 */

public interface JikeService {
    public String getOperation(String instanceId) throws Exception;

    public void send(DelegateExecution execution, String operationStr) throws Exception;

    public void report(DelegateExecution execution, String operationStr) throws Exception;
}
