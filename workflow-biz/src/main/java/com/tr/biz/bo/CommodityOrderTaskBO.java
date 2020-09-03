package com.tr.biz.bo;

import com.tr.dal.mybatis.uo.CommodityOrderTaskUO;
import com.tr.dal.mybatis.uo.TaskUO;

import java.util.List;

/**
 * Created by yangjie on 2018/2/2.
 */
public class CommodityOrderTaskBO {
    private List<CommodityOrderTaskUO> data;
    private Long total;

    public List<CommodityOrderTaskUO> getData() {
        return data;
    }

    public void setData(List<CommodityOrderTaskUO> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
