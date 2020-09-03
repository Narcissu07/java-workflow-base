package com.tr.workflow.api.init;

import com.alibaba.fastjson.JSON;
import com.tr.biz.dto.CommonDTO;
import com.tr.biz.dto.DictDTO;
import com.tr.common.config.BizConfig;
import com.tr.common.config.InitConfig;
import com.tr.common.framework.api.CommonHttpClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2018/4/20.
 */
@Component
public class InitData implements CommandLineRunner {
    @Resource
    private BizConfig bizConfig;

    @Resource
    private CommonHttpClient commonHttpClient;

    public void run(String... args) {
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            String url = bizConfig.integrationDataUrlRoot + bizConfig.integrationDataUrlDictGetDictDatas;
            System.out.println("okk");
            String msg = commonHttpClient.get(url, paramMap);
            DictDTO dictDTO = JSON.parseObject(msg, DictDTO.class);
            InitConfig.dictList = dictDTO.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}