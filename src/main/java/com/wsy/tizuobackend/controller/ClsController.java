package com.wsy.tizuobackend.controller;

import cn.hutool.core.util.ObjUtil;
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
import com.wsy.tizuobackend.model.vo.ClsVO;
import com.wsy.tizuobackend.service.ClsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 班级接口
 */
@RestController
@RequestMapping("/cls")
public class ClsController {

    @Resource
    private ClsService clsService;

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
}
