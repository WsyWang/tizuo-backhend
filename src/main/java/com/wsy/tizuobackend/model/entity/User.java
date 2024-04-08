package com.wsy.tizuobackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName tb_user
 */
@TableName(value ="tb_user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 身份证
     */
    private String userIdentityCard;

    /**
     * 电话
     */
    private String userPhone;

    /**
     * 性别 -0 男 -1 女
     */
    private Integer userGender;

    /**
     * 电子邮箱
     */
    private String userEmail;

    /**
     * 班级列表（json数组）
     */
    private String classIdList;

    /**
     * 用户权限 "admin" "teacher" "student" "ban" 默认"student"
     */
    private String userRole;

    /**
     * 用户状态 -ban 封号 -normal 正常
     */
    private String userStatus;

    /**
     * 创建时间 默认CURRENT_TIMESTAMP
     */
    private Date createTime;

    /**
     * 更新时间 默认
CURRENT_TIMESTAMP
     */
    private Date updateTime;

    /**
     * 逻辑删除 删除 -1 未删除 -0 默认 -0
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}