package com.tr.dal.mybatis.uo;

import java.util.Map;

/**
 * Created by yangjie on 2018/2/2.
 */
public class CommodityOrderTaskUO {
    private Map<String, String> taskInfo;
    private Map<String, String> commodityOrderInfo;

    public Map<String, String> getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(Map<String, String> taskInfo) {
        this.taskInfo = taskInfo;
    }

    public Map<String, String> getCommodityOrderInfo() {
        return commodityOrderInfo;
    }

    public void setCommodityOrderInfo(Map<String, String> commodityOrderInfo) {
        this.commodityOrderInfo = commodityOrderInfo;
    }
}
