package com.tr.biz.dto;

import com.tr.dal.mybatis.model.ApprovalRecordPO;

import java.util.List;

public class ApprovalResultDTO {
    private List<ApprovalRecordPO> list;
    private Boolean result;
    private String resultStr;

    public List<ApprovalRecordPO> getList() {
        return list;
    }

    public void setList(List<ApprovalRecordPO> list) {
        this.list = list;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }
}
