package com.tr.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tr.biz.api.CustomTaskService;
import com.tr.biz.bo.CommodityOrderTaskBO;
import com.tr.biz.bo.TaskBO;
import com.tr.biz.dto.OrdersDTO;
import com.tr.biz.request.TaskSearchRequest;


import com.tr.common.config.BizConfig;
import com.tr.common.framework.api.CommonHttpClient;
import com.tr.common.framework.enums.CommonResultCodeEnum;
import com.tr.common.framework.exceptions.RemoteDataException;
import com.tr.common.utils.DateUtil;
import com.tr.dal.mybatis.ro.OrdersRO;
import com.tr.dal.mybatis.uo.CommodityOrderTaskUO;
import com.tr.dal.mybatis.uo.TaskUO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CustomTaskServiceImpl implements CustomTaskService {
	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private CommonHttpClient commonHttpClient;

	@Resource
	private BizConfig bizConfig;

	public TaskBO searchByAssignee(TaskSearchRequest taskSearchRequest) throws Exception {
		TaskBO taskBO = new TaskBO();
		List<TaskUO> taskUOList = new ArrayList<TaskUO>();

		String assignee = taskSearchRequest.getAssignee();
		Long total = taskService.createTaskQuery().taskAssignee(assignee).count();
		Integer start = Integer.valueOf(taskSearchRequest.getStart());
		Integer size = Integer.valueOf(taskSearchRequest.getSize());
		//获取任务列表
		List<Task> taskList = taskService.createTaskQuery()
				.taskAssignee(assignee)
				.orderByTaskCreateTime().desc()
				.listPage(start,size);

		if(taskList.size()>0){
			//获取实例集
			Set<String> instanceIdSet = new HashSet<String>();
			for(int i=0; i<taskList.size(); i++){
				instanceIdSet.add(taskList.get(i).getProcessInstanceId());
			}

			//获取关联业务key
			List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(instanceIdSet).list();
			String businessKeys = "";
			Map<String, String> businessKeyMap = new HashMap<String, String>();
			for(int i=0; i<processInstanceList.size(); i++){
				businessKeys = businessKeys + "," + processInstanceList.get(i).getBusinessKey();
				businessKeyMap.put(processInstanceList.get(i).getBusinessKey(), processInstanceList.get(i).getProcessInstanceId());
			}
			businessKeys = businessKeys.substring(1);

			//通过业务key查询关联业务列表
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orders", businessKeys);
			String url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlFindOrderByIds;
			String param = JSON.toJSONString(paramMap);
			String msg = commonHttpClient.postBody(url, param);

			OrdersDTO ordersDTO;
			try {
				ordersDTO = JSON.parseObject(msg, OrdersDTO.class);
			} catch (Exception e) {
				throw new RemoteDataException(CommonResultCodeEnum.REMOTE_DATA_ERROR.getCode(), CommonResultCodeEnum.REMOTE_DATA_ERROR.getMessage());
			}
			String code = ordersDTO.getCode();
			if(StringUtils.isEmpty(code)|| (!code.equals("0"))){
				throw new RemoteDataException(CommonResultCodeEnum.REMOTE_DATA_ERROR.getCode(), CommonResultCodeEnum.REMOTE_DATA_ERROR.getMessage());
			}

			String orderStr = ordersDTO.getData();
			OrdersRO orderInfo = JSONObject.parseObject(orderStr, OrdersRO.class);//JSON字符串转对象
			List<Map<String, String>> orderList = orderInfo.getList();
			for(int i=0; i<taskList.size(); i++){
				TaskUO taskUO = new TaskUO();
				Map<String, String> taskInfo = taskToMap(taskList.get(i));
				taskUO.setTaskInfo(taskInfo);


				for(int j=0; j<orderList.size(); j++){
					String orderId = orderList.get(j).get("orderId");
					String processDefinitionId = businessKeyMap.get(orderId);
					if(processDefinitionId.equals(taskList.get(i).getProcessInstanceId())){
						taskUO.setOrderInfo(orderList.get(j));
						break;
					}
				}

				taskUOList.add(taskUO);
			}

			taskBO.setData(taskUOList);
			taskBO.setTotal(total);
			taskBO.setStart(Integer.valueOf(taskSearchRequest.getStart()));
			taskBO.setSize(Integer.valueOf(taskSearchRequest.getSize()));
		}else{
			taskBO.setData(taskUOList);
			taskBO.setTotal(0L);
			taskBO.setStart(Integer.valueOf(taskSearchRequest.getStart()));
			taskBO.setSize(Integer.valueOf(taskSearchRequest.getSize()));
		}
		return taskBO;
	}

	@Override
	public TaskBO emptyBO() {
		TaskBO taskBO = new TaskBO();
		List<TaskUO> taskUOList = new ArrayList<TaskUO>();
		taskBO.setData(taskUOList);
		taskBO.setTotal(0L);
		taskBO.setStart(0);
		taskBO.setSize(20);
		return taskBO;
	}


	public CommodityOrderTaskBO commodityOrderTaskListPage(TaskSearchRequest taskSearchRequest) throws Exception {
		CommodityOrderTaskBO commodityOrderTaskBO = new CommodityOrderTaskBO();

		List<CommodityOrderTaskUO> taskUOList = new ArrayList<CommodityOrderTaskUO>();

		String assignee = taskSearchRequest.getAssignee();
		Long total = taskService.createTaskQuery().taskAssignee(assignee).count();
		Integer start = Integer.valueOf(taskSearchRequest.getStart());
		Integer size = Integer.valueOf(taskSearchRequest.getSize());
		//获取任务列表
		List<Task> taskList = taskService.createTaskQuery()
				.taskAssignee(assignee)
				.orderByTaskCreateTime().desc()
				.listPage(start,size);

		if(taskList.size()>0){
			//获取实例集
			Set<String> instanceIdSet = new HashSet<String>();
			for(int i=0; i<taskList.size(); i++){
				instanceIdSet.add(taskList.get(i).getProcessInstanceId());
			}

			//获取关联业务key
			List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(instanceIdSet).list();
			String businessKeys = "";
			Map<String, String> businessKeyMap = new HashMap<String, String>();
			for(int i=0; i<processInstanceList.size(); i++){
				businessKeys = businessKeys + "," + processInstanceList.get(i).getBusinessKey();
				businessKeyMap.put(processInstanceList.get(i).getBusinessKey(), processInstanceList.get(i).getProcessInstanceId());
			}
			businessKeys = businessKeys.substring(1);

			//通过业务key查询关联业务列表
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderIds", businessKeys);
			String url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlCommodityCommodityOrderListPageByOrderIds;
			String param = JSON.toJSONString(paramMap);
			String msg = commonHttpClient.postBody(url, param);

			OrdersDTO ordersDTO;
			try {
				ordersDTO = JSON.parseObject(msg, OrdersDTO.class);
			} catch (Exception e) {
				throw new RemoteDataException(CommonResultCodeEnum.REMOTE_DATA_ERROR.getCode(), CommonResultCodeEnum.REMOTE_DATA_ERROR.getMessage());
			}
			String code = ordersDTO.getCode();
			if(StringUtils.isEmpty(code)|| (!code.equals("0"))){
				throw new RemoteDataException(CommonResultCodeEnum.REMOTE_DATA_ERROR.getCode(), CommonResultCodeEnum.REMOTE_DATA_ERROR.getMessage());
			}

			String orderStr = ordersDTO.getData();
			List<Map<String, String>> orderList = JSONObject.parseObject(orderStr, List.class);//JSON字符串转对象
			for(int i=0; i<taskList.size(); i++){
				CommodityOrderTaskUO taskUO = new CommodityOrderTaskUO();
				Map<String, String> taskInfo = taskToMap(taskList.get(i));
				taskUO.setTaskInfo(taskInfo);

				for(int j=0; j<orderList.size(); j++){
					String orderId = orderList.get(j).get("loanOrderId");
					String processDefinitionId = businessKeyMap.get(orderId);
					if(processDefinitionId.equals(taskList.get(i).getProcessInstanceId())){
						taskUO.setCommodityOrderInfo(orderList.get(j));
						break;
					}
				}

				taskUOList.add(taskUO);
			}

			commodityOrderTaskBO.setData(taskUOList);
			commodityOrderTaskBO.setTotal(total);
		}else{
			commodityOrderTaskBO.setData(taskUOList);
			commodityOrderTaskBO.setTotal(0L);
		}
		return commodityOrderTaskBO;
	}

	@Override
	public TaskBO searchMerchantTaskBO(TaskSearchRequest taskSearchRequest) throws Exception {
		taskSearchRequest.setProcessDefinitionKey("MerchantJoinLoanProcess");
		TaskBO taskBO = new TaskBO();
		List<TaskUO> taskUOList = new ArrayList<TaskUO>();

		Integer start = Integer.valueOf(taskSearchRequest.getStart());
		Integer size = Integer.valueOf(taskSearchRequest.getSize());
		//获取任务列表
		TaskQuery query = taskService.createTaskQuery();
		if(StringUtils.isNotBlank(taskSearchRequest.getAssignee())){
			query.taskAssignee(taskSearchRequest.getAssignee());
		}
		if(StringUtils.isNotBlank(taskSearchRequest.getCategory())){
			query.taskCategory(taskSearchRequest.getCategory());
		}
		if(StringUtils.isNotBlank(taskSearchRequest.getProcessDefinitionKey())){
			query.processDefinitionKey(taskSearchRequest.getProcessDefinitionKey());
		}
		Long total = query.count();
		List<Task> taskList = query.orderByTaskCreateTime().desc().listPage(start,size);

		if(taskList.size()>0){
			//获取实例集
			Set<String> instanceIdSet = new HashSet<String>();
			for(int i=0; i<taskList.size(); i++){
				instanceIdSet.add(taskList.get(i).getProcessInstanceId());
			}

			//获取关联业务key
			List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(instanceIdSet).list();
			Set<String> businessKeys = new HashSet<>();
			Map<String, String> businessKeyMap = new HashMap<String, String>();
			for(int i=0; i<processInstanceList.size(); i++){
				businessKeys.add(processInstanceList.get(i).getBusinessKey());
				businessKeyMap.put(processInstanceList.get(i).getBusinessKey(), processInstanceList.get(i).getProcessInstanceId());
			}

			//通过业务key查询关联业务列表
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orderNos", businessKeys);
			paramMap.put("page", "1");
			paramMap.put("limit", "100");
			String url = bizConfig.integrationOrderUrlSearchRoot + "/merchantOrder/getOrderList";
			String param = JSON.toJSONString(paramMap);
			String msg = commonHttpClient.postBody(url, param);

			OrdersDTO ordersDTO;
			try {
				ordersDTO = JSON.parseObject(msg, OrdersDTO.class);
			} catch (Exception e) {
				throw new RemoteDataException(CommonResultCodeEnum.REMOTE_DATA_ERROR.getCode(), CommonResultCodeEnum.REMOTE_DATA_ERROR.getMessage());
			}
			String code = ordersDTO.getCode();
			if(StringUtils.isEmpty(code)|| (!code.equals("200"))){
				throw new RemoteDataException(CommonResultCodeEnum.REMOTE_DATA_ERROR.getCode(), CommonResultCodeEnum.REMOTE_DATA_ERROR.getMessage());
			}

			String orderStr = ordersDTO.getData();
			OrdersRO orderInfo = JSONObject.parseObject(orderStr, OrdersRO.class);//JSON字符串转对象
			List<Map<String, String>> orderList = orderInfo.getList();
			for(int i=0; i<taskList.size(); i++){
				TaskUO taskUO = new TaskUO();
				Map<String, String> taskInfo = taskToMap(taskList.get(i));
				taskUO.setTaskInfo(taskInfo);

				for(int j=0; j<orderList.size(); j++){
					String orderId = orderList.get(j).get("orderNo");
					String processDefinitionId = businessKeyMap.get(orderId);
					if(processDefinitionId.equals(taskList.get(i).getProcessInstanceId())){
						taskUO.setOrderInfo(orderList.get(j));
						break;
					}
				}

				taskUOList.add(taskUO);
			}
			taskBO.setData(taskUOList);
			taskBO.setTotal(total);
			taskBO.setStart(Integer.valueOf(taskSearchRequest.getStart()));
			taskBO.setSize(Integer.valueOf(taskSearchRequest.getSize()));
		}else{
			taskBO.setData(taskUOList);
			taskBO.setTotal(0L);
			taskBO.setStart(Integer.valueOf(taskSearchRequest.getStart()));
			taskBO.setSize(Integer.valueOf(taskSearchRequest.getSize()));
		}
		return taskBO;
	}
	private Map<String,String> taskToMap(Task task){
		Map<String, String> taskInfo = new HashMap<String, String>();
		taskInfo.put("taskId",task.getId());
		taskInfo.put("taskAssignee",task.getAssignee());
		taskInfo.put("taskDescription",task.getDescription());
		taskInfo.put("taskName",task.getName());
		taskInfo.put("processInstanceId",task.getProcessInstanceId());
		taskInfo.put("taskTenantId",task.getTenantId());
		taskInfo.put("taskFormKey",task.getFormKey());
		taskInfo.put("taskDefinitionKey",task.getTaskDefinitionKey());
		taskInfo.put("taskCreateTime", DateUtil.convert2String(task.getCreateTime(), DateUtil.DATETIME_FORMAT_2));
		return taskInfo;
	}
}
