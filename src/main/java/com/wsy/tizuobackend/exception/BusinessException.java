package com.wsy.tizuobackend.exception;

import com.wsy.tizuobackend.common.ErrorCode;
import lombok.Data;

/**
 * 基本异常类
 */
@Data
public class BusinessException extends RuntimeException{

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
