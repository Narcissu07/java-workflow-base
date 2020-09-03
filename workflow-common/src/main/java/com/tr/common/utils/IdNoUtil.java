package com.tr.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class IdNoUtil {
    /**
     * 根据身份证号计算年龄
     * @param idNo
     * @return
     */
    public static int getAge(String idNo){
        if(StringUtils.isEmpty(idNo) || idNo.length()!=18){
            return 0;
        }
        int birthYear = Integer.valueOf(idNo.substring(6,10));
        int currYear = DateUtil.getYear(new Date());
        int userAge = currYear-birthYear;
        return userAge;
    }
}
