package com.tr.biz.impl;

import com.alibaba.fastjson.JSON;
import com.tr.biz.bo.ApprovalInfoBO;
import com.tr.biz.dto.*;
import com.tr.common.config.BizConfig;
import com.tr.common.framework.api.CommonHttpClient;
import com.tr.common.framework.results.CommonResult;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaseDataCheckService {
    @Resource
    protected CommonHttpClient commonHttpClient;
    @Resource
    protected BizConfig bizConfig;
    public static final String APPROVAL_ACCEPT = "ACCEPT";
    public static final String APPROVAL_REJECT = "REJECT";
    public static final String APPROVAL_EXCEPTION = "EXCEPTION";
    /**
     * 直辖市
     */
    public static Set<String> MUNIC = new HashSet<String>();
    static{
        MUNIC.add("重庆市");
        MUNIC.add("北京市");
        MUNIC.add("天津市");
        MUNIC.add("上海市");
    }
    protected void addApprovalRecord(String orderId, String operation, String taskName, String processInstanceId, String comment, String externalResponse) throws Exception{
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("orderId", orderId);
        paramMap.put("operateName", "系统自动");
        paramMap.put("operation", operation);
        paramMap.put("taskId", "-1");
        paramMap.put("taskName", taskName);
        paramMap.put("processInstanceId", processInstanceId);
        paramMap.put("comment", comment);
        paramMap.put("externalResponse", externalResponse);
        String url = bizConfig.integrationWorkflowUrlRoot + bizConfig.integrationWorkflowUrlApprovalRecordAdd;
        String param = JSON.toJSONString(paramMap);
        commonHttpClient.postBody(url, param);
    }

    /**
     * 中智诚黑名单
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param name 姓名
     * @param idcardNo 身份证
     * @param mobile 电话
     * @return
     * @throws Exception
     */
    protected String zhongzhichengBlacklist(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId, String name, String idcardNo, String mobile) throws Exception{
        String taskName = "中智诚黑名单查询";
        try{
            String url = bizConfig.integrationDataresourceUrlRoot + bizConfig.integrationDataresourceUrlZhongzhichengBlacklist;
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("name", name);
            paramMap.put("idcardNo", idcardNo);
            paramMap.put("mobile", mobile);
            String param = JSON.toJSONString(paramMap);
            String msg = commonHttpClient.postBody(url, param);
            ZhongzhichengBlacklistDTO zhongzhichengBlacklistDTO = JSON.parseObject(msg, ZhongzhichengBlacklistDTO.class);
            ZhongzhichengBlacklistRO zhongzhichengBlacklistRO = zhongzhichengBlacklistDTO.getData();
            String homePhoneBlacklist = zhongzhichengBlacklistRO.getBlacklist().get("home_phone").get("blackLevel");
            String homeAddressBlacklist = zhongzhichengBlacklistRO.getBlacklist().get("home_address").get("blackLevel");
            String mobileBlacklist = zhongzhichengBlacklistRO.getBlacklist().get("mobile").get("blackLevel");
            String pidBlacklist = zhongzhichengBlacklistRO.getBlacklist().get("pid").get("blackLevel");
            String deviceIdBlacklist = zhongzhichengBlacklistRO.getBlacklist().get("deviceId").get("blackLevel");
            boolean flag = homePhoneBlacklist.equals("none")&&homeAddressBlacklist.equals("none")&&mobileBlacklist.equals("none")&&pidBlacklist.equals("none")&&deviceIdBlacklist.equals("none");
            if(flag){
                approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
                addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, "通过", msg);
                return APPROVAL_ACCEPT;
            }else{
                approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
                addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "命中", msg);
                return APPROVAL_REJECT;
            }
        }catch (Exception e){
            approvalInfoBO.setExceptionCount(approvalInfoBO.getExceptionCount()+1);
            addApprovalRecord(orderId, APPROVAL_EXCEPTION, taskName, processInstanceId, "查询异常", e.getMessage());
            return APPROVAL_EXCEPTION;
        }
    }
    /**
     * 集奥3要素
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param name 姓名
     * @param idcardNo 身份证
     * @param mobile  电话
     * @return
     * @throws Exception
     */
    protected String geoNameMobileIdnumberVerify(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId, String name, String idcardNo, String mobile) throws Exception{
        String taskName = "集奥手机号码、身份证号码、姓名验证";
        try{
            String url = bizConfig.integrationDataresourceUrlRoot + bizConfig.integrationDataresourceUrlGeoNameMobileIdnumberVerify;
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
            boolean flag = false;
            if(code.equals("0")){
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
    /**
     * 集奥手机在网状态验证
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param name 姓名
     * @param idcardNo 身份证
     * @param mobile 手机号
     * @return
     * @throws Exception
     */
    protected String geoMobileStatusVerify(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId, String name, String idcardNo, String mobile) throws Exception{
        String taskName = "集奥在线状态查询";
        try{
            String url = bizConfig.integrationDataresourceUrlRoot + bizConfig.integrationDataresourceUrlGeoMobileStatusVerify;
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
            boolean flag = false;
            if(code.equals("0")){
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
    /**
     * 法海失信执行人
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param name 姓名
     * @param idcardNo 身份证
     * @return
     * @throws Exception
     */
    protected String fahaiPenson(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId, String name, String idcardNo) throws Exception{
        String taskName = "法海涉诉查询";
        try{
            String url = bizConfig.integrationDataresourceUrlRoot + bizConfig.integrationDataresourceUrlFahaiPerson;
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("name", name);
            paramMap.put("idcardNo", idcardNo);
            String param = JSON.toJSONString(paramMap);
            String msg = commonHttpClient.postBody(url, param);
            FahaiPersonDTO fahaiPersonDTO = JSON.parseObject(msg, FahaiPersonDTO.class);

            FahaiPersonRO fahaiPersonRO = fahaiPersonDTO.getData();
            Integer count = Integer.valueOf(fahaiPersonRO.getCount());
            boolean flag = false;
            if(count==0){
                flag = true;
            }
            if(flag){
                approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
                addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, "通过", msg);
                return APPROVAL_ACCEPT;
            }else{
                approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
                addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, "命中", msg);
                return APPROVAL_REJECT;
            }
        }catch (Exception e){
            approvalInfoBO.setExceptionCount(approvalInfoBO.getExceptionCount()+1);
            addApprovalRecord(orderId, APPROVAL_EXCEPTION, taskName, processInstanceId, "查询异常", e.getMessage());
            return APPROVAL_EXCEPTION;
        }
    }
    /**
     * 同盾分查询
     * @param approvalInfoBO
     * @param orderId
     * @param processInstanceId
     * @param name 姓名
     * @param idcardNo 身份证
     * @param mobile 电话
     * @return
     * @throws Exception
     */
    protected String tongdunGetData(ApprovalInfoBO approvalInfoBO, String orderId, String processInstanceId, String name, String idcardNo, String mobile) throws Exception{
        String taskName = "同盾分查询";
        try{
            String url = bizConfig.integrationDataresourceUrlRoot + bizConfig.integrationDataresourceUrlTongdunGetData;
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("account_name", name);
            paramMap.put("id_number", idcardNo);
            paramMap.put("account_mobile", mobile);
            String param = JSON.toJSONString(paramMap);
            String msg = commonHttpClient.postBody(url, param);
            CommonResult result = JSON.parseObject(msg, CommonResult.class);
            Integer score = (Integer)result.getData();
            boolean flag = false;
            if(score<80){
                flag = true;
            }
            if(flag){
                approvalInfoBO.setAcceptCount(approvalInfoBO.getAcceptCount()+1);
                addApprovalRecord(orderId, APPROVAL_ACCEPT, taskName, processInstanceId, String.valueOf(score), msg);
                return APPROVAL_ACCEPT;
            }else{
                approvalInfoBO.setRejectCount(approvalInfoBO.getRejectCount()+1);
                addApprovalRecord(orderId, APPROVAL_REJECT, taskName, processInstanceId, String.valueOf(score), msg);
                return APPROVAL_REJECT;
            }
        }catch (Exception e){
            approvalInfoBO.setExceptionCount(approvalInfoBO.getExceptionCount()+1);
            addApprovalRecord(orderId, APPROVAL_EXCEPTION, taskName, processInstanceId, "查询异常", e.getMessage());
            return APPROVAL_EXCEPTION;
        }
    }

    /**
     * 获取联系人二要素验证
     * @param friendName 姓名
     * @param mobile 电话
     * @return
     */
    protected GeoDTO friendNameMobileVerify(String friendName,String mobile) throws  Exception{
        String url = bizConfig.integrationDataresourceUrlRoot + bizConfig.integrationDataresourceUrlGeoNameMobileVerify;
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("cid", mobile);
        paramMap.put("realName", friendName);
        String param = JSON.toJSONString(paramMap);
        String msg = commonHttpClient.postBody(url, param);
        GeoDTO geoDTO = JSON.parseObject(msg, GeoDTO.class);
        return geoDTO;
    }
}
