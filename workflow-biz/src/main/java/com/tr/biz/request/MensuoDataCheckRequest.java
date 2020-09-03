package com.tr.biz.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 门锁测试大数据校验规则form
 */
public class MensuoDataCheckRequest {
    private String orderId = "test_mensuo_check";
    private String processInstanceId = "test_mensuo_pinstid";
    /**
     * 居住地址省
     */
    @NotBlank(message = "居住省不能为空")
    private String province;
    /**
     * 居住地址市
     */
    @NotBlank(message = "居住市不能为空")
    private String city;
    /**
     * 申请位置经度
     */
    @NotBlank(message = "申请位置经度不能为空")
    private String lng;
    /**
     * 申请位置纬度
     */
    @NotBlank(message = "申请位置纬度不能为空")
    private String lat;
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
    @NotBlank(message = "联系人姓名不能为空")
    private String friendName1;
    /**
     * 联系人1电话
     */
    @NotBlank(message = "联系人电话不能为空")
    private String friendMobileNo1;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
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

    public void setFriendName1(String friendName) {
        this.friendName1 = friendName;
    }

    public String getFriendMobileNo1() {
        return friendMobileNo1;
    }

    public void setFriendMobileNo1(String friendMobileNo1) {
        this.friendMobileNo1 = friendMobileNo1;
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
}
