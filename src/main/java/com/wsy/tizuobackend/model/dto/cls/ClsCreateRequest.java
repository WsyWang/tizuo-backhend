package com.wsy.tizuobackend.model.dto.cls;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级请求体
 */
@Data
public class ClsCreateRequest implements Serializable {

    private static final long serialVersionUID = 4599612489027825549L;

    /**
     * 班级名
     */
    private String className;

    /**
     * 学科名
     */
    private String subName;

}
