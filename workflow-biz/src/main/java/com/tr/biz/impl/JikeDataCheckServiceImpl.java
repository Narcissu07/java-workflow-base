package com.tr.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tr.biz.api.JikeDataCheckService;
import com.tr.biz.bo.ApprovalInfoBO;
import com.tr.biz.dto.ApprovalResultDTO;
import com.tr.biz.dto.CommonDTO;
import com.tr.biz.dto.GeoDTO;
import com.tr.biz.request.JikeDataCheckRequest;
import com.tr.common.framework.results.CommonResult;
import com.tr.common.helper.DictHelper;
import com.tr.common.utils.IdNoUtil;
import com.tr.dal.mybatis.model.ApprovalRecordPO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 即科风控规则service
 */
@Service
public class JikeDataCheckServiceImpl extends BaseDataCheckService implements JikeDataCheckService {
    private Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 居住地址限制的省份
     */
    private static final Set<String> LIVE_EXTAREA = new HashSet<>();
    /**
     * 未通过的联系次数code集合
     */
    private static final Set<String> CALLS_NUM = new HashSet<>();
    static{
        LIVE_EXTAREA.add("新疆维吾尔自治区");
        LIVE_EXTAREA.add("西藏自治区");
        LIVE_EXTAREA.add("内蒙古自治区");
        LIVE_EXTAREA.add("青海省");
        LIVE_EXTAREA.add("黑龙江省");
        LIVE_EXTAREA.add("吉林省");
        LIVE_EXTAREA.add("辽宁省");
        LIVE_EXTAREA.add("甘肃省");
        CALLS_NUM.add("0");
        CALLS_NUM.add("1");
        CALLS_NUM.add("2");
        CALLS_NUM.add("101");
    }

    @Override
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
        //姓名
        String name = dataMap.get("name");
        //身份证
        String idNo = dataMap.get("idNo");
        //电话
        String mobileNo = dataMap.get("mobileNo");
        //联系人1电话
        String friendMobileNo1 = dataMap.get("friendMobileNo1");
        //联系人1姓名
        String friendName1 = dataMap.get("friendName1");
        //联系人2电话
        String friendMobileNo2 = dataMap.get("friendMobileNo2");
        //联系人2姓名
        String friendName2 = dataMap.get("friendName2");
        //订单金额(分)
        String account = dataMap.get("account");
        //期数
        String termNo = dataMap.get("termNo");
        //单位所在市
        String companyCity = dataMap.get("companyAddrCity");
        //住址所在市
        String addressCity = dataMap.get("addressCity");
        //住址所在省
        String addressProvince = dataMap.get("addressProvince");
        ApprovalInfoBO approvalInfoBO = new ApprovalInfoBO();
        approvalInfoBO.setAcceptCount(0);
        approvalInfoBO.setRejectCount(0);
        approvalInfoBO.setExceptionCount(0);
        String operation= "";
        operation = userAgeValid(approvalInfoBO,orderId,execution.getProcessInstanceId(),idNo);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        operation = termNoValid(approvalInfoBO,orderId,execution.getProcessInstanceId(),termNo);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        operation = accountValid(approvalInfoBO,orderId,execution.getProcessInstanceId(),account);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        operation = duplicateLoanValid(approvalInfoBO,orderId,execution.getProcessInstanceId(),idNo);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        operation = liveAddressProvinceValid(approvalInfoBO,orderId,execution.getProcessInstanceId(),addressProvince);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        operation = companyAndLiveCityValid(approvalInfoBO,orderId,execution.getProcessInstanceId(),companyCity,addressCity);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        //中智诚黑名单
        operation = zhongzhichengBlacklist(approvalInfoBO,orderId,execution.getProcessInstanceId(),name,idNo,mobileNo);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        //集奥3要素
        operation = geoNameMobileIdnumberVerify(approvalInfoBO,orderId,execution.getProcessInstanceId(),name,idNo,mobileNo);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        //手机在网状态
        operation = geoMobileStatusVerify(approvalInfoBO,orderId,execution.getProcessInstanceId(),name,idNo,mobileNo);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        //法海失信执行人
        operation = fahaiPenson(approvalInfoBO,orderId,execution.getProcessInstanceId(),name,idNo);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        //同盾分
        operation = tongdunGetData(approvalInfoBO,orderId,execution.getProcessInstanceId(),name,idNo,mobileNo);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        //联系人1通话次数校验
        operation = geoNameMobileVerifyAndCallAmount(approvalInfoBO,orderId,execution.getProcessInstanceId(),name,idNo,mobileNo,friendName1,friendMobileNo1,account);
        if(!APPROVAL_ACCEPT.equals(operation)){
            updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
            return;
        }
        //联系人2通话次数校验
        if(StringUtils.isNotEmpty(friendMobileNo2)&&StringUtils.isNotEmpty(friendMobileNo2)){
            operation = geoNameMobileVerifyAndCallAmount(approvalInfoBO,orderId,execution.getProcessInstanceId(),name,idNo,mobileNo,friendName2,friendMobileNo2,account);
            if(!APPROVAL_ACCEPT.equals(operation)){
                updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
                return;
            }
        }
        updateStateAndCommitWorkFlow(runtimeService,execution.getProcessInstanceId(),operation,orderId);
    }

    @Override
    public ApprovalResultDTO testDataCheck(JikeDataCheckRequest request) throws Exception {
        String del_url = bizConfig.integrationWorkflowUrlRoot +"/approvalRecord/delete?orderId="+request.getOrderId();
        commonHttpClient.get(del_url,null);
        ApprovalInfoBO approvalInfoBO = new ApprovalInfoBO();
        approvalInfoBO.setAcceptCount(0);
        approvalInfoBO.setRejectCount(0);
        approvalInfoBO.setExceptionCount(0);
        String operation = "";
        boolean result = true;
        operation = zhongzhichengBlacklist(approvalInfoBO,request.getOrderId(),request.getProcessInstanceId(),request.getName(),request.getIdcardNo(),request.getMobile());
        if(!APPROVAL_ACCEPT.equals(operation)){
            result = false;
        }
        operation = geoNameMobileIdnumberVerify(approvalInfoBO,request.getOrderId(),request.getProcessInstanceId(),request.getName(),request.getIdcardNo(),request.getMobile());
        if(!APPROVAL_ACCEPT.equals(operation)){
            result = false;
        }
        operation = geoMobileStatusVerify(approvalInfoBO,request.getOrderId(),request.getProcessInstanceId(),request.getName(),request.getIdcardNo(),request.getMobile());
        if(!APPROVAL_ACCEPT.equals(operation)){
            result = false;
        }
        operation = fahaiPenson(approvalInfoBO,request.getOrderId(),request.getProcessInstanceId(),request.getName(),request.getIdcardNo());
        if(!APPROVAL_ACCEPT.equals(operation)){
            result = false;
        }
        operation = tongdunGetData(approvalInfoBO,request.getOrderId(),request.getProcessInstanceId(),request.getName(),request.getIdcardNo(),request.getMobile());
        if(!APPROVAL_ACCEPT.equals(operation)){
            result = false;
        }
        operation = geoNameMobileVerifyAndCallAmount(approvalInfoBO,request.getOrderId(),request.getProcessInstanceId(),request.getName(),request.getIdcardNo(),request.getMobile(),request.getFriendName1(),request.getFriendMobileNo1(),request.getAccount());
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
        log.info("jike风控结果："+ JSON.toJSONString(dto));
        return dto;
    }
    /**
     * 申请人年龄规则(18<=年龄<=60)
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param idNo 身份证号
     */
    private String userAgeValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId, String idNo) throws Exception{
        String taskName = "申请人年龄检查";
        //根据身份证号获取出生年
        int userAge = IdNoUtil.getAge(idNo);
        String extStr = "年龄"+userAge;
        if(userAge>=18 && userAge<=60){
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr,extStr );
            return APPROVAL_ACCEPT;
        }else{
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "申请年龄不在18-60岁", extStr);
            return APPROVAL_REJECT;
        }
    }

    /**
     * 期限数验证（<=12）
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param termNo 期数code
     * @return
     */
    private String termNoValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String termNo) throws Exception{
        String taskName = "申请期限校验";
        String extStr = "";
        //将期数code转为对应的期数
        String termStr = DictHelper.getDictOtherInfoById("TERM_NO", termNo);
        if(StringUtils.isEmpty(termStr)){
            extStr = "申请期限为空";
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, extStr,extStr );
            return APPROVAL_REJECT;
        }
        Integer termInt = Integer.parseInt(termStr);
        extStr = "申请期限为"+termStr+"期";
        if(termInt<=12){
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr,extStr );
            return APPROVAL_ACCEPT;
        }else{
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "借款人申请期限大于12个月，不符合我司申请要求", extStr);
            return APPROVAL_REJECT;
        }
    }

    /**
     * 授信额度（订单金额）校验
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param account 金额
     * @return
     * @throws Exception
     */
    private String accountValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String account) throws Exception{
        String taskName = "授信额度校验";
        BigDecimal amount = new BigDecimal(account);
        //单位  分
        BigDecimal compAmount = new BigDecimal("5000000");
        String extStr = "金额为"+amount.divide(new BigDecimal(100),2, RoundingMode.HALF_DOWN);
        if(compAmount.compareTo(amount)<0){
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "单户额度超过5万，不符合我司申请要求", extStr);
            return APPROVAL_REJECT;
        }else{
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr,extStr );
            return APPROVAL_ACCEPT;
        }
    }

    /**
     * 重复借款验证
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param idNo 身份证号
     * @return
     */
    private String duplicateLoanValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId, String idNo) throws Exception{
        String taskName = "身份证号码重复校验";
        //查询同一身份证号是否有已拒绝或者在贷的订单
        Map<String,Object> params = new HashMap<>();
        params.put("idNo",idNo);
        params.put("notInOrderStatus","1,5,6");
        params.put("childProduct","jike");
        String msg = commonHttpClient.postBody(bizConfig.integrationOrderUrlSearchRoot+bizConfig.integrationOrderUrlcountOrder, JSON.toJSONString(params));
        CommonResult result = JSON.parseObject(msg,new TypeReference<CommonResult>(){});
        Integer count = Integer.valueOf(result.getData().toString());
        String extStr = "有"+count+"笔";
        if(count>0){
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "身份证号码重复，不符合我司产品要求", extStr);
            return APPROVAL_REJECT;
        }else{
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr,extStr );
            return APPROVAL_ACCEPT;
        }
    }

    /**
     * 居住地址所在省份区域检验
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param province
     * @return
     */
    private String liveAddressProvinceValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String province) throws Exception{
        String taskName = "居住地所在城市区域限制";
        String extStr = "居住所在省份为"+province;
        if(LIVE_EXTAREA.contains(province)){
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "限制准入区域，不符合准入", extStr);
            return APPROVAL_REJECT;
        }else{
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr,extStr );
            return APPROVAL_ACCEPT;
        }
    }

    /**
     * 公司和居住地所在城市是否一致验证
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param companyCity 公司所在城市
     * @param liveCity 居住城市
     * @return
     */
    private String companyAndLiveCityValid(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId,String companyCity,String liveCity) throws Exception{
        String taskName = "居住地址、单位地址一致性";
        String extStr = "居住市："+liveCity+",公司："+companyCity;
        if(StringUtils.isEmpty(liveCity) || StringUtils.isEmpty(companyCity)){
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "地址为空", extStr);
            return APPROVAL_REJECT;
        }
        if(!liveCity.equals(companyCity)){
            approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
            addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "单位地址与居住地址不一致", extStr);
            return APPROVAL_REJECT;
        }else{
            approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
            addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, extStr,extStr );
            return APPROVAL_ACCEPT;
        }
    }

    /**
     *集奥联系人二要素及通话次数规则校验
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param name 姓名
     * @param idcardNo 身份证
     * @param mobile 电话
     * @param friendName 联系人姓名
     * @param friendMobileNo 联系人电话
     * @param  account 订单金额
     * @return
     * @throws Exception
     */
    private String geoNameMobileVerifyAndCallAmount(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId, String name, String idcardNo, String mobile,String friendName, String friendMobileNo,String account) throws Exception{
        String taskName = "集奥手机号码、姓名和通话次数验证";
        BigDecimal amount = new BigDecimal(account);
        BigDecimal yuanAmount = amount.divide(new BigDecimal("100"));
        BigDecimal compAmount1 = new BigDecimal("2500000");//分
        BigDecimal compAmount2 = new BigDecimal("2000000");//分
        try{
            GeoDTO friendDTO = friendNameMobileVerify(friendName,friendMobileNo);
            String url = bizConfig.integrationDataresourceUrlRoot + bizConfig.integrationDataresourceUrlGeoCallAmount;
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("cid", mobile);
            paramMap.put("realName", name);
            paramMap.put("idNumber", idcardNo);
            paramMap.put("cid2", friendMobileNo);

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
                        //通话次数小于5
                        if(CALLS_NUM.contains(geoDTO.getData().getCode())){
                            if(compAmount1.compareTo(amount)>0){
                                flag = true;
                            }else{
                                flag = false;
                            }
                            comment = "一致，通话次数不足但金额为"+yuanAmount.toString();
                        }else if("10000002".equals(geoDTO.getData().getCode())){
                            flag = true;
                            comment = "一致，不支持的运营商";
                        }else if("10000004".equals(geoDTO.getData().getCode())){
                            flag = false;
                            comment = "一致,查无此记录";
                        }else{
                            flag = true;
                            comment = "一致，通话次数足够";
                        }
                    }
                }else{
                    if(!geoDTO.getCode().equals("200")){
                        flag = false;
                        comment = geoDTO.getMessage();
                    }else{
                        //通话次数小于5
                        if(CALLS_NUM.contains(geoDTO.getData().getCode())){
                            if(compAmount2.compareTo(amount)>0){
                                flag = true;
                            }else{
                                flag = false;
                            }
                            comment = "不一致，通话次数不足但金额为"+yuanAmount.toString();
                        }else if("10000002".equals(geoDTO.getData().getCode())){
                            flag = true;
                            comment = "不一致，不支持的运营商";
                        }else if("10000004".equals(geoDTO.getData().getCode())){
                            flag = false;
                            comment = "不一致,查无此记录";
                        }else{
                            flag = true;
                            comment = "不一致，通话次数足够";
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
     * 根据审批机构更改订单状态和提交流程
     * @param runtimeService
     * @param processInstanceId
     * @param operation 审批机构
     * @param orderId  订单id
     * @throws Exception
     */
    private void updateStateAndCommitWorkFlow(RuntimeService runtimeService,String processInstanceId,String operation,String orderId) throws Exception{
        if(APPROVAL_REJECT.equals(operation)){
            Map<String,String> paramMap = new HashMap<String, String>();
            paramMap.put("orderId", orderId);
            paramMap.put("orderStatus", "7");
            paramMap.put("operateName", "系统自动");
            String url = bizConfig.integrationOrderUrlSearchRoot + bizConfig.integrationOrderUrlOrderSaveOrder;
            commonHttpClient.postBody(url, JSON.toJSONString(paramMap));
        }
        runtimeService.setVariable(processInstanceId,"operation",operation);
    }
}
