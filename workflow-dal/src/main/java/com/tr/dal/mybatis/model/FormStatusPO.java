package com.tr.dal.mybatis.model;

/**
 * Created by yangjie on 2018/2/5.
 */
public class FormStatusPO {
    private String processKey;
    private String formKey;
    private String operationOrderStatus;

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getOperationOrderStatus() {
        return operationOrderStatus;
    }

    public void setOperationOrderStatus(String operationOrderStatus) {
        this.operationOrderStatus = operationOrderStatus;
    }
}
