package com.wsy.tizuobackend.model.vo;

import cn.hutool.json.JSONUtil;
import com.wsy.tizuobackend.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户VO
 */
@Data
public class UserVO implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 6895634443585903806L;

    /**
     * id
     */
    private long id;

    /**
     * 账号
     */
    private String userAccount;

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
     * 性别
     */
    private String  userGender;

    /**
     * 电子邮箱
     */
    private String userEmail;

    /**
     * 班级列表（json数组）
     */
    private List<String> classIdList;

    /**
     * 用户权限 "admin" "teacher" "student" "ban" 默认"student"
     */
    private String userRole;

    /**
     * 用户状态 -ban 封号 -normal 正常
     */
    private String userStatus;

    /**
     * 对象转包装类
     * @param user 需要转换的对象
     * @return 用户信息VO
     */
    public static UserVO objToVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        String classIdListStr = user.getClassIdList();
        List<String> classIdList = JSONUtil.toList(classIdListStr, String.class);
        userVO.setClassIdList(classIdList);
        Integer userGender = user.getUserGender();
        if (userGender == 0) {
            userVO.setUserGender("男");
        } else {
            userVO.setUserGender("女");
        }
        return userVO;
    }

}
