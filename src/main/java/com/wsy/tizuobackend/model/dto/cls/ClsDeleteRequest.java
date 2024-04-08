package com.wsy.tizuobackend.model.dto.cls;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级删除请求体
 */
@Data
public class ClsDeleteRequest implements Serializable {

    private static final long serialVersionUID = 11602878934254286L;

    /**
     * 班级id
     */
    private Long classId;
}
