package com.wsy.tizuobackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 成绩表
 * @TableName tb_achievement
 */
@TableName(value ="tb_achievement")
@Data
public class Achievement implements Serializable {
    /**
     * 成绩id
     */
    @TableId
    private Long achievementId;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 考试id
     */
    private Long examId;

    /**
     * 成绩单状态， 0 - 未完成 1 -已完成
     */
    private Integer status;

    /**
     * 成绩
     */
    private Integer score;

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