package com.tr.biz.dto;

import java.util.HashMap;
import java.util.List;

public class MerchantOrderDTO {
    private HashMap<String,Object> order;
    private HashMap<String,Object> userBasicInfo;
    private HashMap<String,Object> merchantInfo;
    private List<HashMap<String,Object>> borrowerList;
    private List<HashMap<String,Object>> orderFiles;
    private HashMap<String,Object> businessMan;
    private List<HashMap<String,Object>> approvalPOList;

    public HashMap<String, Object> getOrder() {
        return order;
    }

    public void setOrder(HashMap<String, Object> order) {
        this.order = order;
    }

    public HashMap<String, Object> getUserBasicInfo() {
        return userBasicInfo;
    }

    public void setUserBasicInfo(HashMap<String, Object> userBasicInfo) {
        this.userBasicInfo = userBasicInfo;
    }

    public HashMap<String, Object> getMerchantInfo() {
        return merchantInfo;
    }

    public void setMerchantInfo(HashMap<String, Object> merchantInfo) {
        this.merchantInfo = merchantInfo;
    }

    public List<HashMap<String, Object>> getBorrowerList() {
        return borrowerList;
    }

    public void setBorrowerList(List<HashMap<String, Object>> borrowerList) {
        this.borrowerList = borrowerList;
    }

    public List<HashMap<String, Object>> getOrderFiles() {
        return orderFiles;
    }

    public void setOrderFiles(List<HashMap<String, Object>> orderFiles) {
        this.orderFiles = orderFiles;
    }

    public HashMap<String, Object> getBusinessMan() {
        return businessMan;
    }

    public void setBusinessMan(HashMap<String, Object> businessMan) {
        this.businessMan = businessMan;
    }

    public List<HashMap<String, Object>> getApprovalPOList() {
        return approvalPOList;
    }

    public void setApprovalPOList(List<HashMap<String, Object>> approvalPOList) {
        this.approvalPOList = approvalPOList;
    }
}
