package com.wsy.tizuobackend.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建题目请求体
 */
@Data
public class QuestionUpdateRequest implements Serializable {

    private static final long serialVersionUID = -5154776273953210194L;
    /**
     * 题目id
     */
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
     * 题目答案
     */
    private String questionAnswer;

    /**
     * 学科名
     */
    private String subName;

}
