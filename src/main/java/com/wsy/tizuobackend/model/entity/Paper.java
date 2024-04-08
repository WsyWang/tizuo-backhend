package com.wsy.tizuobackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 试卷表
 * @TableName tb_paper
 */
@TableName(value ="tb_paper")
@Data
public class Paper implements Serializable {
    /**
     * 试卷id
     */
    @TableId
    private Long paperId;

    /**
     * 题目id列表（json数组）
     */
    private String questionIdList;

    /**
     * 分数信息（json对象）
     */
    private String scoreInfo;

    /**
     * 总分
     */
    private String totalScore;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 试卷科目
     */
    private String subName;

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