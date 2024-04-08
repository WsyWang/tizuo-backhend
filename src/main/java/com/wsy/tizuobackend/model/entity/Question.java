package com.wsy.tizuobackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 题目表
 * @TableName tb_question
 */
@TableName(value ="tb_question")
@Data
public class Question implements Serializable {
    /**
     * 题目id
     */
    @TableId
    private Long questionId;

    /**
     * 题目类型 -0 选择 -1 判断 -2 综合
     */
    private Integer questionType;

    /**
     * 题目内容
     */
    private String questionContent;

    /**
     * 题目选项（json对象）
     */
    private String questionOption;

    /**
     * 题目答案(json对象)
     */
    private String questionAnswer;

    /**
     * 学科名
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