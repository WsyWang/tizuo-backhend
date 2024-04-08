package com.wsy.tizuobackend.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.constant.PageConstant;
import com.wsy.tizuobackend.constant.UserConstant;
import com.wsy.tizuobackend.exception.BusinessException;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.mapper.UserMapper;
import com.wsy.tizuobackend.model.dto.user.UserLoginRequest;
import com.wsy.tizuobackend.model.dto.user.UserRegisterRequest;
import com.wsy.tizuobackend.model.entity.User;
import com.wsy.tizuobackend.model.vo.UserVO;
import com.wsy.tizuobackend.service.UserService;
import com.wsy.tizuobackend.utils.CheckUtil;
import com.wsy.tizuobackend.utils.EncryptionUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 15790
 * @description 针对表【tb_user(用户表)】的数据库操作Service实现
 * @createDate 2024-03-16 20:13:31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    /**
     * 注册方法
     *
     * @param userRegisterRequest
     * @return 用户id
     */
    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userName = userRegisterRequest.getUserName();
        String userIdentityCard = userRegisterRequest.getUserIdentityCard();
        String userPhone = userRegisterRequest.getUserPhone();
        String userRole = userRegisterRequest.getUserRole();
        //基础校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword, userName, userIdentityCard, userPhone, userRole)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ThrowUtil.throwIf((userAccount.length() < 4), ErrorCode.PARAMS_ERROR.getCode(), "账号过短");
        ThrowUtil.throwIf((userPassword.length() < 8), ErrorCode.PARAMS_ERROR.getCode(), "密码过短");
        ThrowUtil.throwIf((userName.length() > 20), ErrorCode.PARAMS_ERROR.getCode(), "姓名过长");
        ThrowUtil.throwIf(!(userPassword.equals(checkPassword)), ErrorCode.PARAMS_ERROR.getCode(), "密码不一致");
        ThrowUtil.throwIf(!CheckUtil.idCardCheck(userIdentityCard), ErrorCode.PARAMS_ERROR.getCode(), "非法身份证");
        ThrowUtil.throwIf(!(CheckUtil.phoneNumberCheck(userPhone)), ErrorCode.PARAMS_ERROR.getCode(), "非法手机号");
        ThrowUtil.throwIf(!CheckUtil.isChineseName(userName), ErrorCode.PARAMS_ERROR.getCode(), "请输入中文名");
        //查询数据库是否已有用户，判断标准，账号、身份证、手机号均不可重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount)
                .or()
                .eq("userIdentityCard", userIdentityCard)
                .or()
                .eq("userPhone", userPhone);
        User user = this.getOne(queryWrapper);
        if (user != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode(), "用户已存在");
        }
        //判断性别
        int gender = Integer.parseInt(userIdentityCard.substring(16, 17));
        if (gender % 2 == 0) {
            gender = 1;
        } else {
            gender = 0;
        }
        //密码加密
        String ecCode = EncryptionUtil.md5(userPassword, UserConstant.PASSWORD_SALT);
        //保存数据库
        User saveUser = new User();
        saveUser.setUserAccount(userAccount);
        saveUser.setUserPassword(ecCode);
        saveUser.setUserName(userName);
        saveUser.setUserIdentityCard(userIdentityCard);
        saveUser.setUserPhone(userPhone);
        saveUser.setUserGender(gender);
        saveUser.setUserRole(userRole);
        ThrowUtil.throwIf(!this.save(saveUser), ErrorCode.OPERATION_ERROR);
        return saveUser.getId();
    }

    /**
     * 登录方法
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @Override
    public UserVO login(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        //基础校验
        ThrowUtil.throwIf(StrUtil.hasBlank(userAccount, userPassword), ErrorCode.PARAMS_ERROR);
        //密码加密
        String enPassword = EncryptionUtil.md5(userPassword, UserConstant.PASSWORD_SALT);
        //查询数据库
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount)
                .and(userQueryWrapper -> userQueryWrapper.eq("userPassword", enPassword));
        User user = this.getOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode(), "账号或密码错误");
        }
        if (user.getUserStatus().equals(UserConstant.TYPE_BAN)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR.getCode(), "已被封号");
        }
        //将用户信息保存到缓存中
        request.getSession().setAttribute(UserConstant.USER_LOGIN_KEY, user.getId());
        //对象转包装类
        UserVO userVO = UserVO.objToVO(user);
        return userVO;
    }

    /**
     * 注销方法
     *
     * @param request
     * @return
     */
    @Override
    public Boolean logout(HttpServletRequest request) {
        if (request.getSession().getAttribute(UserConstant.USER_LOGIN_KEY) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode(), "请先登录");
        }
        //移除登录缓存
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_KEY);
        return true;
    }

    /**
     * 获取登录用户方法
     *
     * @param request
     * @return
     */
    @Override
    public UserVO getLoginUser(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute(UserConstant.USER_LOGIN_KEY);
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode(), "未登录");
        }
        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        UserVO userVO = UserVO.objToVO(user);
        return userVO;
    }

    /**
     * 获取教师列表(分页)
     *
     * @return
     */
    @Override
    public Page<UserVO> getTeacherList(long currentPage) {
        long pageSize = PageConstant.PAGE_SIZE;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userRole", "teacher");
        Page<User> userPage = this.page(new Page<>(currentPage, pageSize), queryWrapper);
        Page<UserVO> userVOPage = new Page<>(currentPage, pageSize, userPage.getTotal());
        List<User> userList = userPage.getRecords();
        if (userList.size() == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<UserVO> userVOList = userList.stream().map(item -> UserVO.objToVO(item)).collect(Collectors.toList());
        userVOPage.setRecords(userVOList);
        return userVOPage;
    }

    /**
     * 获取学生列表方法(分页)
     *
     * @return
     */
    @Override
    public Page<UserVO> getStudentList(long currentPage) {
        long pageSize = PageConstant.PAGE_SIZE;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userRole", "student");
        Page<User> userPage = this.page(new Page<>(currentPage, pageSize), queryWrapper);
        if (userPage.getRecords().size() == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Page<UserVO> userVOPage = new Page<>(currentPage, pageSize, userPage.getTotal());
        List<User> userList = userPage.getRecords();
        List<UserVO> userVOList = userList.stream().map(item -> UserVO.objToVO(item)).collect(Collectors.toList());
        userVOPage.setRecords(userVOList);
        return userVOPage;
    }


}




