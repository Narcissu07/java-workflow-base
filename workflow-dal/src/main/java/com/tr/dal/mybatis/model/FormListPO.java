package com.tr.dal.mybatis.model;

/**
 * Created by yangjie on 2018/2/5.
 */
public class FormListPO {
    private String formKey;
    private String formListKey;
    private String displayType;

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormListKey() {
        return formListKey;
    }

    public void setFormListKey(String formListKey) {
        this.formListKey = formListKey;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }
}
