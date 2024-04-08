package com.wsy.tizuobackend.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsy.tizuobackend.annotation.AuthCheck;
import com.wsy.tizuobackend.common.BaseResponse;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.common.RequestUtil;
import com.wsy.tizuobackend.constant.UserConstant;
import com.wsy.tizuobackend.exception.BusinessException;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.user.UserLoginRequest;
import com.wsy.tizuobackend.model.dto.user.UserRegisterRequest;
import com.wsy.tizuobackend.model.vo.UserVO;
import com.wsy.tizuobackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 用户服务层
     */
    @Resource
    private UserService userService;

    /**
     * 注册接口
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long userId = userService.userRegister(userRegisterRequest);
        return RequestUtil.success(userId);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 登录请求体
     * @param request          请求
     * @return 用户登录信息
     */
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtil.throwIf(ObjUtil.hasEmpty(userLoginRequest, request), ErrorCode.PARAMS_ERROR);
        Object user = request.getSession().getAttribute(UserConstant.USER_LOGIN_KEY);
        ThrowUtil.throwIf(ObjUtil.isNotEmpty(user), ErrorCode.OPERATION_ERROR.getCode(), "已登录");
        //调用登录方法
        UserVO userVO = userService.login(userLoginRequest, request);
        return RequestUtil.success(userVO);
    }

    /**
     * 获取登录用户接口
     *
     * @param request
     * @return
     */
    @GetMapping("/getLoginUser")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO userVO = userService.getLoginUser(request);
        return RequestUtil.success(userVO);
    }

    /**
     * 用户注销接口
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean result = userService.logout(request);
        return RequestUtil.success(result);
    }

    /**
     * 获取教师列表接口分页
     *
     * @return
     */
    @GetMapping("/getTeacherList")
    @AuthCheck(mustRole = {UserConstant.TYPE_ADMIN})
    public BaseResponse<Page<UserVO>> getTeacherList(Long currentPage) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(currentPage), ErrorCode.PARAMS_ERROR);
        Page<UserVO> userVOPage = userService.getTeacherList(currentPage);
        return RequestUtil.success(userVOPage);
    }

    /**
     * 获取学生列表分页
     * @param currentPage
     * @return
     */
    @GetMapping("/getStudentList")
    @AuthCheck(mustRole = {UserConstant.TYPE_ADMIN, UserConstant.TYPE_TEACHER})
    public BaseResponse<Page<UserVO>> getStudentList(Long currentPage) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(currentPage), ErrorCode.PARAMS_ERROR);
        Page<UserVO> userVOList = userService.getStudentList(currentPage);
        return RequestUtil.success(userVOList);
    }

}
