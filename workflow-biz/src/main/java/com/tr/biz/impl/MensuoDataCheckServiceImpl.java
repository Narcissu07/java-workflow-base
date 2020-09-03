package com.tr.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tr.biz.api.MensuoDataCheckService;
import com.tr.biz.bo.ApprovalInfoBO;
import com.tr.biz.dto.*;
import com.tr.biz.request.MensuoDataCheckRequest;
import com.tr.common.framework.results.CommonResult;
import com.tr.common.utils.DateUtil;
import com.tr.common.utils.IdNoUtil;
import com.tr.dal.mybatis.model.ApprovalRecordPO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.DTD;
import java.util.*;

@Service
public class MensuoDataCheckServiceImpl extends BaseDataCheckService implements MensuoDataCheckService {
	private Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 特殊省份代码集合
     * 包含（陕西、云南、广西、海南、贵州、山西、辽宁、吉林、黑龙江、河南）
     */
    private static Set<String> SPECIAL_PROVINCES = new HashSet<String>();
    static {
        SPECIAL_PROVINCES.add("610000");
        SPECIAL_PROVINCES.add("530000");
        SPECIAL_PROVINCES.add("450000");
        SPECIAL_PROVINCES.add("460000");
        SPECIAL_PROVINCES.add("520000");
        SPECIAL_PROVINCES.add("140000");
        SPECIAL_PROVINCES.add("210000");
        SPECIAL_PROVINCES.add("220000");
        SPECIAL_PROVINCES.add("230000");
        SPECIAL_PROVINCES.add("410000");
    }

	public void dataCheck(DelegateExecution execution, RuntimeService runtimeService, TaskService taskService) throws Exception {
		String orderId = execution.getProcessInstanceBusinessKey();
		if("0".equals(bizConfig.openDataCheck)){
			updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),APPROVAL_ACCEPT,orderId);
			return;
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderId", orderId);
		String url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlFindOrderById;
		String param = JSON.toJSONString(paramMap);
		String msg = commonHttpClient.postBody(url, param);
		CommonDTO commonDTO = JSON.parseObject(msg, CommonDTO.class);
		Map<String, String> dataMap = commonDTO.getData();
		String name = dataMap.get("name");
		String idNo = dataMap.get("idNo");
		String bankCardNo = dataMap.get("bankCardNo");
		String mobileNo = dataMap.get("mobileNo");
		String friendMobileNo1 = dataMap.get("friendMobileNo1");
		String friendName1 = dataMap.get("friendName1");
		//申请时间
        String createTime = dataMap.get("createTime");
        String userId = dataMap.get("userId");
        //经度
		String lng = dataMap.get("lng");
		//纬度
		String lat = dataMap.get("lat");
		//户籍省
		String censusProvince = dataMap.get("censusProvince");
		//户籍市
		String censusCity = dataMap.get("censusCity");
		//户籍区
		String censusRegion = dataMap.get("censusRegion");
		//居住省
		String province = dataMap.get("province");
		//居住市
		String city = dataMap.get("city");
		//居住区
		String region = dataMap.get("region");

		ApprovalInfoBO approvalInfoBO = new ApprovalInfoBO();
		approvalInfoBO.setAcceptCount(0);
		approvalInfoBO.setRejectCount(0);
		approvalInfoBO.setExceptionCount(0);
		boolean result = true;
		String operation= "";
		// 历史登录是否跨省
		operation = historyLoginLogValid(approvalInfoBO, orderId, execution.getProcessInstanceId(),userId);
		if(!APPROVAL_ACCEPT.equals(operation)){
			//updateStateAndCommitWorkFlow(runtimeService,execution.getId(),operation,orderId);
			//return;
			result = false;
		}
		// 申请时间段不在1-7点
		operation = applyTimeValid(approvalInfoBO, orderId, execution.getProcessInstanceId(),createTime);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		// 当天内账户申请次数<6
		operation = oneDayApplyTimesValid(approvalInfoBO, orderId, execution.getProcessInstanceId(),userId,createTime);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//7天内账户申请次数
		operation = sevenDaysApplyTimesValid(approvalInfoBO, orderId, execution.getProcessInstanceId(),userId,createTime);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//用户申请的地理位置与居住地址是否在同一城市
		operation = applyAddrAndUserAddrValid(approvalInfoBO, orderId, execution.getProcessInstanceId(),province,city,lng,lat);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//同一手机号匹配申请笔数（非本人）
		operation = notSelfApplyTimes(approvalInfoBO, orderId, execution.getProcessInstanceId(),mobileNo,userId);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//同一手机号匹配通过笔数（非本人）
		operation = notSelfSuccessTimes(approvalInfoBO, orderId, execution.getProcessInstanceId(),mobileNo,userId);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//同一城市户籍匹配申请笔数（非本人）
		operation = notSelfSameCityApplyTimes(approvalInfoBO, orderId, execution.getProcessInstanceId(),userId,censusProvince,censusCity);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//同一城市户籍匹配通过笔数（非本人）
		operation = notSelfSameCitySuccessTimes(approvalInfoBO, orderId, execution.getProcessInstanceId(),userId,censusProvince,censusCity);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//申请人年龄规则
		operation = userAgeValid(approvalInfoBO, orderId, execution.getProcessInstanceId(),idNo);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//中智诚黑名单
		operation = zhongzhichengBlacklist(approvalInfoBO, orderId, execution.getProcessInstanceId(), name, idNo, mobileNo);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//法海失信执行人
		operation = fahaiPenson(approvalInfoBO, orderId, execution.getProcessInstanceId(), name, idNo);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//同盾评分核查
		operation = tongdunGetData(approvalInfoBO, orderId, execution.getProcessInstanceId(), name, idNo, mobileNo);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//手机号码、身份证号码、姓名验证（集奥）
		operation = geoNameMobileIdnumberVerify(approvalInfoBO, orderId, execution.getProcessInstanceId(), name, idNo, mobileNo);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//在网状态（集奥）
		operation = geoMobileStatusVerify(approvalInfoBO, orderId, execution.getProcessInstanceId(), name, idNo, mobileNo);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//在网时长（集奥）
		operation = geoMobileValidTime(approvalInfoBO, orderId, execution.getProcessInstanceId(), name, idNo, mobileNo);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//联系人有效
		operation = geoNameMobileVerifyAndCallAmount(approvalInfoBO, orderId, execution.getProcessInstanceId(), name, idNo, mobileNo,friendName1, friendMobileNo1);
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		String oper = result ? APPROVAL_ACCEPT:APPROVAL_REJECT;
		updateStateAndCommitWorkFlow(runtimeService,execution.getId(),oper,orderId);
	}

	private void updateStateAndCommitWorkFlow(RuntimeService runtimeService,String processInstanceId,String operation,String orderId) throws Exception{
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("loanOrderId", orderId);
		String url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlCommodityGetCommodityOrder;
		String param = JSON.toJSONString(paramMap);
		String msg = commonHttpClient.postBody(url, param);
		CommodityOrderDTO commodityOrderDTO = JSON.parseObject(msg, CommodityOrderDTO.class);
		List<Map<String, String>> dataList = commodityOrderDTO.getData();
		String commodityOrderId = dataList.get(0).get("commodityOrderId");
		String commodityOrderStatus = "";
		String loanOrderStatus = "";
		if(APPROVAL_ACCEPT.equals(operation)){
			commodityOrderStatus = "3";
			loanOrderStatus = "2";
		}else if(APPROVAL_REJECT.equals(operation)){
			commodityOrderStatus = "2";
			loanOrderStatus = "7";
		}

		paramMap = new HashMap<String, String>();
		paramMap.put("commodityOrderId", commodityOrderId);
		paramMap.put("status", commodityOrderStatus);
		url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlCommoditySaveCommodityOrder;
		param = JSON.toJSONString(paramMap);
		commonHttpClient.postBody(url, param);



		paramMap = new HashMap<String, String>();
		paramMap.put("orderId", orderId);
		paramMap.put("orderStatus", loanOrderStatus);
		paramMap.put("operateName", "系统自动");
		url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlOrderSaveOrder;
		param = JSON.toJSONString(paramMap);
		commonHttpClient.postBody(url, param);
		runtimeService.setVariable(processInstanceId,"operation",operation);
	}


	private String geoMobileValidTime(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId, String name, String idcardNo, String mobile) throws Exception{
		String taskName = "集奥在线时长查询";
		try{
			String url = bizConfig.integrationDataresourceUrlRoot + bizConfig.integrationDataresourceUrlGeoMobileValidTime;
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("cid", mobile);
			paramMap.put("realName", name);
			paramMap.put("idNumber", idcardNo);
			String param = JSON.toJSONString(paramMap);
			String msg = commonHttpClient.postBody(url, param);
			GeoDTO geoDTO = JSON.parseObject(msg, GeoDTO.class);
			if(!geoDTO.getCode().equals("200")){
				approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
				addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, geoDTO.getMessage(), msg);
				return APPROVAL_REJECT;
			}
			GeoRO geoRO = geoDTO.getData();
			String code = geoRO.getCode();

			Set<String> set = new HashSet<String>();
			set.add("4");
			set.add("5");
			set.add("6");
			set.add("7");
			set.add("06");
			boolean flag = false;
			if(set.contains(code)){
				flag = true;
			}
			if(flag){
				approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
				addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, geoRO.getDesc(), msg);
				return APPROVAL_ACCEPT;
			}else{
				approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
				addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, geoRO.getDesc(), msg);
				return APPROVAL_REJECT;
			}
		}catch (Exception e){
			approvalInfoBO.setExceptionCount(approvalInfoBO.getExceptionCount()+1);
			addApprovalRecord(orderId, APPROVAL_EXCEPTION, taskName, processInstanceId, "查询异常", e.getMessage());
			return APPROVAL_EXCEPTION;
		}
	}

	private String geoNameMobileVerifyAndCallAmount(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId, String name, String idcardNo, String mobile,String friendName, String friendMobileNo1) throws Exception{
		String taskName = "集奥手机号码、姓名和通话次数验证";
		try{
			GeoDTO friendDTO = friendNameMobileVerify(friendName,friendMobileNo1);
			String url = bizConfig.integrationDataresourceUrlRoot + bizConfig.integrationDataresourceUrlGeoCallAmount;
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("cid", mobile);
			paramMap.put("realName", name);
			paramMap.put("idNumber", idcardNo);
			paramMap.put("cid2", friendMobileNo1);

			String param = JSON.toJSONString(paramMap);
			String msg = commonHttpClient.postBody(url, param);
			GeoDTO geoDTO = JSON.parseObject(msg, GeoDTO.class);
			boolean flag = false;
			String comment = "";
			if(!"200".equals(friendDTO.getCode())){
				flag = false;
				comment = friendDTO.getMessage();
			}else{
				//联系人姓名手机一致
				if("0".equals(friendDTO.getData().getCode())){
					if(!geoDTO.getCode().equals("200")){
						flag = false;
						comment = geoDTO.getMessage();
					}else{
						if("0".equals(geoDTO.getData().getCode())){
							flag = false;
							comment = "一致，但无通话次数";
						}else if("10000002".equals(geoDTO.getData().getCode())){
							flag = true;
							comment = "一致，不支持的运营商";
						}else if("10000004".equals(geoDTO.getData().getCode())){
							flag = false;
							comment = "一致,查无此记录";
						}else{
							flag = true;
							comment = "一致，且有通话次数";
						}
					}
				}else{
					if(!geoDTO.getCode().equals("200")){
						flag = false;
						comment = geoDTO.getMessage();
					}else{
						int code = Integer.parseInt(geoDTO.getData().getCode());
						if(code<3 || code==101 ){
							flag = false;
							comment = "不一致，且通话次数不够";
						}else if(code==10000004){
                            flag = false;
                            comment = "不一致，且查无此记录";
                        }else if(code==10000002){
                            flag = false;
                            comment = "不一致且，且暂不支持的运营商";
                        }else{
							flag = true;
							comment = "不一致，但通话次数满足";
						}
					}
				}
			}

			if(flag){
				approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
				addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, comment, msg);
				return APPROVAL_ACCEPT;
			}else{
				approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
				addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, comment, msg);
				return APPROVAL_REJECT;
			}
		}catch (Exception e){
			approvalInfoBO.setExceptionCount(approvalInfoBO.getExceptionCount()+1);
			addApprovalRecord(orderId, APPROVAL_EXCEPTION, taskName, processInstanceId, "查询异常", e.getMessage());
			return APPROVAL_EXCEPTION;
		}
	}

	/**
	 * 验证用户历史登录地点是否跨省
	 * @param approvalInfoBO
	 * @param orderId
	 * @param processInstanceId
	 * @param userId 用户id
	 */
	private String historyLoginLogValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String userId) throws Exception{
		String taskName = "历史登录位置验证";
		try{
			Map<String,String> params = new HashMap<>();
			params.put("userId",userId);
			//获取历史登录省份
			String logHistoryProvince = commonHttpClient.get(bizConfig.integrationUsercenterUrlRoot+"/user/loginLog/getProvince",params);
			CommonResult result = JSON.parseObject(logHistoryProvince,CommonResult.class);
			if("0".equals(result.getCode())){
				List<Map<String,Object>> dataArray = (List<Map<String, Object>>) result.getData();
				StringBuilder extStr = new StringBuilder("");
				//历史登录位置存在跨省的情况
				if(dataArray!=null && dataArray.size()>1){
					for(Map<String,Object> map:dataArray){
						extStr.append(map.get("province").toString()+",");
					}
					approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
					addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "历史登录位置存在跨省的现象", extStr.toString());
					return APPROVAL_REJECT;
				}else{
					extStr.append(dataArray.size()==0?"无历史登录日志信息":dataArray.get(0).get("province"));
					approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
					addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, "历史登录位置在同一省份内", extStr.toString());
					return APPROVAL_ACCEPT;
				}
			}else{
				approvalInfoBO.setExceptionCount(approvalInfoBO.getExceptionCount()+1);
				addApprovalRecord(orderId, APPROVAL_EXCEPTION, taskName, processInstanceId, "查询历史登录省份信息异常", result.getMessage());
				return APPROVAL_EXCEPTION;
			}
		}catch (Exception e){
			approvalInfoBO.setExceptionCount(approvalInfoBO.getExceptionCount()+1);
			addApprovalRecord(orderId, APPROVAL_EXCEPTION, taskName, processInstanceId, "计算历史登录省份信息异常", e.getMessage());
			return  APPROVAL_EXCEPTION;
		}

	}

    /**
     *  申请行为时间段(非凌晨（1:00—7:00）)
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param createTime 订单创建时间
     */
	private String applyTimeValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String createTime) throws Exception{
	    String taskName = "申请行为时间段验证";
	    try{
			long lt = new Long(createTime);
			Date date = new Date(lt);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            String hourStr = "申请时间"+hour+"点";
            if(hour>1 && hour<7){
                approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
                addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "不在规定的时间范围之内", hourStr);
                return APPROVAL_REJECT;
            }else{
                approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
                addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, hourStr, hourStr);
                return APPROVAL_ACCEPT;
            }
        }catch (Exception e){
            approvalInfoBO.setExceptionCount(approvalInfoBO.getExceptionCount()+1);
            addApprovalRecord(orderId, APPROVAL_EXCEPTION, taskName, processInstanceId, "申请行为时间段计算异常", e.getMessage());
            return APPROVAL_EXCEPTION;
        }

    }

    /**
     * 1天内账户申请次数(<6)
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param userId 申请人ID
	 * @return 返回通过与否（通过：ACCEPT;拒绝：REJECT）
     */
    private String oneDayApplyTimesValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String userId,String createTime) throws Exception{
        String taskName = "当天账户申请次数验证";
		Calendar cdate = Calendar.getInstance();
		cdate.setTimeInMillis(Long.valueOf(createTime));
		Date date = cdate.getTime();
		Map<String,Object> param = new HashMap<>();
		param.put("userId",userId);
		param.put("createBeginTime",DateUtil.convert2String(DateUtil.setStartDay(date),DateUtil.DATETIME_FORMAT_2));
		param.put("createEndTime",DateUtil.convert2String(DateUtil.setEndDay(date),DateUtil.DATETIME_FORMAT_2));
		param.put("childProduct","mensuo");
		String msg = commonHttpClient.postBody(bizConfig.integrationOrderUrlSearchRoot+bizConfig.integrationOrderUrlcountOrder, JSON.toJSONString(param));
		CommonResult result = JSON.parseObject(msg,new TypeReference<CommonResult>(){});
		Integer count = Integer.valueOf(result.getData().toString());
		String extStr = "已申请"+count+"次";
        if(count<6){
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr, extStr);
            return APPROVAL_ACCEPT;
        }else{
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "当天内账户申请次数超限", extStr);
            return APPROVAL_REJECT;
        }
    }
    /**
     * 7天内账户申请次数(<10)
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param userId 申请人ID
     */
    private String sevenDaysApplyTimesValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String userId,String createTime) throws Exception{
        String taskName = "7天内账户申请次数验证";
		Calendar cdate = Calendar.getInstance();
		cdate.setTimeInMillis(Long.valueOf(createTime));
		Date date = cdate.getTime();
		Date bdate = DateUtil.addDays(date,-6);
		Map<String,Object> param = new HashMap<>();
		param.put("userId",userId);
		param.put("createBeginTime",DateUtil.convert2String(DateUtil.setStartDay(bdate),DateUtil.DATETIME_FORMAT_2));
		param.put("createEndTime",DateUtil.convert2String(DateUtil.setEndDay(date),DateUtil.DATETIME_FORMAT_2));
		param.put("childProduct","mensuo");
		String msg = commonHttpClient.postBody(bizConfig.integrationOrderUrlSearchRoot+bizConfig.integrationOrderUrlcountOrder, JSON.toJSONString(param));
		CommonResult result = JSON.parseObject(msg,new TypeReference<CommonResult>(){});
		Integer count = Integer.valueOf(result.getData().toString());
		String extStr = "已申请"+count+"次";
        if(count<10){
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr, extStr);
            return APPROVAL_ACCEPT;
        }else{
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "7天内账户申请次数超限", extStr);
            return APPROVAL_REJECT;
        }
    }


	/**
	 * 用户申请的地理位置居住地址是否在同一城市
	 * @param approvalInfoBO
	 * @param orderId
	 * @param processInstanceId
     * @param province 省编码
	 * @param cityCode 市编码
	 * @param lng 申请经度
	 * @param lat 申请纬度
	 */
	public String applyAddrAndUserAddrValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String province,String cityCode,String lng,String lat) throws Exception{
		String taskName = "用户申请的地理位置居住地址是否在同一城市验证";
		//查询cityCode对应得市名称
		try{
			//获取填写的城市名
			Map<String,String> param = new HashMap<>();
			param.put("code",cityCode);
			param.put("type","city");
			String region = commonHttpClient.get(bizConfig.integrationDataUrlRoot+bizConfig.integrationDataUrlRegionName,param);
			CommonResult result = JSON.parseObject(region,CommonResult.class);
			String city = (String)result.getData();
			//获取省名
            Map<String,String> paramP = new HashMap<>();
            paramP.put("code",province);
            paramP.put("type","province");
            String strP = commonHttpClient.get(bizConfig.integrationDataUrlRoot+bizConfig.integrationDataUrlRegionName,paramP);
            CommonResult resultP = JSON.parseObject(strP,CommonResult.class);
            String provinceP = (String)resultP.getData();
			//获取gps定位的城市名
			Map<String,String> geoParam = new HashMap<>();
			geoParam.put("longitude",lng);
			geoParam.put("latitude",lat);
			String geoStr = commonHttpClient.postBody(bizConfig.integrationDataresourceUrlRoot+bizConfig.integrationDataresourceUrlBaiduReverseSn,JSON.toJSONString(geoParam));
			CommonResult geoResult = JSON.parseObject(geoStr,CommonResult.class);
			if("200".equals(geoResult.getCode())){
				Map<String,Object> geoAdd = (Map<String, Object>) geoResult.getData();
				String geoCity  = "";
				String geoProvince = "";
				if("0".equals(geoAdd.get("status").toString())){
					geoCity = geoAdd.get("city").toString();
					geoProvince = geoAdd.get("province").toString();
				}
				if(MUNIC.contains(geoProvince)){
					if(provinceP.equals(geoProvince)){
						approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount() + 1);
						addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, "住址与申请地址一致", "住址与申请地址一致，位置为" + geoCity + " " + geoProvince);
						return APPROVAL_ACCEPT;
					}else{
						approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount() + 1);
						addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "住址与申请地址不一致", "获取的地理位置为" + geoCity + " " + geoProvince);
						return APPROVAL_REJECT;
					}
				}else {
					if (city.equals(geoCity) && provinceP.equals(geoProvince)) {
						approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount() + 1);
						addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, "住址与申请地址一致", "住址与申请地址一致，位置为" + geoCity + " " + geoProvince);
						return APPROVAL_ACCEPT;
					} else {
						approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount() + 1);
						addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "住址与申请地址不一致", "获取的地理位置为" + geoCity + " " + geoProvince);
						return APPROVAL_REJECT;
					}
				}
			}else{
				approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
				addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "获取申请地址失败", geoResult.getMessage());
				return APPROVAL_REJECT;
			}
		}catch (Exception e){
			approvalInfoBO.setExceptionCount(approvalInfoBO.getExceptionCount()+1);
			addApprovalRecord(orderId, "EXCEPTION", taskName, processInstanceId, "住址与申请地址比较异常", e.getMessage());
			return APPROVAL_EXCEPTION;
		}

	}

    /**
     * 同一手机号匹配申请笔数（非本人）
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param mobileNo 手机号
     * @param userId 申请人ID
     */
    private String notSelfApplyTimes(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String mobileNo,String userId) throws Exception{
        String taskName = "同一手机号匹配申请笔数（非本人）";
        Map<String,Object> param = new HashMap<>();
        param.put("mobileNo",mobileNo);
        param.put("notInUserIds",userId);
        param.put("childProduct","mensuo");
        String msg = commonHttpClient.postBody(bizConfig.integrationOrderUrlSearchRoot+bizConfig.integrationOrderUrlcountOrder, JSON.toJSONString(param));
        CommonResult result = JSON.parseObject(msg,new TypeReference<CommonResult>(){});
        Integer count = Integer.valueOf(result.getData().toString());
        String extStr = "已申请"+count+"次";
        if(count<5){
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr, extStr);
            return APPROVAL_ACCEPT;
        }else{
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "同一手机号非本人申请次数超限", extStr);
            return APPROVAL_REJECT;
        }
    }
    /**
     * 同一手机号匹配通过笔数（非本人）
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param mobileNo 手机号
     * @param userId 申请人ID
     */
    private String notSelfSuccessTimes(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String mobileNo,String userId) throws Exception{
        String taskName = "同一手机号匹配通过笔数（非本人）";
        Map<String,Object> param = new HashMap<>();
        param.put("mobileNo",mobileNo);
        param.put("notInUserIds",userId);
        param.put("childProduct","mensuo");
        param.put("notInOrderStatus","1,6,7");
        String msg = commonHttpClient.postBody(bizConfig.integrationOrderUrlSearchRoot+bizConfig.integrationOrderUrlcountOrder, JSON.toJSONString(param));
        CommonResult result = JSON.parseObject(msg,new TypeReference<CommonResult>(){});
        Integer count = Integer.valueOf(result.getData().toString());
        String extStr = "已通过"+count+"次";
        if(count<3){
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr, extStr);
            return APPROVAL_ACCEPT;
        }else{
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "同一手机号非本人通过次数超限", extStr);
            return APPROVAL_REJECT;
        }
    }

	/**
	 * 同一城市户籍匹配申请笔数（非本人）
	 * @param approvalInfoBO
	 * @param orderId
	 * @param processInstanceId
	 * @param userId 申请人ID
	 * @param province 申请人所在省代码
	 * @throws Exception
	 */
    private String notSelfSameCityApplyTimes(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String userId,String province,String city) throws Exception{
		String taskName = "同一城市户籍匹配申请笔数验证";
		Map<String,Object> param = new HashMap<>();
		param.put("notInUserIds",userId);
		param.put("childProduct","mensuo");
		param.put("censusProvince",province);
		param.put("censusCity",city);
		String msg = commonHttpClient.postBody(bizConfig.integrationOrderUrlSearchRoot+bizConfig.integrationOrderUrlcountOrder, JSON.toJSONString(param));
		CommonResult result = JSON.parseObject(msg,new TypeReference<CommonResult>(){});
		Integer count = Integer.valueOf(result.getData().toString());
		String extStr = "已申请"+count+"次";
		//特殊区域
		if(SPECIAL_PROVINCES.contains(province)){
			if(count<8){
				approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
				addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr, extStr);
				return APPROVAL_ACCEPT;
			}else{
				approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
				addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "同一城市户籍匹配申请笔数超限", extStr);
				return APPROVAL_REJECT;
			}
		}else{
			if(count<15){
				approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
				addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr, extStr);
				return APPROVAL_ACCEPT;
			}else{
				approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
				addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "同一城市户籍匹配申请笔数超限", extStr);
				return APPROVAL_REJECT;
			}
		}
	}
	/**
	 * 同一城市户籍匹配通过笔数（非本人）
	 * @param approvalInfoBO
	 * @param orderId
	 * @param processInstanceId
	 * @param userId 申请人ID
	 * @param province 申请人所在省代码
	 * @param city 申请人所在市代码
	 * @throws Exception
	 */
	private String notSelfSameCitySuccessTimes(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String userId,String province,String city) throws Exception{
		String taskName = "同一城市户籍匹配通过笔数验证";
		Map<String,Object> param = new HashMap<>();
		param.put("notInUserIds",userId);
		param.put("notInOrderStatus","1,6,7");
		param.put("childProduct","mensuo");
		param.put("censusProvince",province);
		param.put("censusCity",city);
		String msg = commonHttpClient.postBody(bizConfig.integrationOrderUrlSearchRoot+bizConfig.integrationOrderUrlcountOrder, JSON.toJSONString(param));
		CommonResult result = JSON.parseObject(msg,new TypeReference<CommonResult>(){});
		Integer count = Integer.valueOf(result.getData().toString());
		String extStr = "已通过"+count+"次";
		//特殊区域
		if(SPECIAL_PROVINCES.contains(province)){
			if(count<5){
				approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
				addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr, extStr);
				return APPROVAL_ACCEPT;
			}else{
				approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
				addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "同一城市户籍匹配通过笔数超限", extStr);
				return APPROVAL_REJECT;
			}
		}else{
			if(count<8){
				approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
				addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr, extStr);
				return APPROVAL_ACCEPT;
			}else{
				approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
				addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "同一城市户籍匹配通过笔数超限", extStr);
				return APPROVAL_REJECT;
			}
		}
	}

	/**
	 * 申请人年龄规则(20<=年龄<=60)
	 * @param approvalInfoBO
	 * @param orderId
	 * @param processInstanceId
	 * @param idNo 身份证号
	 */
	private String userAgeValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String idNo) throws Exception{
		String taskName = "申请人年龄检查";
		//根据身份证号获取出生年
		int userAge = IdNoUtil.getAge(idNo);
		String extStr = "申请人年龄"+userAge;
		if(userAge>=20 && userAge<=60){
			approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
			addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr,extStr );
			return APPROVAL_ACCEPT;
		}else{
			approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
			addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "申请年龄不在规定范围内", extStr);
			return APPROVAL_REJECT;
		}
	}

	@Override
	@Transactional
	public ApprovalResultDTO testDataCheck(MensuoDataCheckRequest request) throws Exception {
		String del_url = bizConfig.integrationWorkflowUrlRoot +"/approvalRecord/delete?orderId="+request.getOrderId();
		commonHttpClient.get(del_url,null);
		ApprovalInfoBO approvalInfoBO = new ApprovalInfoBO();
		approvalInfoBO.setAcceptCount(0);
		approvalInfoBO.setRejectCount(0);
		approvalInfoBO.setExceptionCount(0);
		String operation = "";
		boolean result = true;
		operation = applyAddrAndUserAddrValid(approvalInfoBO,request.getOrderId(),request.getProcessInstanceId(),request.getProvince(),request.getCity(),request.getLng(),request.getLat());
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//中智诚黑名单
		operation = zhongzhichengBlacklist(approvalInfoBO, request.getOrderId(), request.getProcessInstanceId(), request.getName(), request.getIdcardNo(), request.getMobile());
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//法海失信执行人
		operation = fahaiPenson(approvalInfoBO, request.getOrderId(), request.getProcessInstanceId(), request.getName(), request.getIdcardNo());
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//同盾评分核查
		operation = tongdunGetData(approvalInfoBO, request.getOrderId(), request.getProcessInstanceId(), request.getName(), request.getIdcardNo(), request.getMobile());
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//手机号码、身份证号码、姓名验证（集奥）
		operation = geoNameMobileIdnumberVerify(approvalInfoBO, request.getOrderId(), request.getProcessInstanceId(), request.getName(), request.getIdcardNo(), request.getMobile());
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//在网状态（集奥）
		operation = geoMobileStatusVerify(approvalInfoBO, request.getOrderId(), request.getProcessInstanceId(), request.getName(), request.getIdcardNo(), request.getMobile());
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//在网时长（集奥）
		operation = geoMobileValidTime(approvalInfoBO, request.getOrderId(), request.getProcessInstanceId(), request.getName(), request.getIdcardNo(), request.getMobile());
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		//联系人有效
		operation = geoNameMobileVerifyAndCallAmount(approvalInfoBO, request.getOrderId(), request.getProcessInstanceId(), request.getName(), request.getIdcardNo(), request.getMobile(),request.getFriendName1(), request.getFriendMobileNo1());
		if(!APPROVAL_ACCEPT.equals(operation)){
			result = false;
		}
		ApprovalResultDTO dto = new ApprovalResultDTO();
		String oper = result ? APPROVAL_ACCEPT:APPROVAL_REJECT;
		dto.setResult(result);
		dto.setResultStr(oper);
		String url = bizConfig.integrationWorkflowUrlRoot +"/approvalRecord/query?orderId="+request.getOrderId();
		List<ApprovalRecordPO> list = JSON.parseObject(commonHttpClient.get(url,null),new TypeReference<List<ApprovalRecordPO>>(){});
		dto.setList(list);
		log.info("mensuo风控结果："+JSON.toJSONString(dto));
		return dto;
	}
}
