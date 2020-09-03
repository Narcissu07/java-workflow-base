package com.tr.biz.bo;

import com.tr.dal.mybatis.uo.TaskUO;

import java.util.List;

/**
 * Created by yangjie on 2018/2/2.
 */
public class ApprovalInfoBO {
    private Integer acceptCount;
    private Integer rejectCount;
    private Integer exceptionCount;

    public Integer getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(Integer acceptCount) {
        this.acceptCount = acceptCount;
    }

    public Integer getRejectCount() {
        return rejectCount;
    }

    public void setRejectCount(Integer rejectCount) {
        this.rejectCount = rejectCount;
    }

    public Integer getExceptionCount() {
        return exceptionCount;
    }

    public void setExceptionCount(Integer exceptionCount) {
        this.exceptionCount = exceptionCount;
    }
}
