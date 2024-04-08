package com.wsy.tizuobackend.common;

/**
 * 请求工具类
 */
public class RequestUtil {

    /**
     * 代表只提供静态方法
     */
    private RequestUtil() {
    }

    /**
     * 成功
     * @param data 返回数据
     * @return 统一返回类型类
     * @param <T> 数据类型
     */
    public static <T> BaseResponse<T> success(T data) {
        ErrorCode success = ErrorCode.SUCCESS;
        return new BaseResponse<>(success.getCode(), success.getMessage(), data);
    }

    /**
     * 失败
     * @param errorCode 错误码对象
     * @return 统一返回类型类
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse(errorCode);
    }

    /**
     * 失败
     * @param code 错误码
     * @param message 错误信息
     * @return 统一返回类型类
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, message);
    }


}
