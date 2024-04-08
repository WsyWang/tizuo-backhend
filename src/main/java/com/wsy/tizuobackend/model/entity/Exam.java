package com.wsy.tizuobackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 考试信息表
 * @TableName tb_exam
 */
@TableName(value ="tb_exam")
@Data
public class Exam implements Serializable {
    /**
     * 考试id
     */
    @TableId
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
    private Long teacherId;

    /**
     * 考试状态 0- 待考试 1- 考试中 2- 已结束
     */
    private Integer examStatus;

    /**
     * 逻辑删除 删除 -1 未删除 -0 默认 -0
     */
    private Integer isDelete;

    /**
     * 创建时间 默认CURRENT_TIMESTAMP
     */
    private Date createTime;

    /**
     * 更新时间 默认CURRENT_TIMESTAMP
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}