package com.tr.biz.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 即科测试大数据校验规则form
 */
public class JikeDataCheckRequest {
    private String orderId = "test_jike_check";
    private String processInstanceId = "test_jike_pinstid";
    /**
     * 申请人名字
     */
    @NotBlank(message = "申请人姓名不能为空")
    private String name;
    /**
     * 申请人电话
     */
    @NotBlank(message = "申请人电话不能为空")
    private String mobile;
    /**
     * 申请人身份证
     */
    @NotBlank(message = "身份证号码不能为空")
    private String idcardNo;
    /**
     * 联系人1姓名
     */
    @NotBlank(message = "联系人1姓名不能为空")
    private String friendName1;
    /**
     * 联系人1电话
     */
    @NotBlank(message = "联系人1电话不能为空")
    private String friendMobileNo1;
    /**
     * 联系人1姓名
     */
    @NotBlank(message = "联系人2姓名不能为空")
    private String friendName2;
    /**
     * 联系人1电话
     */
    @NotBlank(message = "联系人2电话不能为空")
    private String friendMobileNo2;
    /**
     * 订单金额
     */
    @NotBlank(message = "订单金额不能为空")
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getFriendName1() {
        return friendName1;
    }

    public void setFriendName1(String friendName1) {
        this.friendName1 = friendName1;
    }

    public String getFriendMobileNo1() {
        return friendMobileNo1;
    }

    public void setFriendMobileNo1(String friendMobileNo1) {
        this.friendMobileNo1 = friendMobileNo1;
    }

    public String getFriendName2() {
        return friendName2;
    }

    public void setFriendName2(String friendName2) {
        this.friendName2 = friendName2;
    }

    public String getFriendMobileNo2() {
        return friendMobileNo2;
    }

    public void setFriendMobileNo2(String friendMobileNo2) {
        this.friendMobileNo2 = friendMobileNo2;
    }
}
