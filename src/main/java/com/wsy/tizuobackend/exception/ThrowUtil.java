package com.wsy.tizuobackend.exception;

import com.wsy.tizuobackend.common.ErrorCode;

/**
 * 抛出异常工具类
 */
public class ThrowUtil {

    /**
     * 表示只对外提供静态方法
     */
    private ThrowUtil() {}

    /**
     * 条件成立抛异常
     * @param condition 条件
     * @param runtimeException 异常类型
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立抛异常
     * @param condition 异常
     * @param errorCode 错误码对象
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立抛异常
     * @param condition 条件
     * @param code 状态码
     * @param message 错误信息
     */
    public static void throwIf(boolean condition, int code, String message) {
        throwIf(condition, new BusinessException(code, message));
    }

    /**
     * 条件成立抛异常
     * @param condition 条件
     * @param code 状态码
     * @param message 错误信息
     */
    public static void throwIf(boolean condition, ErrorCode code, String message) {
        throwIf(condition, new BusinessException(code.getCode(), message));
    }
}
