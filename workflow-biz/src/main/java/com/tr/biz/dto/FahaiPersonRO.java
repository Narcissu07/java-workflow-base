package com.tr.biz.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/26.
 */
public class FahaiPersonRO {
    private String searchSeconds;
    private String count;
    private String pageNo;
    private String range;
    private List<Map<String, String>> entryList;
    private String code;

    public String getSearchSeconds() {
        return searchSeconds;
    }

    public void setSearchSeconds(String searchSeconds) {
        this.searchSeconds = searchSeconds;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<Map<String, String>> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<Map<String, String>> entryList) {
        this.entryList = entryList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
