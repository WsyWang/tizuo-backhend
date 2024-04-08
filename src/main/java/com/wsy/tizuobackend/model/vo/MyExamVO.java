package com.wsy.tizuobackend.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取我的试卷VO类
 */
@Data
public class MyExamVO implements Serializable {

    private static final long serialVersionUID = -7850956825482668593L;

    /**
     * 考试id
     */
    private Long examId;

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
     * 班级id
     */
    private Long classid;

    /**
     * 考试持续时间
     */
    private String examDurationTime;

    /**
     * 试卷id
     */
    private Long paperId;

    /**
     * 考试发起人
     */
    private String teacherName;

}
