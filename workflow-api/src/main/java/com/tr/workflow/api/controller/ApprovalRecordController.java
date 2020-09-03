package com.tr.workflow.api.controller;

import com.tr.biz.request.ApprovalRecordAddRequest;
import com.tr.biz.request.ApprovalRecordQueryRequest;
import com.tr.common.framework.results.CommonResult;
import com.tr.dal.mybatis.mapper.CustomMapper;
import com.tr.dal.mybatis.model.ApprovalRecordPO;
import org.activiti.engine.ManagementService;
import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;
import org.activiti.engine.impl.cmd.CustomSqlExecution;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping(value = "/approvalRecord")
public class ApprovalRecordController {
	@Resource
	private ManagementService managementService;

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public List<ApprovalRecordPO> query(ApprovalRecordQueryRequest approvalRecordQueryRequest) throws Exception {
		CustomSqlExecution<CustomMapper, List<ApprovalRecordPO>> customSqlExecution =
				new AbstractCustomSqlExecution<CustomMapper, List<ApprovalRecordPO>>(CustomMapper.class) {
					public List<ApprovalRecordPO> execute(CustomMapper customMapper) {
						return customMapper.approvalRecordQuery(approvalRecordQueryRequest.getOrderId());
					}
				};
		List<ApprovalRecordPO> results = managementService.executeCustomSql(customSqlExecution);
		return results;
	}


	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult add(@RequestBody ApprovalRecordAddRequest approvalRecordAddRequest) throws Exception {
		CustomSqlExecution<CustomMapper, Boolean> customSqlExecution =
				new AbstractCustomSqlExecution<CustomMapper, Boolean>(CustomMapper.class) {
					public Boolean execute(CustomMapper customMapper) {
						return customMapper.approvalRecordAdd(approvalRecordAddRequest.getOrderId(), approvalRecordAddRequest.getOperateName(),approvalRecordAddRequest.getOperation(),approvalRecordAddRequest.getTaskId(),approvalRecordAddRequest.getTaskName(),approvalRecordAddRequest.getProcessInstanceId(),approvalRecordAddRequest.getComment(),approvalRecordAddRequest.getExternalResponse());
					}
				};
		managementService.executeCustomSql(customSqlExecution);
		return new CommonResult();
	}

	/**
	 * 删除订单对应的大数据审批记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult delete(ApprovalRecordQueryRequest request) throws Exception{
		CustomSqlExecution<CustomMapper, Integer> customSqlExecution =
				new AbstractCustomSqlExecution<CustomMapper, Integer>(CustomMapper.class) {
					public Integer execute(CustomMapper customMapper) {
						return customMapper.approvalRecordDelete(request.getOrderId());
					}
				};
		managementService.executeCustomSql(customSqlExecution);
		return new CommonResult();
	}
}