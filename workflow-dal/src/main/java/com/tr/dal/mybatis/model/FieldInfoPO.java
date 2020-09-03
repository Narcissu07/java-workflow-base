package com.tr.dal.mybatis.model;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

/**
 * Created by yangjie on 2018/2/5.
 */
public class FieldInfoPO {
    private String fieldName;
    private String fieldDesc;
    private String fieldType;
    private String dictTypeCode;
    private String dictFilter;
    private String domain;

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
