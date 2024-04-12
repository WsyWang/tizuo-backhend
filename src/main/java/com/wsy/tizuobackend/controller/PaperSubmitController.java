package com.wsy.tizuobackend.controller;

import cn.hutool.core.util.ObjUtil;
import com.wsy.tizuobackend.annotation.AuthCheck;
import com.wsy.tizuobackend.common.BaseResponse;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.common.RequestUtil;
import com.wsy.tizuobackend.constant.UserConstant;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.paperSubmit.SubmitPaperRequest;
import com.wsy.tizuobackend.service.PapersubmitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 试卷提交接口
 */
@RestController
@RequestMapping("/paperSubmit")
public class PaperSubmitController {

    @Resource
    private PapersubmitService papersubmitService;

    /**
     * 创建试卷提交
     *
     * @param examId
     * @param request
     * @return
     */
    @PostMapping("/createPaperSubmit")
    @AuthCheck(mustRole = {UserConstant.TYPE_STUDENT})
    public BaseResponse<Long> createPaperSubmit(@RequestParam String examId, HttpServletRequest request) {
        ThrowUtil.throwIf(ObjUtil.hasEmpty(examId, request), ErrorCode.PARAMS_ERROR);
        long eId = Long.parseLong(examId);
        Long submitId = papersubmitService.createPaperSubmit(eId, request);
        return RequestUtil.success(submitId);
    }

    @GetMapping("/getPaperIsSubmit")
    @AuthCheck(mustRole = {UserConstant.TYPE_STUDENT})
    public BaseResponse<Boolean> getPaperIsSubmit(Long examId, HttpServletRequest request) {
        Boolean isSubmit = papersubmitService.getPaperIsSubmit(examId, request);
        return RequestUtil.success(isSubmit);
    }

    /**
     * 交卷
     * @param submitPaperRequest
     * @param request
     * @return
     */
    @PostMapping("/submitPaper")
    @AuthCheck(mustRole = {UserConstant.TYPE_STUDENT})
    public BaseResponse<Boolean> submitPaper(@RequestBody SubmitPaperRequest submitPaperRequest, HttpServletRequest request) {
        System.out.println(submitPaperRequest);
        //判空
        ThrowUtil.throwIf(ObjUtil.hasEmpty(submitPaperRequest, request), ErrorCode.PARAMS_ERROR, "请求参数为空");
        //调用service方法
        Boolean result = papersubmitService.submitPaper(submitPaperRequest, request);
        //返回是否提交成功
        return RequestUtil.success(result);
    }

    //@PostMapping("/judgePaper")
    //@AuthCheck(mustRole = {UserConstant.TYPE_TEACHER})
    //public BaseResponse<Boolean> judgePaper(@RequestBody JudgePaperRequest judgePaperRequest, HttpServletRequest request) {
    //    System.out.println(submitPaperRequest);
    //    //判空
    //    ThrowUtil.throwIf(ObjUtil.hasEmpty(submitPaperRequest, request), ErrorCode.PARAMS_ERROR, "请求参数为空");
    //    //调用service方法
    //    Boolean result = papersubmitService.judgePaper(submitPaperRequest, request);
    //    //返回是否提交成功
    //    return RequestUtil.success(result);
    //}
}
