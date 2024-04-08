package com.wsy.tizuobackend.model.dto.paperSubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * 提交试卷请求体
 */
@Data
public class SubmitPaperRequest implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 6717911289907119477L;

    /**
     * 考试场次
     */
    private Long examId;

    /**
     * 批卷信息，考试答案，json数组
     */
    private String[] judgeInfo;

}
