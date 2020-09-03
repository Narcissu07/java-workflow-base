package com.tr.dal.mybatis.model;

/**
 * Created by yangjie on 2018/2/5.
 */
public class ListFieldPO {
    private String listKey;
    private String fieldName;
    private String fieldDesc;
    private String fieldType;
    private String dictTypeCode;
    private String dictFilter;
    private String domain;

    public String getListKey() {
        return listKey;
    }

    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getDictTypeCode() {
        return dictTypeCode;
    }

    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

    public String getDictFilter() {
        return dictFilter;
    }

    public void setDictFilter(String dictFilter) {
        this.dictFilter = dictFilter;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
