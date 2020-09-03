package com.tr.dal.mybatis.uo;

import com.tr.dal.mybatis.model.*;

import java.util.List;

/**
 * Created by yangjie on 2018/2/2.
 */
public class ConfigUO {
    private List<ListFormPO> listFormAll;
    private List<ListFieldPO> listFieldAll;
    private List<FormStatusPO> formStatusAll;
    private List<FormFieldPO> formFieldAll;
    private List<FormListPO> formListAll;
    private List<FieldInfoPO> fieldInfoAll;
    private List<FormDisplayGroupPO> formDisplayGroupAll;

    public List<ListFormPO> getListFormAll() {
        return listFormAll;
    }

    public void setListFormAll(List<ListFormPO> listFormAll) {
        this.listFormAll = listFormAll;
    }

    public List<ListFieldPO> getListFieldAll() {
        return listFieldAll;
    }

    public void setListFieldAll(List<ListFieldPO> listFieldAll) {
        this.listFieldAll = listFieldAll;
    }

    public List<FormStatusPO> getFormStatusAll() {
        return formStatusAll;
    }

    public void setFormStatusAll(List<FormStatusPO> formStatusAll) {
        this.formStatusAll = formStatusAll;
    }

    public List<FormFieldPO> getFormFieldAll() {
        return formFieldAll;
    }

    public void setFormFieldAll(List<FormFieldPO> formFieldAll) {
        this.formFieldAll = formFieldAll;
    }

    public List<FormListPO> getFormListAll() {
        return formListAll;
    }

    public void setFormListAll(List<FormListPO> formListAll) {
        this.formListAll = formListAll;
    }

    public List<FieldInfoPO> getFieldInfoAll() {
        return fieldInfoAll;
    }

    public void setFieldInfoAll(List<FieldInfoPO> fieldInfoAll) {
        this.fieldInfoAll = fieldInfoAll;
    }

    public List<FormDisplayGroupPO> getFormDisplayGroupAll() {
        return formDisplayGroupAll;
    }

    public void setFormDisplayGroupAll(List<FormDisplayGroupPO> formDisplayGroupAll) {
        this.formDisplayGroupAll = formDisplayGroupAll;
    }
}
