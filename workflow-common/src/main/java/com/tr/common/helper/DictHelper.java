package com.tr.common.helper;

import com.tr.common.config.BizConfig;
import com.tr.common.config.InitConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2018/4/20.
 */
public class DictHelper {

    public static Map<String, String> getDictById(String typeCode, String code){
        List<Map<String, String>> dictList = InitConfig.dictList;
        Map<String, String> dict = new HashMap<String, String>();
        for(int i=0; i<dictList.size(); i++){
            if(dictList.get(i).get("typeCode").equals(typeCode) && dictList.get(i).get("code").equals(code)){
                dict = dictList.get(i);
            }

        }
        return dict;
    }


    public static String getDictDescById(String typeCode, String code){
        return getDictById(typeCode, code).get("codeDesc");
    }


    public static String getDictOtherInfoById(String typeCode, String code){
        return getDictById(typeCode, code).get("otherInfo");
    }
}
