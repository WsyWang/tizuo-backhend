package com.wsy.tizuobackend.model.dto.exam;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建考试列表请求体
 */
@Data
public class ListExamCreateRequest implements Serializable {

    private static final long serialVersionUID = 8620458997999347688L;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 考试开始时间
     */
    private String examStartTime;

    /**
     * 考试结束时间
     */
    private String examEndTime;

    /**
     * 考试科目
     */
    private String subName;

    /**
     * 班级id列表
     */
    private List<String> classIdList;

    /**
     * 考试持续时间
     */
    private String examDurationTime;

    /**
     * 试卷id
     */
    private Long paperId;
}
