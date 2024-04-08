package com.wsy.tizuobackend.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回考试详情
 */
@Data
public class ExamDetailVO implements Serializable {

    private static final long serialVersionUID = 8684342582925618879L;

    /**
     * 考试id
     */
    private Long examId;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 考试班级
     */
    private String className;

    /**
     * 考试科目
     */
    private String subName;

    /**
     * 考试开始时间
     */
    private String examStartTime;

    /**
     * 考试结束时间
     */
    private String examEndTime;

    /**
     * 考试持续时间
     */
    private String examDurationTime;

    /**
     * 考试发起人
     */
    private String teacherName;

    /**
     * 考试状态 0- 待考试 1- 考试中 2- 已结束
     */
    private Integer examStatus;
}
