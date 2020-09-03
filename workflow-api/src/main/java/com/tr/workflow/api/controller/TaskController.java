package com.tr.workflow.api.controller;

import com.tr.biz.api.CustomTaskService;
import com.tr.biz.bo.CommodityOrderTaskBO;
import com.tr.biz.bo.TaskBO;
import com.tr.biz.request.ApprovalRecordAddRequest;
import com.tr.biz.request.TaskSearchRequest;
import com.tr.common.framework.results.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping(value = "/runtime/tasks")
public class TaskController {
	@Resource
	private CustomTaskService customTaskService;

	@RequestMapping(value = "/searchByAssignee", method = RequestMethod.GET)
	@ResponseBody
	public Object searchByAssignee(TaskSearchRequest taskSearchRequest) throws Exception {
		TaskBO taskBO = customTaskService.emptyBO();
		return taskBO;
	}


	@RequestMapping(value = "/commodityOrderTaskListPage", method = RequestMethod.GET)
	@ResponseBody
	public Object commodityOrderTaskListPage(TaskSearchRequest taskSearchRequest) throws Exception {
		CommodityOrderTaskBO commodityOrderTaskBO = customTaskService.commodityOrderTaskListPage(taskSearchRequest);
		return commodityOrderTaskBO;
	}

	/**
	 * 签约宝联合放款任务列表
	 * @param taskSearchRequest
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/joinLoanProcessTask")
	public Object joinLoanProcessTask(TaskSearchRequest taskSearchRequest) throws Exception {
		TaskBO taskBO = customTaskService.searchMerchantTaskBO(taskSearchRequest);
		CommonResult result = new CommonResult("200","成功",taskBO);
		return result;
	}
}