package com.wsy.tizuobackend.aop;

import com.wsy.tizuobackend.annotation.AuthCheck;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.exception.BusinessException;
import com.wsy.tizuobackend.model.enums.UserAccessEnum;
import com.wsy.tizuobackend.model.vo.UserVO;
import com.wsy.tizuobackend.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 权限校验切面
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 权限校验切面方法
     * @param joinPoint 目标对象
     * @param authCheck 权限注解
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        //1.获取需要的注解
        String[] mustRole = authCheck.mustRole();
        //2.获取请求对象,查询当前用户权限
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        UserVO loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        String userRole = loginUser.getUserRole();
        //3.通过注解传入的权限是否在枚举类里
        List<UserAccessEnum> mustRoleEnums = UserAccessEnum.getEnumByValue(mustRole);
        UserAccessEnum needCheckRole = UserAccessEnum.getEnumByValue(userRole);
        //4.如果注解传入的权限不在枚举类，直接返回系统错误
        if (mustRoleEnums.size() == 0 || needCheckRole == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //5.如果用户权限为封号，直接返回
        if (needCheckRole.equals(UserAccessEnum.BAN)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean accept = false;
        //6.遍历需要权限的枚举对象列表，如果用户权限不在里面，直接返回
        for (UserAccessEnum userAccessEnum : mustRoleEnums) {
            if (userAccessEnum.equals(needCheckRole)) {
                accept = true;
            }
        }
        if (!accept) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //6.校验通过，放行
        return joinPoint.proceed();
    }
}
