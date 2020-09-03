package com.tr.dal.mybatis.model;

/**
 * Created by yangjie on 2018/2/5.
 */
public class FormDisplayGroupPO {
    private String formKey;
    private String displayGroupName;
    private String displayType;

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getDisplayGroupName() {
        return displayGroupName;
    }

    public void setDisplayGroupName(String displayGroupName) {
        this.displayGroupName = displayGroupName;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }
}
