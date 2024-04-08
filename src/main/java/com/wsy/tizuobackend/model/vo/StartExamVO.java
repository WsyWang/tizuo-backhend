package com.wsy.tizuobackend.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 开始考试VO
 */
@Data
public class StartExamVO {

    /**
     * 考试id
     */
    private Long examId;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 考试科目
     */
    private String subName;

    /**
     * 考试持续时间
     */
    private String examDurationTime;

    /**
     * 试卷id
     */
    private List<Long> questionIdList;

}
