package com.wsy.tizuobackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 班级表
 * @TableName tb_cls
 */
@TableName(value ="tb_cls")
@Data
public class Cls implements Serializable {
    /**
     * 班级id
     */
    @TableId
    private Long classId;

    /**
     * 班级名
     */
    private String className;

    /**
     * 学科名
     */
    private String subName;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 班级口令
     */
    private String classKey;

    /**
     * 考试信息列表（json数组）
     */
    private String examInfoIdList;

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