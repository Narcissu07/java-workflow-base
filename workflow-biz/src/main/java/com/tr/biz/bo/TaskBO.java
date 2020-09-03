package com.tr.biz.bo;

import com.tr.dal.mybatis.model.TaskPO;
import com.tr.dal.mybatis.uo.TaskUO;

import java.util.List;

/**
 * Created by yangjie on 2018/2/2.
 */
public class TaskBO {
    private List<TaskUO> data;
    private Long total;
    private Integer start;
    private String sort;
    private String order;
    private Integer size;

    public List<TaskUO> getData() {
        return data;
    }

    public void setData(List<TaskUO> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
