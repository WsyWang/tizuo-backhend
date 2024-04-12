package com.wsy.tizuobackend.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsy.tizuobackend.annotation.AuthCheck;
import com.wsy.tizuobackend.common.BaseResponse;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.common.RequestUtil;
import com.wsy.tizuobackend.constant.UserConstant;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.exam.ExamCreateRequest;
import com.wsy.tizuobackend.model.dto.exam.GetExamListRequest;
import com.wsy.tizuobackend.model.vo.*;
import com.wsy.tizuobackend.service.ExamService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 考试接口
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Resource
    private ExamService examService;

    /**
     * 创建考试
     * @param examCreateRequest
     * @param request
     * @return
     */
    @PostMapping("/createExam")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER})
    public BaseResponse<Boolean> createExam(@RequestBody ExamCreateRequest examCreateRequest, HttpServletRequest request) {
        ThrowUtil.throwIf(ObjUtil.hasEmpty(examCreateRequest, request), ErrorCode.PARAMS_ERROR);
        examService.createExam(examCreateRequest, request);
        return RequestUtil.success(true);
    }

    /**
     * 获取我的考试
     * @param request
     * @return
     */
    @GetMapping("/getMyExam")
    @AuthCheck(mustRole = {UserConstant.TYPE_STUDENT})
    public BaseResponse<List<MyExamVO>> getMyExam(HttpServletRequest request) {
        List<MyExamVO> examList = examService.getMyExam(request);
        return RequestUtil.success(examList);
    }

    /**
     * 获取考试详情
     * @param examId
     * @return
     */
    @GetMapping("/getStartExamDetail")
    @AuthCheck(mustRole = {UserConstant.TYPE_STUDENT, UserConstant.TYPE_TEACHER})
    public BaseResponse<ExamDetailVO> getStartExamDetail(Long examId) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(examId), ErrorCode.PARAMS_ERROR);
        ExamDetailVO examDetailVO = examService.getStartExamDetail(examId);
        return RequestUtil.success(examDetailVO);
    }

    @PostMapping("/startExam")
    @AuthCheck(mustRole = {UserConstant.TYPE_STUDENT})
    public BaseResponse<StartExamVO> startExam(@RequestParam Long examId, HttpServletRequest request) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(examId), ErrorCode.PARAMS_ERROR);
        StartExamVO startExamVO = examService.startExam(examId, request);
        return RequestUtil.success(startExamVO);
    }

    @GetMapping("/getExamInfo")
    @AuthCheck(mustRole = {UserConstant.TYPE_STUDENT, UserConstant.TYPE_TEACHER, UserConstant.TYPE_ADMIN})
    public BaseResponse<ExamInfoVO> getExamInfo(Long examId) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(examId), ErrorCode.PARAMS_ERROR);
        ExamInfoVO examInfoVO = examService.getExamInfo(examId);
        return RequestUtil.success(examInfoVO);
    }

    @GetMapping("/getQuestionIdList")
    @AuthCheck(mustRole = UserConstant.TYPE_STUDENT)
    public BaseResponse<List<Long>> getQuestionList(Long examId) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(examId), ErrorCode.PARAMS_ERROR);
        List<Long> questionIdList = examService.getQuestionIdList(examId);
        return RequestUtil.success(questionIdList);
    }

    /**
     * 教师获取考试列表接口
     * @param getExamListRequest
     * @param request
     * @return
     */
    @PostMapping("/get-examList-teacher")
    @AuthCheck(mustRole = UserConstant.TYPE_TEACHER)
    public BaseResponse<Page<ExamListVO>> getExamListTeacher(@RequestBody GetExamListRequest getExamListRequest, HttpServletRequest request) {
        ThrowUtil.throwIf(getExamListRequest == null, ErrorCode.PARAMS_ERROR, "查询参数错误");
        ThrowUtil.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求头错误");
        Page<ExamListVO> examListVOPage = examService.getExamListTeacher(getExamListRequest, request);
        return RequestUtil.success(examListVOPage);
    }
}
