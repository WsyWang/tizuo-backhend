package com.wsy.tizuobackend.exception;

import com.wsy.tizuobackend.common.BaseResponse;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.common.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理基本异常
     * @param e 异常对象
     * @return 统一封装类型类
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException: " + e);
        e.printStackTrace();
        return RequestUtil.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理运行时异常
     * @param e 异常对象
     * @return 统一封装类型类
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: " + e);
        return RequestUtil.error(ErrorCode.SYSTEM_ERROR);
    }

}
