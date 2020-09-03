package com.tr.workflow.api.controller;

import com.alibaba.fastjson.JSON;
import com.tr.biz.api.JikeDataCheckService;
import com.tr.biz.api.MensuoDataCheckService;
import com.tr.biz.dto.ApprovalResultDTO;
import com.tr.biz.request.DataCheckExcelRequest;
import com.tr.biz.request.JikeDataCheckRequest;
import com.tr.biz.request.MensuoDataCheckRequest;
import com.tr.common.config.BizConfig;
import com.tr.common.framework.results.CommonResult;
import com.tr.common.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 大数据智能风控规则测试controller
 */
@RestController
@RequestMapping("/dataCheck/test")
public class DataCheckController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private BizConfig bizConfig;
    @Resource
    private MensuoDataCheckService mensuoDataCheckService;
    @Resource
    private JikeDataCheckService jikeDataCheckService;
    @PostMapping("/executeMensuo")
    public Object executeMensuo(@RequestBody MensuoDataCheckRequest request) throws Exception{
        ApprovalResultDTO dto = mensuoDataCheckService.testDataCheck(request);
        CommonResult result = new CommonResult("0","处理成功",dto);
        return result;
    }
    @PostMapping("/executeJike")
    public Object executeJike(@RequestBody JikeDataCheckRequest request) throws Exception{
        ApprovalResultDTO dto = jikeDataCheckService.testDataCheck(request);
        CommonResult result = new CommonResult("0","处理成功",dto);
        return result;
    }

    /**
     * 正式数据测试excel
     * @param req
     * @return
     */
    @GetMapping("/execute")
    public Object execute(DataCheckExcelRequest req){
        List<ApprovalResultDTO> dtos = new ArrayList<>();
        String path = System.getProperty("SEC_CONF")+File.separator+req.getChildProduct()+".xlsx";
        log.info("测试数据文件路径："+path);
        try{
            List<String[]> rows = ExcelUtil.getData(path);
            if("jike".equals(req.getChildProduct())){
                JikeDataCheckRequest request = null;
                for (String[] row : rows){
                    request = new JikeDataCheckRequest();
                    request.setName(row[0]);
                    request.setMobile(row[1]);
                    request.setIdcardNo(row[2]);
                    request.setFriendName1(row[3]);
                    request.setFriendMobileNo1(row[4]);
                    request.setFriendName2(row[5]);
                    request.setFriendMobileNo2(row[6]);
                    request.setAccount(row[7]);
                    dtos.add(jikeDataCheckService.testDataCheck(request));
                }
            }else if("mensuo".equals(req.getChildProduct())){
                MensuoDataCheckRequest request = null;
                for (String[] row : rows){
                    request = new MensuoDataCheckRequest();
                    request.setProvince(row[0]);
                    request.setCity(row[1]);
                    request.setLng(row[2]);
                    request.setLat(row[3]);
                    request.setName(row[4]);
                    request.setMobile(row[5]);
                    request.setIdcardNo(row[6]);
                    request.setFriendName1(row[7]);
                    request.setFriendMobileNo1(row[8]);
                    dtos.add(mensuoDataCheckService.testDataCheck(request));
                }
            }else{
                log.info("暂不支持的产品代码");
                return "暂不支持的产品代码";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dtos;
    }
}
