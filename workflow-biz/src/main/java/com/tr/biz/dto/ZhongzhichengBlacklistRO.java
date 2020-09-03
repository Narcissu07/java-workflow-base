package com.tr.biz.dto;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/26.
 */
public class ZhongzhichengBlacklistRO {
    private Map<String, Map<String, String>> blacklist;

    public Map<String, Map<String, String>> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Map<String, Map<String, String>> blacklist) {
        this.blacklist = blacklist;
    }
}
