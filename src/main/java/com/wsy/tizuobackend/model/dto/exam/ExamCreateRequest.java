package com.wsy.tizuobackend.model.dto.exam;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 创建考试请求体
 */
@Data
public class ExamCreateRequest implements Serializable {

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
}
