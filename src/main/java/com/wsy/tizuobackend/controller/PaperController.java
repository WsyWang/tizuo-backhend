package com.wsy.tizuobackend.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsy.tizuobackend.annotation.AuthCheck;
import com.wsy.tizuobackend.common.BaseResponse;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.common.RequestUtil;
import com.wsy.tizuobackend.constant.UserConstant;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.paper.PaperCreateRequest;
import com.wsy.tizuobackend.model.dto.paper.PaperListNoPageRequest;
import com.wsy.tizuobackend.model.dto.paper.PaperQueryRequest;
import com.wsy.tizuobackend.model.vo.PaperVO;
import com.wsy.tizuobackend.service.PaperService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 试卷接口
 */
@RestController
@RequestMapping("/paper")
public class PaperController {

    @Resource
    private PaperService paperService;

    /**
     * 创建试卷
     * @param paperCreateRequest
     * @param request
     * @return
     */
    @PostMapping("/createPaper")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER})
    public BaseResponse<Boolean> createPaper(@RequestBody PaperCreateRequest paperCreateRequest, HttpServletRequest request) {
        ThrowUtil.throwIf(ObjUtil.hasEmpty(paperCreateRequest, request), ErrorCode.PARAMS_ERROR);
        paperService.createPaper(paperCreateRequest, request);
        return RequestUtil.success(true);
    }

    /**
     * 获取试卷列表（分页）
     *
     * @param request
     * @return
     */
    @PostMapping("/getPaperList")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER, UserConstant.TYPE_ADMIN})
    public BaseResponse<Page<PaperVO>> getPaperList(HttpServletRequest request, @RequestBody PaperQueryRequest queryRequest) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(queryRequest), ErrorCode.PARAMS_ERROR);
        Page<PaperVO> paperVOPage = paperService.getPaperList(request, queryRequest);
        return RequestUtil.success(paperVOPage);
    }

    @PostMapping("/get_paper_list_no_page")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER})
    public BaseResponse<List<PaperVO>> getPaperListNoPage(@RequestBody PaperListNoPageRequest paperListNoPageRequest) {
        List<PaperVO> paperVOList = paperService.getPaperListNoPage(paperListNoPageRequest);
        return RequestUtil.success(paperVOList);
    }


}
