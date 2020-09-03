package com.tr.dal.mybatis.uo;

import com.tr.dal.mybatis.model.TaskPO;

import java.util.Date;
import java.util.Map;

/**
 * Created by yangjie on 2018/2/2.
 */
public class TaskUO {
    private Map<String, String> taskInfo;
    private Map<String, String> orderInfo;
    public Map<String, String> getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(Map<String, String> taskInfo) {
        this.taskInfo = taskInfo;
    }

    public Map<String, String> getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Map<String, String> orderInfo) {
        this.orderInfo = orderInfo;
    }
}
