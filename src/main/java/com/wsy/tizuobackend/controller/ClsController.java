package com.wsy.tizuobackend.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsy.tizuobackend.annotation.AuthCheck;
import com.wsy.tizuobackend.common.BaseResponse;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.common.RequestUtil;
import com.wsy.tizuobackend.constant.UserConstant;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.cls.ClsCreateRequest;
import com.wsy.tizuobackend.model.dto.cls.ClsDeleteRequest;
import com.wsy.tizuobackend.model.dto.cls.ClsQueryRequest;
import com.wsy.tizuobackend.model.dto.cls.ClsUpdateRequest;
import com.wsy.tizuobackend.model.entity.Cls;
import com.wsy.tizuobackend.model.vo.ClsVO;
import com.wsy.tizuobackend.service.ClsService;
import com.wsy.tizuobackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 班级接口
 */
@RestController
@RequestMapping("/cls")
public class ClsController {

    @Resource
    private ClsService clsService;

    @Resource
    private UserService userService;

    /**
     * 班级创建接口
     *
     * @param clsCreateRequest
     * @return
     */
    @PostMapping("/createCls")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER})
    public BaseResponse<String> createCls(@RequestBody ClsCreateRequest clsCreateRequest, HttpServletRequest request) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(clsCreateRequest), ErrorCode.PARAMS_ERROR);
        String result = clsService.createCls(clsCreateRequest, request);
        return RequestUtil.success(result);
    }

    /**
     * 获取班级列表（分页）
     *
     * @param request
     * @return
     */
    @PostMapping("/getClassList")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER, UserConstant.TYPE_ADMIN, UserConstant.TYPE_STUDENT})
    public BaseResponse<Page<ClsVO>> getClassListT(HttpServletRequest request, @RequestBody ClsQueryRequest queryRequest) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(queryRequest), ErrorCode.PARAMS_ERROR);
        Page<ClsVO> clsVOS = clsService.getClassList(request, queryRequest);
        return RequestUtil.success(clsVOS);
    }

    /**
     * 更新班级信息
     *
     * @param updateRequest
     * @return
     */
    @PostMapping("/updateClass")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER, UserConstant.TYPE_ADMIN})
    public BaseResponse<Boolean> updateClass(@RequestBody ClsUpdateRequest updateRequest) {
        Boolean result = clsService.updateClass(updateRequest);
        return RequestUtil.success(result);
    }

    /**
     * 删除班级
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/deleteClass")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER, UserConstant.TYPE_ADMIN})
    public BaseResponse<Boolean> deleteClass(@RequestBody ClsDeleteRequest deleteRequest) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(deleteRequest), ErrorCode.PARAMS_ERROR, "班级id为空");
        Boolean result = clsService.deleteClass(deleteRequest);
        return RequestUtil.success(result);
    }

    @GetMapping("/teacher/get/class/list")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER})
    public BaseResponse<List<ClsVO>> teacherGetClassList(HttpServletRequest request) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(request), ErrorCode.PARAMS_ERROR);
        long teacherId = userService.getLoginUser(request).getId();
        QueryWrapper<Cls> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacherId", teacherId);
        List<Cls> clsList = clsService.list(queryWrapper);
        if (clsList.size() == 0) {
            return RequestUtil.success(new ArrayList<>());
        }
        List<ClsVO> clsVOList = clsList.stream().map(ClsVO::objToVO).collect(Collectors.toList());
        return RequestUtil.success(clsVOList);
    }
}
