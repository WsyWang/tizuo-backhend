package com.wsy.tizuobackend.model.dto.cls;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级查询请求体
 */
@Data
public class ClsQueryRequest implements Serializable {

    private static final long serialVersionUID = 11602878934254286L;

    /**
     * 学科名
     */
    private String subName;

    /**
     * 班级名
     */
    private String className;

    /**
     * 当前页面
     */
    private Long currentPage;
}
