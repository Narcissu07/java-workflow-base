package com.tr.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2017/10/13.
 */
@Configuration
public class BizConfig {
    @Value("${integration.permission.url.root}")
    public String permissionUrlRoot;
    @Value("${integration.order.url.root}")
    public String integrationOrderUrlSearchRoot;

    @Value("${integration.weixin.url.root}")
    public String integrationWeixinUrlRoot;

    @Value("${integration.usercenter.url.root}")
    public String integrationUsercenterUrlRoot;

    @Value("${integration.account.url.root}")
    public String integrationAccountUrlRoot;

    @Value("${integration.data.url.root}")
    public String integrationDataUrlRoot;

    @Value("${integration.workflow.url.root}")
    public String integrationWorkflowUrlRoot;

    @Value("${integration.payment.url.root}")
    public String integrationPaymentUrlRoot;

    @Value("${integration.dataresource.url.root}")
    public String integrationDataresourceUrlRoot;

    @Value("${integration.institution.url.root}")
    public String integrationInstitutionUrlRoot;

    @Value("${integration.order.url.findOrderById}")
    public String integrationOrderUrlFindOrderById;

    @Value("${integration.order.url.findOrderByIds}")
    public String integrationOrderUrlFindOrderByIds;

    @Value("${integration.order.url.order.saveOrder}")
    public String integrationOrderUrlOrderSaveOrder;

    @Value("${integration.order.url.commodity.getCommodityOrder}")
    public String integrationOrderUrlCommodityGetCommodityOrder;

    @Value("${integration.order.url.commodity.saveCommodityOrder}")
    public String integrationOrderUrlCommoditySaveCommodityOrder;

    @Value("${integration.order.url.commodity.commodityOrderListPageByOrderIds}")
    public String integrationOrderUrlCommodityCommodityOrderListPageByOrderIds;

    @Value("${integration.weixin.url.push.order.approved}")
    public String integrationWeixinUrlPushOrderApproved;

    @Value("${integration.weixin.url.push.order.deny}")
    public String integrationWeixinUrlPushOrderDeny;

    @Value("${integration.usercenter.url.user.findUser}")
    public String integrationUsercenterUrlUserFindUser;

    @Value("${integration.usercenter.url.user.findInfo}")
    public String integrationUsercenterUrlUserFindInfo;

    @Value("${integration.usercenter.url.user.findInfoList}")
    public String integrationUsercenterUrlUserInfoList;

    @Value("${integration.account.url.account.init}")
    public String integrationAccountUrlAccountInit;

    @Value("${integration.account.url.account.initMensuo}")
    public String integrationAccountUrlAccountInitMensuo;

    @Value("${integration.data.url.dict.getDictDatas}")
    public String integrationDataUrlDictGetDictDatas;
    @Value("${integration.data.url.dict.getRegionName}")
    public String  integrationDataUrlRegionName;

    @Value("${integration.workflow.url.approvalRecord.add}")
    public String integrationWorkflowUrlApprovalRecordAdd;

    @Value("${integration.payment.url.payment.pay.send}")
    public String integrationPaymentUrlPaymentPaySend;

    @Value("${integration.dataresource.url.zhongzhicheng.blacklist}")
    public String integrationDataresourceUrlZhongzhichengBlacklist;

    @Value("${integration.dataresource.url.fahai.person}")
    public String integrationDataresourceUrlFahaiPerson;

    @Value("${integration.dataresource.url.tongdun.getData}")
    public String integrationDataresourceUrlTongdunGetData;

    @Value("${integration.dataresource.url.geo.nameMobileIdnumberVerify}")
    public String integrationDataresourceUrlGeoNameMobileIdnumberVerify;

    @Value("${integration.dataresource.url.geo.mobileStatusVerify}")
    public String integrationDataresourceUrlGeoMobileStatusVerify;

    @Value("${integration.dataresource.url.geo.mobileValidTime}")
    public String integrationDataresourceUrlGeoMobileValidTime;

    @Value("${integration.dataresource.url.geo.nameMobileVerify}")
    public String integrationDataresourceUrlGeoNameMobileVerify;

    @Value("${integration.dataresource.url.geo.callAmount}")
    public String integrationDataresourceUrlGeoCallAmount;

    @Value("${integration.dataresource.url.baidu.geocoder.renderReverseSn}")
    public String integrationDataresourceUrlBaiduReverseSn;

    @Value("${integration.order.url.findOrder.findOrder}")
    public String integrationOrderUrlFindOrder;
    @Value("${integration.order.url.countOrder}")
    public  String integrationOrderUrlcountOrder;

    @Value("${integration.report.url.root}")
    public String integrationReportUrlRoot;

    @Value("${open.data.check}")
    public String openDataCheck;

}
