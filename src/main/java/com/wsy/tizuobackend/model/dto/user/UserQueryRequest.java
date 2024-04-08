package com.wsy.tizuobackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户查询请求体
 */
@Data
public class UserQueryRequest implements Serializable {

    private static final long serialVersionUID = 4142064248842772666L;

    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 性别 -0 男 -1 女
     */
    private Integer userGender;


}
