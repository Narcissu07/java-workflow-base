package com.tr.dal.mybatis.ro;

import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2018/2/24.
 */
public class OrdersRO {
    private List<Map<String, String>> list;

    public List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }
}
