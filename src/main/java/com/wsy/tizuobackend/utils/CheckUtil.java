package com.wsy.tizuobackend.utils;

import java.util.regex.Pattern;

/**
 * 校验工具类
 */
public class CheckUtil {
    /**
     * 省份编码
     */
    private static final String[] PROVINCE_CODE = {
            "11", "12", "13", "14", "15", "21", "22", "23", "31", "32",
            "33", "34", "35", "36", "37", "41", "42", "43", "44", "45",
            "46", "50", "51", "52", "53", "54", "61", "62", "63", "64",
            "65", "71", "81", "82", "91"
    };


    /**
     * 只对外提供静态工具方法
     */
    private CheckUtil() {}

    /**
     * 校验身份证合法性
     * @param idCard 身份证号
     * @return 是否验证通过
     */
    public static boolean idCardCheck(String idCard) {
        //1.判断身份证位数
        if (idCard.length() != 18) {
            return false;
        }
        //2.利用正则判断身份证合法性
        String regexIdCard = "^\\d{17}[\\d|x|X]$";
        if (!Pattern.matches(regexIdCard, idCard)) {
            return false;
        }
        //校验省份
        boolean validProvince = false;
        String provinceCode = idCard.substring(0, 2);
        for (String code : PROVINCE_CODE) {
            if (provinceCode.equals(code)) {
                validProvince = true;
                break;
            }
        }
        if (!validProvince) {
            return false;
        }
        //校验生日
        String birthDay = idCard.substring(6,14);
        int year = Integer.parseInt(birthDay.substring(0, 4));
        int month = Integer.parseInt(birthDay.substring(4, 6));
        int day = Integer.parseInt(birthDay.substring(6, 8));
        if (year < 1900 || year > 2999 || month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }
        if (month == 2 && day > 29) {
            return false;
        }
        //3.校验通过
        return true;
    }

    /**
     * 校验手机号
     * @param phoneNumber 手机号
     * @return 是否校验通过
     */
    public static boolean phoneNumberCheck(String phoneNumber) {
        //1.判断手机号是否为空
        if (phoneNumber == null) {
            return false;
        }
        //2.判断手机号位数
        if (phoneNumber.length() != 11) {
            return false;
        }
        //3.根据正则判断手机号合法性
        String phoneRegex = "^1[345789]\\d{9}$";
        if (!Pattern.matches(phoneRegex, phoneNumber)) {
            return false;
        }
        //4.校验通过
        return true;
    }

    /**
     * 校验是否是中文名字
     * @param name 名字
     * @return 是否是中文名
     */
    public static boolean isChineseName(String name) {
        String chineseRegex = "^[\\u4e00-\\u9fa5]+$";
        if (!Pattern.matches(chineseRegex, name)) {
            return false;
        }
        return true;
    }

}
