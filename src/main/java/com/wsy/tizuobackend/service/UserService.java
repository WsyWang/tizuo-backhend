package com.wsy.tizuobackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsy.tizuobackend.model.dto.user.UserLoginRequest;
import com.wsy.tizuobackend.model.dto.user.UserRegisterRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wsy.tizuobackend.model.entity.User;
import com.wsy.tizuobackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 15790
* @description 针对表【tb_user(用户表)】的数据库操作Service
* @createDate 2024-03-16 20:13:31
*/
public interface UserService extends IService<User> {

    /**
     * 注册方法
     * @param userRegisterRequest
     * @return
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 登录方法
     * @param userLoginRequest
     * @param request
     * @return
     */
    UserVO login(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 注销方法
     * @param request
     * @return
     */
    Boolean logout(HttpServletRequest request);

    /**
     * 获取登录用户方法
     * @param request
     * @return
     */
    UserVO getLoginUser(HttpServletRequest request);

    /**
     * 获取教师列表
     * @return
     */
    Page<UserVO> getTeacherList(long currentPage);

    /**
     * 获取学生列表
     * @return
     */
    Page<UserVO> getStudentList(long currentPage);

}
