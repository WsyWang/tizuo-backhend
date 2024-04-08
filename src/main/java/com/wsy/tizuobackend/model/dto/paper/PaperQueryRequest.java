package com.wsy.tizuobackend.model.dto.paper;

import lombok.Data;

import java.io.Serializable;

/**
 * 试卷查询请求体
 */
@Data
public class PaperQueryRequest implements Serializable {

    private static final long serialVersionUID = 11602878934254286L;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 试卷id
     */
    private Long paperId;

    /**
     * 科目
     */
    private String subName;

    /**
     * 当前页面
     */
    private Long currentPage;
}
