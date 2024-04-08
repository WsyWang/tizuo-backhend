package com.wsy.tizuobackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 71842575747943018L;
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 校验密码
     */
    private String checkPassword;

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
     * 用户权限
     */
    private String userRole;

}
