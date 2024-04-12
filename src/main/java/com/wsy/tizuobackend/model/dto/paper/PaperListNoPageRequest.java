package com.wsy.tizuobackend.model.dto.paper;

import lombok.Data;

import java.io.Serializable;

/**
 * 不分页的试卷列表请求体
 */
@Data
public class PaperListNoPageRequest implements Serializable {

    private static final long serialVersionUID = 11602878934254286L;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 科目
     */
    private String subName;

}
