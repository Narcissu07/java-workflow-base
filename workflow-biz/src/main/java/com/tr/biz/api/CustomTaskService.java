package com.tr.biz.api;


import com.tr.biz.bo.CommodityOrderTaskBO;
import com.tr.biz.bo.TaskBO;
import com.tr.biz.request.ApprovalRecordAddRequest;
import com.tr.biz.request.TaskSearchRequest;

public interface CustomTaskService {
	public TaskBO searchByAssignee(TaskSearchRequest taskSearchRequest) throws Exception;
	TaskBO emptyBO();
	public CommodityOrderTaskBO commodityOrderTaskListPage(TaskSearchRequest taskSearchRequest) throws Exception;
	TaskBO searchMerchantTaskBO(TaskSearchRequest taskSearchRequest) throws Exception;
}