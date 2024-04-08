package com.wsy.tizuobackend.utils;

import cn.hutool.crypto.SecureUtil;

/**、
 * 加密工具类
 */
public class EncryptionUtil {
    private EncryptionUtil(){}

    public static String md5(String data, String salt) {
        return SecureUtil.md5().digestHex16(data + salt);
    }
}
