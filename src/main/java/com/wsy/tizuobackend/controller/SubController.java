package com.wsy.tizuobackend.controller;

import cn.hutool.core.util.ObjUtil;
import com.wsy.tizuobackend.annotation.AuthCheck;
import com.wsy.tizuobackend.common.BaseResponse;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.common.RequestUtil;
import com.wsy.tizuobackend.constant.UserConstant;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.sub.SubCreateRequest;
import com.wsy.tizuobackend.model.entity.Sub;
import com.wsy.tizuobackend.service.SubService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学科相关接口
 */
@RestController
@RequestMapping("/sub")
public class SubController {

    @Resource
    private SubService subService;

    /**
     * 创建学科接口
     *
     * @param subCreateRequest
     * @return
     */
    @PostMapping("/createSub")
    @AuthCheck(mustRole = {UserConstant.TYPE_ADMIN})
    public BaseResponse<Boolean> createSub(@RequestBody SubCreateRequest subCreateRequest) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(subCreateRequest), ErrorCode.PARAMS_ERROR);
        boolean result = subService.createSub(subCreateRequest);
        return RequestUtil.success(result);
    }

    /**
     * 获取学科列表
     * @return
     */
    @GetMapping("/getSubName")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER, UserConstant.TYPE_ADMIN, UserConstant.TYPE_STUDENT})
    public BaseResponse<List<Sub>> getSubName() {
        List<Sub> subList = subService.list();
        return RequestUtil.success(subList);
    }


}
