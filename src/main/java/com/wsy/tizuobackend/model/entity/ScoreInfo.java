package com.wsy.tizuobackend.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 试卷分数信息
 */
@Data
public class ScoreInfo implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -1564205974551099442L;

    /**
     * 选择题
     */
    private Integer option;

    /**
     * 判断题
     */
    private Integer judge;

    /**
     * 综合题
     */
    private Integer comprehensive;
}
