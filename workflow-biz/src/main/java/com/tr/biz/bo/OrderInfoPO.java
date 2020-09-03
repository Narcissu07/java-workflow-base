package com.tr.biz.bo;

import javax.persistence.*;
import java.util.Date;

@Table(name = "order_info")
public class OrderInfoPO {
    /**
     * 主键自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单号
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 用户的ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 申请贷款金额
     */
    private String account;

    /**
     * 申请期限12期，24期等
     */
    @Column(name = "term_no")
    private String termNo;

    /**
     * 贷款用途
     */
    private String purpose;

    /**
     * 产品名称默认信用贷
     */
    private String product;

    /**
     * 子产品
     */
    @Column(name = "child_product")
    private String childProduct;

    /**
     * 来源，默认互联网
     */
    private String source;

    /**
     * 贷款利率
     */
    private String rate;

    /**
     * 还款方式
     */
    private String repayment;

    /**
     * 批准贷款金额
     */
    @Column(name = "risk_account")
    private String riskAccount;

    /**
     * 批准贷款期限
     */
    @Column(name = "risk_term")
    private String riskTerm;

    /**
     * 审批通过时间
     */
    @Column(name = "risk_date")
    private Date riskDate;

    /**
     * 最终贷款金额
     */
    @Column(name = "final_account")
    private String finalAccount;

    /**
     * 订单贷款金额
     */
    @Column(name = "order_account")
    private String orderAccount;

    /**
     * 绑定的银行卡
     */
    @Column(name = "bank_card_no")
    private String bankCardNo;

    /**
     * 银行卡银行名称
     */
    @Column(name = "bank_card_name")
    private String bankCardName;

    /**
     * 订单的大状态，已提交，审批中，待放款，还款中，已完成
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 订单的小状态，运营审批，风险审批，风险初审，财务审批，放款
     */
    @Column(name = "inside_order_status")
    private Integer insideOrderStatus;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    @Column(name = "id_no")
    private String idNo;

    /**
     * 注册的手机号
     */
    @Column(name = "mobile_no")
    private String mobileNo;

    /**
     * 婚姻状况，已婚，未婚，离异等
     */
    private String marry;

    /**
     * 省
     */
    private String province;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 详细城市区县
     */
    private String region;

    /**
     * 住房性质
     */
    @Column(name = "house_type")
    private String houseType;

    /**
     * 单位类型
     */
    @Column(name = "company_type")
    private String companyType;

    /**
     * 职业类型
     */
    @Column(name = "work_type")
    private String workType;

    /**
     * 是否有社保信息
     */
    @Column(name = "has_social_security")
    private String hasSocialSecurity;

    /**
     * 开户年月
     */
    @Column(name = "social_security_open_year")
    private String socialSecurityOpenYear;

    /**
     * 社保当前状态
     */
    @Column(name = "social_security_now_status")
    private String socialSecurityNowStatus;

    /**
     * 社保月缴存金额
     */
    @Column(name = "social_security_month_money")
    private String socialSecurityMonthMoney;

    /**
     * 缴存单位
     */
    @Column(name = "social_security_deposit_units")
    private String socialSecurityDepositUnits;

    /**
     * 是否有公积金
     */
    @Column(name = "has_accumulation_fund")
    private String hasAccumulationFund;

    /**
     * 公积金开户年月
     */
    @Column(name = "accumulation_fund_open_year")
    private String accumulationFundOpenYear;

    /**
     * 公积金当前状态
     */
    @Column(name = "accumulation_fund_now_status")
    private String accumulationFundNowStatus;

    /**
     * 公积金月缴存金额
     */
    @Column(name = "accumulation_fund_month_money")
    private String accumulationFundMonthMoney;

    /**
     * 公积金缴存单位
     */
    @Column(name = "accumulation_fund_deposit_units")
    private String accumulationFundDepositUnits;

    /**
     * 是否有房产
     */
    @Column(name = "has_house")
    private String hasHouse;

    /**
     * 住房地址
     */
    @Column(name = "house_address")
    private String houseAddress;

    /**
     * 住房面积
     */
    @Column(name = "house_area")
    private String houseArea;

    /**
     * 住房估值，30.00万
     */
    @Column(name = "house_value")
    private String houseValue;

    /**
     * 用户配偶姓名
     */
    @Column(name = "friend_name1")
    private String friendName1;

    /**
     * 配偶的手机号
     */
    @Column(name = "friend_mobile_no1")
    private String friendMobileNo1;

    /**
     * 关系1
     */
    @Column(name = "friend_ship1")
    private String friendShip1;

    /**
     * 关联人姓名
     */
    @Column(name = "friend_name2")
    private String friendName2;

    /**
     * 关联人的手机号
     */
    @Column(name = "friend_mobile_no2")
    private String friendMobileNo2;

    /**
     * 关系2
     */
    @Column(name = "friend_ship2")
    private String friendShip2;

    /**
     * 插入时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 扩容字段1
     */
    private String field1;

    /**
     * 扩容字段2
     */
    private String field2;

    /**
     * 扩容字段3
     */
    private String field3;

    /**
     * 消费金融合同金额
     */
    private String contract;

    /**
     * 实际放款日
     */
    @Column(name = "act_money_day")
    private String actMoneyDay;

    /**
     * 商户，本身来源0，摩尔龙1
     */
    private String tenant;

    /**
     * 放款时间
     */
    @Column(name = "issue_date")
    private Date issueDate;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 学历
     */
    private String education;

    /**
     * 家庭人数
     */
    @Column(name = "family_nums")
    private Integer familyNums;

    /**
     * 单位名称
     */
    private String company;

    /**
     * 单位地址
     */
    @Column(name = "company_addr")
    private String companyAddr;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 工作职位
     */
    private String position;

    /**
     * 是否购买五险
     */
    @Column(name = "is_buy_social_insurance")
    private String isBuySocialInsurance;

    /**
     * 发薪日
     */
    @Column(name = "salary_day")
    private String salaryDay;

    /**
     * 发放形式
     */
    @Column(name = "pay_way")
    private String payWay;

    /**
     * 月收入
     */
    @Column(name = "month_income")
    private Integer monthIncome;

    /**
     * 其他收入
     */
    @Column(name = "other_income")
    private Integer otherIncome;

    /**
     * 房产证url，多个用逗号分隔
     */
    @Column(name = "house_urls")
    private String houseUrls;

    /**
     * 性别
     */
    private String sex;

    /**
     * 户籍地址
     */
    @Column(name = "household_address")
    private String householdAddress;

    /**
     * 家庭电话
     */
    @Column(name = "house_mobile")
    private String houseMobile;

    /**
     * 身份证地址
     */
    @Column(name = "id_address")
    private String idAddress;

    /**
     * 业务地点
     */
    @Column(name = "business_address")
    private String businessAddress;

    /**
     * 客户申请日期
     */
    @Column(name = "apply_date")
    private String applyDate;

    /**
     * 放款户名
     */
    @Column(name = "merchant_name")
    private String merchantName;

    /**
     * 受托支付收款账号
     */
    @Column(name = "merchant_bank_card")
    private String merchantBankCard;

    @Column(name = "merchant_bank_card_name")
    private String merchantBankCardName;

    /**
     * 支行
     */
    @Column(name = "merchant_branch_bank")
    private String merchantBranchBank;

    /**
     * 开户行所在省
     */
    @Column(name = "merchant_bank_province")
    private String merchantBankProvince;

    /**
     * 开户行所在城市
     */
    @Column(name = "merchant_bank_city")
    private String merchantBankCity;

    /**
     * 即科的征信
     */
    @Column(name = "jike_credit_investigation")
    private String jikeCreditInvestigation;

    /**
     * 工作单位电话
     */
    @Column(name = "company_phone")
    private String companyPhone;

    /**
     * 合同名称
     */
    @Column(name = "contract_name")
    private String contractName;

    /**
     * 合同编号
     */
    @Column(name = "contract_no")
    private String contractNo;

    /**
     * 合同签订日期
     */
    @Column(name = "contract_sign_date")
    private String contractSignDate;

    /**
     * 签约地址
     */
    @Column(name = "sign_address_url")
    private String signAddressUrl;

    /**
     * 借款期限天数
     */
    @Column(name = "loanDays")
    private String loandays;

    /**
     * 每期月数
     */
    @Column(name = "month_no")
    private String monthNo;

    /**
     * 是否允许提前还款
     */
    @Column(name = "is_allow_advance_repay")
    private String isAllowAdvanceRepay;

    /**
     * 提前还款违约金率
     */
    @Column(name = "advance_repay_penalty")
    private String advanceRepayPenalty;

    /**
     * 最短借款期限
     */
    @Column(name = "most_shot_days")
    private String mostShotDays;

    /**
     * 合同签订日期
     */
    @Column(name = "contract_url")
    private String contractUrl;

    /**
     * 客户经理
     */
    @Column(name = "relation_manager")
    private String relationManager;

    /**
     * 投放区域-省
     */
    @Column(name = "put_province")
    private String putProvince;

    /**
     * 投放区域-市
     */
    @Column(name = "put_city")
    private String putCity;

    /**
     * 投放区域-区
     */
    @Column(name = "put_region")
    private String putRegion;

    /**
     * 申请订单的位置（经度）
     */
    private String lng;

    /**
     * 申请订单的位置（纬度）
     */
    private String lat;

    /**
     * 户籍所在地（省）
     */
    @Column(name = "census_province")
    private String censusProvince;

    /**
     * 户籍所在地（市）
     */
    @Column(name = "census_city")
    private String censusCity;

    /**
     * 户籍所在地（区）
     */
    @Column(name = "census_region")
    private String censusRegion;

    /**
     * 户籍详细地址
     */
    @Column(name = "census_address")
    private String censusAddress;

    /**
     * 放款方式0 对私，1对公
     */
    @Column(name = "issue_type")
    private Integer issueType;

    /**
     * 商户-对私放款身份证号
     */
    @Column(name = "merchant_id_no")
    private String merchantIdNo;

    /**
     * 工作地址所在城市
     */
    @Column(name = "company_addr_city")
    private String companyAddrCity;

    /**
     * 家庭地址所在城市
     */
    @Column(name = "address_city")
    private String addressCity;

    /**
     * 居住地址所在省
     */
    @Column(name = "address_province")
    private String addressProvince;

    /**
     * 获取主键自增ID
     *
     * @return id - 主键自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键自增ID
     *
     * @param id 主键自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取订单号
     *
     * @return order_id - 订单号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单号
     *
     * @param orderId 订单号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取用户的ID
     *
     * @return user_id - 用户的ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户的ID
     *
     * @param userId 用户的ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取申请贷款金额
     *
     * @return account - 申请贷款金额
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置申请贷款金额
     *
     * @param account 申请贷款金额
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取申请期限12期，24期等
     *
     * @return term_no - 申请期限12期，24期等
     */
    public String getTermNo() {
        return termNo;
    }

    /**
     * 设置申请期限12期，24期等
     *
     * @param termNo 申请期限12期，24期等
     */
    public void setTermNo(String termNo) {
        this.termNo = termNo == null ? null : termNo.trim();
    }

    /**
     * 获取贷款用途
     *
     * @return purpose - 贷款用途
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * 设置贷款用途
     *
     * @param purpose 贷款用途
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    /**
     * 获取产品名称默认信用贷
     *
     * @return product - 产品名称默认信用贷
     */
    public String getProduct() {
        return product;
    }

    /**
     * 设置产品名称默认信用贷
     *
     * @param product 产品名称默认信用贷
     */
    public void setProduct(String product) {
        this.product = product == null ? null : product.trim();
    }

    /**
     * 获取子产品
     *
     * @return child_product - 子产品
     */
    public String getChildProduct() {
        return childProduct;
    }

    /**
     * 设置子产品
     *
     * @param childProduct 子产品
     */
    public void setChildProduct(String childProduct) {
        this.childProduct = childProduct == null ? null : childProduct.trim();
    }

    /**
     * 获取来源，默认互联网
     *
     * @return source - 来源，默认互联网
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置来源，默认互联网
     *
     * @param source 来源，默认互联网
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * 获取贷款利率
     *
     * @return rate - 贷款利率
     */
    public String getRate() {
        return rate;
    }

    /**
     * 设置贷款利率
     *
     * @param rate 贷款利率
     */
    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    /**
     * 获取还款方式
     *
     * @return repayment - 还款方式
     */
    public String getRepayment() {
        return repayment;
    }

    /**
     * 设置还款方式
     *
     * @param repayment 还款方式
     */
    public void setRepayment(String repayment) {
        this.repayment = repayment == null ? null : repayment.trim();
    }

    /**
     * 获取批准贷款金额
     *
     * @return risk_account - 批准贷款金额
     */
    public String getRiskAccount() {
        return riskAccount;
    }

    /**
     * 设置批准贷款金额
     *
     * @param riskAccount 批准贷款金额
     */
    public void setRiskAccount(String riskAccount) {
        this.riskAccount = riskAccount == null ? null : riskAccount.trim();
    }

    /**
     * 获取批准贷款期限
     *
     * @return risk_term - 批准贷款期限
     */
    public String getRiskTerm() {
        return riskTerm;
    }

    /**
     * 设置批准贷款期限
     *
     * @param riskTerm 批准贷款期限
     */
    public void setRiskTerm(String riskTerm) {
        this.riskTerm = riskTerm == null ? null : riskTerm.trim();
    }

    /**
     * 获取审批通过时间
     *
     * @return risk_date - 审批通过时间
     */
    public Date getRiskDate() {
        return riskDate;
    }

    /**
     * 设置审批通过时间
     *
     * @param riskDate 审批通过时间
     */
    public void setRiskDate(Date riskDate) {
        this.riskDate = riskDate;
    }

    /**
     * 获取最终贷款金额
     *
     * @return final_account - 最终贷款金额
     */
    public String getFinalAccount() {
        return finalAccount;
    }

    /**
     * 设置最终贷款金额
     *
     * @param finalAccount 最终贷款金额
     */
    public void setFinalAccount(String finalAccount) {
        this.finalAccount = finalAccount == null ? null : finalAccount.trim();
    }

    /**
     * 获取订单贷款金额
     *
     * @return order_account - 订单贷款金额
     */
    public String getOrderAccount() {
        return orderAccount;
    }

    /**
     * 设置订单贷款金额
     *
     * @param orderAccount 订单贷款金额
     */
    public void setOrderAccount(String orderAccount) {
        this.orderAccount = orderAccount == null ? null : orderAccount.trim();
    }

    /**
     * 获取绑定的银行卡
     *
     * @return bank_card_no - 绑定的银行卡
     */
    public String getBankCardNo() {
        return bankCardNo;
    }

    /**
     * 设置绑定的银行卡
     *
     * @param bankCardNo 绑定的银行卡
     */
    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo == null ? null : bankCardNo.trim();
    }

    /**
     * 获取银行卡银行名称
     *
     * @return bank_card_name - 银行卡银行名称
     */
    public String getBankCardName() {
        return bankCardName;
    }

    /**
     * 设置银行卡银行名称
     *
     * @param bankCardName 银行卡银行名称
     */
    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName == null ? null : bankCardName.trim();
    }

    /**
     * 获取订单的大状态，已提交，审批中，待放款，还款中，已完成
     *
     * @return order_status - 订单的大状态，已提交，审批中，待放款，还款中，已完成
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单的大状态，已提交，审批中，待放款，还款中，已完成
     *
     * @param orderStatus 订单的大状态，已提交，审批中，待放款，还款中，已完成
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取订单的小状态，运营审批，风险审批，风险初审，财务审批，放款
     *
     * @return inside_order_status - 订单的小状态，运营审批，风险审批，风险初审，财务审批，放款
     */
    public Integer getInsideOrderStatus() {
        return insideOrderStatus;
    }

    /**
     * 设置订单的小状态，运营审批，风险审批，风险初审，财务审批，放款
     *
     * @param insideOrderStatus 订单的小状态，运营审批，风险审批，风险初审，财务审批，放款
     */
    public void setInsideOrderStatus(Integer insideOrderStatus) {
        this.insideOrderStatus = insideOrderStatus;
    }

    /**
     * 获取用户姓名
     *
     * @return name - 用户姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户姓名
     *
     * @param name 用户姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取身份证号码
     *
     * @return id_no - 身份证号码
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * 设置身份证号码
     *
     * @param idNo 身份证号码
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    /**
     * 获取注册的手机号
     *
     * @return mobile_no - 注册的手机号
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * 设置注册的手机号
     *
     * @param mobileNo 注册的手机号
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    /**
     * 获取婚姻状况，已婚，未婚，离异等
     *
     * @return marry - 婚姻状况，已婚，未婚，离异等
     */
    public String getMarry() {
        return marry;
    }

    /**
     * 设置婚姻状况，已婚，未婚，离异等
     *
     * @param marry 婚姻状况，已婚，未婚，离异等
     */
    public void setMarry(String marry) {
        this.marry = marry == null ? null : marry.trim();
    }

    /**
     * 获取省
     *
     * @return province - 省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省
     *
     * @param province 省
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取所在城市
     *
     * @return city - 所在城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置所在城市
     *
     * @param city 所在城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取详细城市区县
     *
     * @return region - 详细城市区县
     */
    public String getRegion() {
        return region;
    }

    /**
     * 设置详细城市区县
     *
     * @param region 详细城市区县
     */
    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    /**
     * 获取住房性质
     *
     * @return house_type - 住房性质
     */
    public String getHouseType() {
        return houseType;
    }

    /**
     * 设置住房性质
     *
     * @param houseType 住房性质
     */
    public void setHouseType(String houseType) {
        this.houseType = houseType == null ? null : houseType.trim();
    }

    /**
     * 获取单位类型
     *
     * @return company_type - 单位类型
     */
    public String getCompanyType() {
        return companyType;
    }

    /**
     * 设置单位类型
     *
     * @param companyType 单位类型
     */
    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    /**
     * 获取职业类型
     *
     * @return work_type - 职业类型
     */
    public String getWorkType() {
        return workType;
    }

    /**
     * 设置职业类型
     *
     * @param workType 职业类型
     */
    public void setWorkType(String workType) {
        this.workType = workType == null ? null : workType.trim();
    }

    /**
     * 获取是否有社保信息
     *
     * @return has_social_security - 是否有社保信息
     */
    public String getHasSocialSecurity() {
        return hasSocialSecurity;
    }

    /**
     * 设置是否有社保信息
     *
     * @param hasSocialSecurity 是否有社保信息
     */
    public void setHasSocialSecurity(String hasSocialSecurity) {
        this.hasSocialSecurity = hasSocialSecurity == null ? null : hasSocialSecurity.trim();
    }

    /**
     * 获取开户年月
     *
     * @return social_security_open_year - 开户年月
     */
    public String getSocialSecurityOpenYear() {
        return socialSecurityOpenYear;
    }

    /**
     * 设置开户年月
     *
     * @param socialSecurityOpenYear 开户年月
     */
    public void setSocialSecurityOpenYear(String socialSecurityOpenYear) {
        this.socialSecurityOpenYear = socialSecurityOpenYear == null ? null : socialSecurityOpenYear.trim();
    }

    /**
     * 获取社保当前状态
     *
     * @return social_security_now_status - 社保当前状态
     */
    public String getSocialSecurityNowStatus() {
        return socialSecurityNowStatus;
    }

    /**
     * 设置社保当前状态
     *
     * @param socialSecurityNowStatus 社保当前状态
     */
    public void setSocialSecurityNowStatus(String socialSecurityNowStatus) {
        this.socialSecurityNowStatus = socialSecurityNowStatus == null ? null : socialSecurityNowStatus.trim();
    }

    /**
     * 获取社保月缴存金额
     *
     * @return social_security_month_money - 社保月缴存金额
     */
    public String getSocialSecurityMonthMoney() {
        return socialSecurityMonthMoney;
    }

    /**
     * 设置社保月缴存金额
     *
     * @param socialSecurityMonthMoney 社保月缴存金额
     */
    public void setSocialSecurityMonthMoney(String socialSecurityMonthMoney) {
        this.socialSecurityMonthMoney = socialSecurityMonthMoney == null ? null : socialSecurityMonthMoney.trim();
    }

    /**
     * 获取缴存单位
     *
     * @return social_security_deposit_units - 缴存单位
     */
    public String getSocialSecurityDepositUnits() {
        return socialSecurityDepositUnits;
    }

    /**
     * 设置缴存单位
     *
     * @param socialSecurityDepositUnits 缴存单位
     */
    public void setSocialSecurityDepositUnits(String socialSecurityDepositUnits) {
        this.socialSecurityDepositUnits = socialSecurityDepositUnits == null ? null : socialSecurityDepositUnits.trim();
    }

    /**
     * 获取是否有公积金
     *
     * @return has_accumulation_fund - 是否有公积金
     */
    public String getHasAccumulationFund() {
        return hasAccumulationFund;
    }

    /**
     * 设置是否有公积金
     *
     * @param hasAccumulationFund 是否有公积金
     */
    public void setHasAccumulationFund(String hasAccumulationFund) {
        this.hasAccumulationFund = hasAccumulationFund == null ? null : hasAccumulationFund.trim();
    }

    /**
     * 获取公积金开户年月
     *
     * @return accumulation_fund_open_year - 公积金开户年月
     */
    public String getAccumulationFundOpenYear() {
        return accumulationFundOpenYear;
    }

    /**
     * 设置公积金开户年月
     *
     * @param accumulationFundOpenYear 公积金开户年月
     */
    public void setAccumulationFundOpenYear(String accumulationFundOpenYear) {
        this.accumulationFundOpenYear = accumulationFundOpenYear == null ? null : accumulationFundOpenYear.trim();
    }

    /**
     * 获取公积金当前状态
     *
     * @return accumulation_fund_now_status - 公积金当前状态
     */
    public String getAccumulationFundNowStatus() {
        return accumulationFundNowStatus;
    }

    /**
     * 设置公积金当前状态
     *
     * @param accumulationFundNowStatus 公积金当前状态
     */
    public void setAccumulationFundNowStatus(String accumulationFundNowStatus) {
        this.accumulationFundNowStatus = accumulationFundNowStatus == null ? null : accumulationFundNowStatus.trim();
    }

    /**
     * 获取公积金月缴存金额
     *
     * @return accumulation_fund_month_money - 公积金月缴存金额
     */
    public String getAccumulationFundMonthMoney() {
        return accumulationFundMonthMoney;
    }

    /**
     * 设置公积金月缴存金额
     *
     * @param accumulationFundMonthMoney 公积金月缴存金额
     */
    public void setAccumulationFundMonthMoney(String accumulationFundMonthMoney) {
        this.accumulationFundMonthMoney = accumulationFundMonthMoney == null ? null : accumulationFundMonthMoney.trim();
    }

    /**
     * 获取公积金缴存单位
     *
     * @return accumulation_fund_deposit_units - 公积金缴存单位
     */
    public String getAccumulationFundDepositUnits() {
        return accumulationFundDepositUnits;
    }

    /**
     * 设置公积金缴存单位
     *
     * @param accumulationFundDepositUnits 公积金缴存单位
     */
    public void setAccumulationFundDepositUnits(String accumulationFundDepositUnits) {
        this.accumulationFundDepositUnits = accumulationFundDepositUnits == null ? null : accumulationFundDepositUnits.trim();
    }

    /**
     * 获取是否有房产
     *
     * @return has_house - 是否有房产
     */
    public String getHasHouse() {
        return hasHouse;
    }

    /**
     * 设置是否有房产
     *
     * @param hasHouse 是否有房产
     */
    public void setHasHouse(String hasHouse) {
        this.hasHouse = hasHouse == null ? null : hasHouse.trim();
    }

    /**
     * 获取住房地址
     *
     * @return house_address - 住房地址
     */
    public String getHouseAddress() {
        return houseAddress;
    }

    /**
     * 设置住房地址
     *
     * @param houseAddress 住房地址
     */
    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress == null ? null : houseAddress.trim();
    }

    /**
     * 获取住房面积
     *
     * @return house_area - 住房面积
     */
    public String getHouseArea() {
        return houseArea;
    }

    /**
     * 设置住房面积
     *
     * @param houseArea 住房面积
     */
    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea == null ? null : houseArea.trim();
    }

    /**
     * 获取住房估值，30.00万
     *
     * @return house_value - 住房估值，30.00万
     */
    public String getHouseValue() {
        return houseValue;
    }

    /**
     * 设置住房估值，30.00万
     *
     * @param houseValue 住房估值，30.00万
     */
    public void setHouseValue(String houseValue) {
        this.houseValue = houseValue == null ? null : houseValue.trim();
    }

    /**
     * 获取用户配偶姓名
     *
     * @return friend_name1 - 用户配偶姓名
     */
    public String getFriendName1() {
        return friendName1;
    }

    /**
     * 设置用户配偶姓名
     *
     * @param friendName1 用户配偶姓名
     */
    public void setFriendName1(String friendName1) {
        this.friendName1 = friendName1 == null ? null : friendName1.trim();
    }

    /**
     * 获取配偶的手机号
     *
     * @return friend_mobile_no1 - 配偶的手机号
     */
    public String getFriendMobileNo1() {
        return friendMobileNo1;
    }

    /**
     * 设置配偶的手机号
     *
     * @param friendMobileNo1 配偶的手机号
     */
    public void setFriendMobileNo1(String friendMobileNo1) {
        this.friendMobileNo1 = friendMobileNo1 == null ? null : friendMobileNo1.trim();
    }

    /**
     * 获取关系1
     *
     * @return friend_ship1 - 关系1
     */
    public String getFriendShip1() {
        return friendShip1;
    }

    /**
     * 设置关系1
     *
     * @param friendShip1 关系1
     */
    public void setFriendShip1(String friendShip1) {
        this.friendShip1 = friendShip1 == null ? null : friendShip1.trim();
    }

    /**
     * 获取关联人姓名
     *
     * @return friend_name2 - 关联人姓名
     */
    public String getFriendName2() {
        return friendName2;
    }

    /**
     * 设置关联人姓名
     *
     * @param friendName2 关联人姓名
     */
    public void setFriendName2(String friendName2) {
        this.friendName2 = friendName2 == null ? null : friendName2.trim();
    }

    /**
     * 获取关联人的手机号
     *
     * @return friend_mobile_no2 - 关联人的手机号
     */
    public String getFriendMobileNo2() {
        return friendMobileNo2;
    }

    /**
     * 设置关联人的手机号
     *
     * @param friendMobileNo2 关联人的手机号
     */
    public void setFriendMobileNo2(String friendMobileNo2) {
        this.friendMobileNo2 = friendMobileNo2 == null ? null : friendMobileNo2.trim();
    }

    /**
     * 获取关系2
     *
     * @return friend_ship2 - 关系2
     */
    public String getFriendShip2() {
        return friendShip2;
    }

    /**
     * 设置关系2
     *
     * @param friendShip2 关系2
     */
    public void setFriendShip2(String friendShip2) {
        this.friendShip2 = friendShip2 == null ? null : friendShip2.trim();
    }

    /**
     * 获取插入时间
     *
     * @return create_time - 插入时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置插入时间
     *
     * @param createTime 插入时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取扩容字段1
     *
     * @return field1 - 扩容字段1
     */
    public String getField1() {
        return field1;
    }

    /**
     * 设置扩容字段1
     *
     * @param field1 扩容字段1
     */
    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    /**
     * 获取扩容字段2
     *
     * @return field2 - 扩容字段2
     */
    public String getField2() {
        return field2;
    }

    /**
     * 设置扩容字段2
     *
     * @param field2 扩容字段2
     */
    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    /**
     * 获取扩容字段3
     *
     * @return field3 - 扩容字段3
     */
    public String getField3() {
        return field3;
    }

    /**
     * 设置扩容字段3
     *
     * @param field3 扩容字段3
     */
    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    /**
     * 获取消费金融合同金额
     *
     * @return contract - 消费金融合同金额
     */
    public String getContract() {
        return contract;
    }

    /**
     * 设置消费金融合同金额
     *
     * @param contract 消费金融合同金额
     */
    public void setContract(String contract) {
        this.contract = contract == null ? null : contract.trim();
    }

    /**
     * 获取实际放款日
     *
     * @return act_money_day - 实际放款日
     */
    public String getActMoneyDay() {
        return actMoneyDay;
    }

    /**
     * 设置实际放款日
     *
     * @param actMoneyDay 实际放款日
     */
    public void setActMoneyDay(String actMoneyDay) {
        this.actMoneyDay = actMoneyDay == null ? null : actMoneyDay.trim();
    }

    /**
     * 获取商户，本身来源0，摩尔龙1
     *
     * @return tenant - 商户，本身来源0，摩尔龙1
     */
    public String getTenant() {
        return tenant;
    }

    /**
     * 设置商户，本身来源0，摩尔龙1
     *
     * @param tenant 商户，本身来源0，摩尔龙1
     */
    public void setTenant(String tenant) {
        this.tenant = tenant == null ? null : tenant.trim();
    }

    /**
     * 获取放款时间
     *
     * @return issue_date - 放款时间
     */
    public Date getIssueDate() {
        return issueDate;
    }

    /**
     * 设置放款时间
     *
     * @param issueDate 放款时间
     */
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取学历
     *
     * @return education - 学历
     */
    public String getEducation() {
        return education;
    }

    /**
     * 设置学历
     *
     * @param education 学历
     */
    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    /**
     * 获取家庭人数
     *
     * @return family_nums - 家庭人数
     */
    public Integer getFamilyNums() {
        return familyNums;
    }

    /**
     * 设置家庭人数
     *
     * @param familyNums 家庭人数
     */
    public void setFamilyNums(Integer familyNums) {
        this.familyNums = familyNums;
    }

    /**
     * 获取单位名称
     *
     * @return company - 单位名称
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置单位名称
     *
     * @param company 单位名称
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    /**
     * 获取单位地址
     *
     * @return company_addr - 单位地址
     */
    public String getCompanyAddr() {
        return companyAddr;
    }

    /**
     * 设置单位地址
     *
     * @param companyAddr 单位地址
     */
    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr == null ? null : companyAddr.trim();
    }

    /**
     * 获取所属行业
     *
     * @return industry - 所属行业
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 设置所属行业
     *
     * @param industry 所属行业
     */
    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    /**
     * 获取工作职位
     *
     * @return position - 工作职位
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置工作职位
     *
     * @param position 工作职位
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * 获取是否购买五险
     *
     * @return is_buy_social_insurance - 是否购买五险
     */
    public String getIsBuySocialInsurance() {
        return isBuySocialInsurance;
    }

    /**
     * 设置是否购买五险
     *
     * @param isBuySocialInsurance 是否购买五险
     */
    public void setIsBuySocialInsurance(String isBuySocialInsurance) {
        this.isBuySocialInsurance = isBuySocialInsurance == null ? null : isBuySocialInsurance.trim();
    }

    /**
     * 获取发薪日
     *
     * @return salary_day - 发薪日
     */
    public String getSalaryDay() {
        return salaryDay;
    }

    /**
     * 设置发薪日
     *
     * @param salaryDay 发薪日
     */
    public void setSalaryDay(String salaryDay) {
        this.salaryDay = salaryDay == null ? null : salaryDay.trim();
    }

    /**
     * 获取发放形式
     *
     * @return pay_way - 发放形式
     */
    public String getPayWay() {
        return payWay;
    }

    /**
     * 设置发放形式
     *
     * @param payWay 发放形式
     */
    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    /**
     * 获取月收入
     *
     * @return month_income - 月收入
     */
    public Integer getMonthIncome() {
        return monthIncome;
    }

    /**
     * 设置月收入
     *
     * @param monthIncome 月收入
     */
    public void setMonthIncome(Integer monthIncome) {
        this.monthIncome = monthIncome;
    }

    /**
     * 获取其他收入
     *
     * @return other_income - 其他收入
     */
    public Integer getOtherIncome() {
        return otherIncome;
    }

    /**
     * 设置其他收入
     *
     * @param otherIncome 其他收入
     */
    public void setOtherIncome(Integer otherIncome) {
        this.otherIncome = otherIncome;
    }

    /**
     * 获取房产证url，多个用逗号分隔
     *
     * @return house_urls - 房产证url，多个用逗号分隔
     */
    public String getHouseUrls() {
        return houseUrls;
    }

    /**
     * 设置房产证url，多个用逗号分隔
     *
     * @param houseUrls 房产证url，多个用逗号分隔
     */
    public void setHouseUrls(String houseUrls) {
        this.houseUrls = houseUrls == null ? null : houseUrls.trim();
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 获取户籍地址
     *
     * @return household_address - 户籍地址
     */
    public String getHouseholdAddress() {
        return householdAddress;
    }

    /**
     * 设置户籍地址
     *
     * @param householdAddress 户籍地址
     */
    public void setHouseholdAddress(String householdAddress) {
        this.householdAddress = householdAddress == null ? null : householdAddress.trim();
    }

    /**
     * 获取家庭电话
     *
     * @return house_mobile - 家庭电话
     */
    public String getHouseMobile() {
        return houseMobile;
    }

    /**
     * 设置家庭电话
     *
     * @param houseMobile 家庭电话
     */
    public void setHouseMobile(String houseMobile) {
        this.houseMobile = houseMobile == null ? null : houseMobile.trim();
    }

    /**
     * 获取身份证地址
     *
     * @return id_address - 身份证地址
     */
    public String getIdAddress() {
        return idAddress;
    }

    /**
     * 设置身份证地址
     *
     * @param idAddress 身份证地址
     */
    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress == null ? null : idAddress.trim();
    }

    /**
     * 获取业务地点
     *
     * @return business_address - 业务地点
     */
    public String getBusinessAddress() {
        return businessAddress;
    }

    /**
     * 设置业务地点
     *
     * @param businessAddress 业务地点
     */
    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress == null ? null : businessAddress.trim();
    }

    /**
     * 获取客户申请日期
     *
     * @return apply_date - 客户申请日期
     */
    public String getApplyDate() {
        return applyDate;
    }

    /**
     * 设置客户申请日期
     *
     * @param applyDate 客户申请日期
     */
    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate == null ? null : applyDate.trim();
    }

    /**
     * 获取放款户名
     *
     * @return merchant_name - 放款户名
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * 设置放款户名
     *
     * @param merchantName 放款户名
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    /**
     * 获取受托支付收款账号
     *
     * @return merchant_bank_card - 受托支付收款账号
     */
    public String getMerchantBankCard() {
        return merchantBankCard;
    }

    /**
     * 设置受托支付收款账号
     *
     * @param merchantBankCard 受托支付收款账号
     */
    public void setMerchantBankCard(String merchantBankCard) {
        this.merchantBankCard = merchantBankCard == null ? null : merchantBankCard.trim();
    }

    /**
     * @return merchant_bank_card_name
     */
    public String getMerchantBankCardName() {
        return merchantBankCardName;
    }

    /**
     * @param merchantBankCardName
     */
    public void setMerchantBankCardName(String merchantBankCardName) {
        this.merchantBankCardName = merchantBankCardName == null ? null : merchantBankCardName.trim();
    }

    /**
     * 获取支行
     *
     * @return merchant_branch_bank - 支行
     */
    public String getMerchantBranchBank() {
        return merchantBranchBank;
    }

    /**
     * 设置支行
     *
     * @param merchantBranchBank 支行
     */
    public void setMerchantBranchBank(String merchantBranchBank) {
        this.merchantBranchBank = merchantBranchBank == null ? null : merchantBranchBank.trim();
    }

    /**
     * 获取开户行所在省
     *
     * @return merchant_bank_province - 开户行所在省
     */
    public String getMerchantBankProvince() {
        return merchantBankProvince;
    }

    /**
     * 设置开户行所在省
     *
     * @param merchantBankProvince 开户行所在省
     */
    public void setMerchantBankProvince(String merchantBankProvince) {
        this.merchantBankProvince = merchantBankProvince == null ? null : merchantBankProvince.trim();
    }

    /**
     * 获取开户行所在城市
     *
     * @return merchant_bank_city - 开户行所在城市
     */
    public String getMerchantBankCity() {
        return merchantBankCity;
    }

    /**
     * 设置开户行所在城市
     *
     * @param merchantBankCity 开户行所在城市
     */
    public void setMerchantBankCity(String merchantBankCity) {
        this.merchantBankCity = merchantBankCity == null ? null : merchantBankCity.trim();
    }

    /**
     * 获取即科的征信
     *
     * @return jike_credit_investigation - 即科的征信
     */
    public String getJikeCreditInvestigation() {
        return jikeCreditInvestigation;
    }

    /**
     * 设置即科的征信
     *
     * @param jikeCreditInvestigation 即科的征信
     */
    public void setJikeCreditInvestigation(String jikeCreditInvestigation) {
        this.jikeCreditInvestigation = jikeCreditInvestigation == null ? null : jikeCreditInvestigation.trim();
    }

    /**
     * 获取工作单位电话
     *
     * @return company_phone - 工作单位电话
     */
    public String getCompanyPhone() {
        return companyPhone;
    }

    /**
     * 设置工作单位电话
     *
     * @param companyPhone 工作单位电话
     */
    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone == null ? null : companyPhone.trim();
    }

    /**
     * 获取合同名称
     *
     * @return contract_name - 合同名称
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * 设置合同名称
     *
     * @param contractName 合同名称
     */
    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    /**
     * 获取合同编号
     *
     * @return contract_no - 合同编号
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 设置合同编号
     *
     * @param contractNo 合同编号
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    /**
     * 获取合同签订日期
     *
     * @return contract_sign_date - 合同签订日期
     */
    public String getContractSignDate() {
        return contractSignDate;
    }

    /**
     * 设置合同签订日期
     *
     * @param contractSignDate 合同签订日期
     */
    public void setContractSignDate(String contractSignDate) {
        this.contractSignDate = contractSignDate == null ? null : contractSignDate.trim();
    }

    /**
     * 获取签约地址
     *
     * @return sign_address_url - 签约地址
     */
    public String getSignAddressUrl() {
        return signAddressUrl;
    }

    /**
     * 设置签约地址
     *
     * @param signAddressUrl 签约地址
     */
    public void setSignAddressUrl(String signAddressUrl) {
        this.signAddressUrl = signAddressUrl == null ? null : signAddressUrl.trim();
    }

    /**
     * 获取借款期限天数
     *
     * @return loanDays - 借款期限天数
     */
    public String getLoandays() {
        return loandays;
    }

    /**
     * 设置借款期限天数
     *
     * @param loandays 借款期限天数
     */
    public void setLoandays(String loandays) {
        this.loandays = loandays == null ? null : loandays.trim();
    }

    /**
     * 获取每期月数
     *
     * @return month_no - 每期月数
     */
    public String getMonthNo() {
        return monthNo;
    }

    /**
     * 设置每期月数
     *
     * @param monthNo 每期月数
     */
    public void setMonthNo(String monthNo) {
        this.monthNo = monthNo == null ? null : monthNo.trim();
    }

    /**
     * 获取是否允许提前还款
     *
     * @return is_allow_advance_repay - 是否允许提前还款
     */
    public String getIsAllowAdvanceRepay() {
        return isAllowAdvanceRepay;
    }

    /**
     * 设置是否允许提前还款
     *
     * @param isAllowAdvanceRepay 是否允许提前还款
     */
    public void setIsAllowAdvanceRepay(String isAllowAdvanceRepay) {
        this.isAllowAdvanceRepay = isAllowAdvanceRepay == null ? null : isAllowAdvanceRepay.trim();
    }

    /**
     * 获取提前还款违约金率
     *
     * @return advance_repay_penalty - 提前还款违约金率
     */
    public String getAdvanceRepayPenalty() {
        return advanceRepayPenalty;
    }

    /**
     * 设置提前还款违约金率
     *
     * @param advanceRepayPenalty 提前还款违约金率
     */
    public void setAdvanceRepayPenalty(String advanceRepayPenalty) {
        this.advanceRepayPenalty = advanceRepayPenalty == null ? null : advanceRepayPenalty.trim();
    }

    /**
     * 获取最短借款期限
     *
     * @return most_shot_days - 最短借款期限
     */
    public String getMostShotDays() {
        return mostShotDays;
    }

    /**
     * 设置最短借款期限
     *
     * @param mostShotDays 最短借款期限
     */
    public void setMostShotDays(String mostShotDays) {
        this.mostShotDays = mostShotDays == null ? null : mostShotDays.trim();
    }

    /**
     * 获取合同签订日期
     *
     * @return contract_url - 合同签订日期
     */
    public String getContractUrl() {
        return contractUrl;
    }

    /**
     * 设置合同签订日期
     *
     * @param contractUrl 合同签订日期
     */
    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl == null ? null : contractUrl.trim();
    }

    /**
     * 获取客户经理
     *
     * @return relation_manager - 客户经理
     */
    public String getRelationManager() {
        return relationManager;
    }

    /**
     * 设置客户经理
     *
     * @param relationManager 客户经理
     */
    public void setRelationManager(String relationManager) {
        this.relationManager = relationManager == null ? null : relationManager.trim();
    }

    /**
     * 获取投放区域-省
     *
     * @return put_province - 投放区域-省
     */
    public String getPutProvince() {
        return putProvince;
    }

    /**
     * 设置投放区域-省
     *
     * @param putProvince 投放区域-省
     */
    public void setPutProvince(String putProvince) {
        this.putProvince = putProvince == null ? null : putProvince.trim();
    }

    /**
     * 获取投放区域-市
     *
     * @return put_city - 投放区域-市
     */
    public String getPutCity() {
        return putCity;
    }

    /**
     * 设置投放区域-市
     *
     * @param putCity 投放区域-市
     */
    public void setPutCity(String putCity) {
        this.putCity = putCity == null ? null : putCity.trim();
    }

    /**
     * 获取投放区域-区
     *
     * @return put_region - 投放区域-区
     */
    public String getPutRegion() {
        return putRegion;
    }

    /**
     * 设置投放区域-区
     *
     * @param putRegion 投放区域-区
     */
    public void setPutRegion(String putRegion) {
        this.putRegion = putRegion == null ? null : putRegion.trim();
    }

    /**
     * 获取申请订单的位置（经度）
     *
     * @return lng - 申请订单的位置（经度）
     */
    public String getLng() {
        return lng;
    }

    /**
     * 设置申请订单的位置（经度）
     *
     * @param lng 申请订单的位置（经度）
     */
    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    /**
     * 获取申请订单的位置（纬度）
     *
     * @return lat - 申请订单的位置（纬度）
     */
    public String getLat() {
        return lat;
    }

    /**
     * 设置申请订单的位置（纬度）
     *
     * @param lat 申请订单的位置（纬度）
     */
    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    /**
     * 获取户籍所在地（省）
     *
     * @return census_province - 户籍所在地（省）
     */
    public String getCensusProvince() {
        return censusProvince;
    }

    /**
     * 设置户籍所在地（省）
     *
     * @param censusProvince 户籍所在地（省）
     */
    public void setCensusProvince(String censusProvince) {
        this.censusProvince = censusProvince == null ? null : censusProvince.trim();
    }

    /**
     * 获取户籍所在地（市）
     *
     * @return census_city - 户籍所在地（市）
     */
    public String getCensusCity() {
        return censusCity;
    }

    /**
     * 设置户籍所在地（市）
     *
     * @param censusCity 户籍所在地（市）
     */
    public void setCensusCity(String censusCity) {
        this.censusCity = censusCity == null ? null : censusCity.trim();
    }

    /**
     * 获取户籍所在地（区）
     *
     * @return census_region - 户籍所在地（区）
     */
    public String getCensusRegion() {
        return censusRegion;
    }

    /**
     * 设置户籍所在地（区）
     *
     * @param censusRegion 户籍所在地（区）
     */
    public void setCensusRegion(String censusRegion) {
        this.censusRegion = censusRegion == null ? null : censusRegion.trim();
    }

    /**
     * 获取户籍详细地址
     *
     * @return census_address - 户籍详细地址
     */
    public String getCensusAddress() {
        return censusAddress;
    }

    /**
     * 设置户籍详细地址
     *
     * @param censusAddress 户籍详细地址
     */
    public void setCensusAddress(String censusAddress) {
        this.censusAddress = censusAddress == null ? null : censusAddress.trim();
    }

    /**
     * 获取放款方式0 对私，1对公
     *
     * @return issue_type - 放款方式0 对私，1对公
     */
    public Integer getIssueType() {
        return issueType;
    }

    /**
     * 设置放款方式0 对私，1对公
     *
     * @param issueType 放款方式0 对私，1对公
     */
    public void setIssueType(Integer issueType) {
        this.issueType = issueType;
    }

    /**
     * 获取商户-对私放款身份证号
     *
     * @return merchant_id_no - 商户-对私放款身份证号
     */
    public String getMerchantIdNo() {
        return merchantIdNo;
    }

    /**
     * 设置商户-对私放款身份证号
     *
     * @param merchantIdNo 商户-对私放款身份证号
     */
    public void setMerchantIdNo(String merchantIdNo) {
        this.merchantIdNo = merchantIdNo == null ? null : merchantIdNo.trim();
    }

    /**
     * 获取工作地址所在城市
     *
     * @return company_addr_city - 工作地址所在城市
     */
    public String getCompanyAddrCity() {
        return companyAddrCity;
    }

    /**
     * 设置工作地址所在城市
     *
     * @param companyAddrCity 工作地址所在城市
     */
    public void setCompanyAddrCity(String companyAddrCity) {
        this.companyAddrCity = companyAddrCity == null ? null : companyAddrCity.trim();
    }

    /**
     * 获取家庭地址所在城市
     *
     * @return address_city - 家庭地址所在城市
     */
    public String getAddressCity() {
        return addressCity;
    }

    /**
     * 设置家庭地址所在城市
     *
     * @param addressCity 家庭地址所在城市
     */
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity == null ? null : addressCity.trim();
    }

    /**
     * 获取居住地址所在省
     *
     * @return address_province - 居住地址所在省
     */
    public String getAddressProvince() {
        return addressProvince;
    }

    /**
     * 设置居住地址所在省
     *
     * @param addressProvince 居住地址所在省
     */
    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince == null ? null : addressProvince.trim();
    }
}