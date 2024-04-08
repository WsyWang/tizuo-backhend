package com.wsy.tizuobackend.model.dto.paper;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 试卷创建请求体
 */
@Data
public class PaperCreateRequest implements Serializable {

    private static final long serialVersionUID = 8778930884872886078L;

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
     * 试卷科目
     */
    private String subName;

}
