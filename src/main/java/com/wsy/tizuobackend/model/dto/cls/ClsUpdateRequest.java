package com.wsy.tizuobackend.model.dto.cls;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级更新请求体
 */
@Data
public class ClsUpdateRequest implements Serializable {

    private static final long serialVersionUID = 11602878934254286L;

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 班级名
     */
    private String className;

}
