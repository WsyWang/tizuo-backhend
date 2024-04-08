package com.wsy.tizuobackend.constant;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户常量
 */
public class UserConstant implements Serializable {


    /**
     * 密码加密算法的盐
     */
    public static final String PASSWORD_SALT = "TiZuoWsy";

    /**
     * 用户类型 -- 学生
     */
    public static final String TYPE_STUDENT = "student";

    /**
     * 用户类型 -- 教师
     */
    public static final String TYPE_TEACHER = "teacher";

    /**
     * 用户类型 -- 管理员
     */
    public static final String TYPE_ADMIN = "admin";

    /**
     * 用户状态 -- 封号
     */
    public static final String TYPE_BAN = "ban";

    /**
     * 用户状态 -- 正常
     */
    public static final String TYPE_NORMAL = "normal";

    /**
     * 用户登录态键
     */
    public static final String USER_LOGIN_KEY = "user_login";

    private static final long serialVersionUID = -4451833250260160373L;
}
