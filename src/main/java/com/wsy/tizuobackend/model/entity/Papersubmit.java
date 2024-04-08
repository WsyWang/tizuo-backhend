package com.wsy.tizuobackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试卷提交表
 * @TableName tb_papersubmit
 */
@TableName(value ="tb_papersubmit")
@Data
public class Papersubmit implements Serializable {
    /**
     * 试卷提交id
     */
    @TableId
    private Long paperSubmitId;

    /**
     * 提交人
     */
    private Long studentId;

    /**
     * 考试场次
     */
    private Long examId;

    /**
     * 答卷状态 -0 未答卷 1- 已答卷
     */
    private Integer answerStatus;

    /**
     * 批卷状态 -0 未批卷 1- 已批卷
     */
    private Integer judgeStatus;

    /**
     * 批卷信息（json对象）
     */
    private String judgeInfo;

    /**
     * 逻辑删除 删除 -1 未删除 -0 默认 -0
     */
    private Integer isDelete;

    /**
     * 创建时间 默认CURRENT_TIMESTAMP
     */
    private Date createTime;

    /**
     * 更新时间 默认
CURRENT_TIMESTAMP
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}