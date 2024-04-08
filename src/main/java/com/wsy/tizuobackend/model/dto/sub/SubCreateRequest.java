package com.wsy.tizuobackend.model.dto.sub;

import lombok.Data;

import java.io.Serializable;

/**
 * 学科创建请求体
 */
@Data
public class SubCreateRequest implements Serializable {

    private static final long serialVersionUID = -1472202770538589423L;

    /**
     * 学科名
     */
    private String subName;

}
