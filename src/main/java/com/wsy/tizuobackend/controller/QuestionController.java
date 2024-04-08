package com.wsy.tizuobackend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wsy.tizuobackend.annotation.AuthCheck;
import com.wsy.tizuobackend.common.BaseResponse;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.common.RequestUtil;
import com.wsy.tizuobackend.constant.UserConstant;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.question.QuestionCreateRequest;
import com.wsy.tizuobackend.model.dto.question.QuestionUpdateRequest;
import com.wsy.tizuobackend.model.entity.Question;
import com.wsy.tizuobackend.model.vo.QuestionVO;
import com.wsy.tizuobackend.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 题目接口
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    /**
     * 创建题目
     * @param questionCreateRequest
     * @return
     */
    @PostMapping("/createQuestion")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER, UserConstant.TYPE_ADMIN})
    public BaseResponse<Boolean> createQuestion(@RequestBody QuestionCreateRequest questionCreateRequest) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(questionCreateRequest), ErrorCode.PARAMS_ERROR);
        questionService.createQuestion(questionCreateRequest);
        return RequestUtil.success(true);
    }

    /**
     * 获取试题分页
     * @return
     */
    @GetMapping("/getQuestionList")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER, UserConstant.TYPE_ADMIN})
    public BaseResponse<Page<QuestionVO>> getQuestionList(Long currentPage) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(currentPage), ErrorCode.PARAMS_ERROR);
        Page<QuestionVO> questionVOPage = questionService.getQuestionList(currentPage);
        return RequestUtil.success(questionVOPage);
    }

    @GetMapping("/getQuestion")
    @AuthCheck(mustRole = {UserConstant.TYPE_STUDENT, UserConstant.TYPE_ADMIN, UserConstant.TYPE_TEACHER})
    public BaseResponse<QuestionVO> getQuestion(Long questionId) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(questionId), ErrorCode.PARAMS_ERROR);
        QuestionVO questionVO = questionService.getQuestion(questionId);
        return RequestUtil.success(questionVO);
    }

    @PostMapping("/updateQuestion")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER, UserConstant.TYPE_ADMIN})
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        ThrowUtil.throwIf(ObjUtil.isEmpty(questionUpdateRequest), ErrorCode.PARAMS_ERROR);
        String questionContent = questionUpdateRequest.getQuestionContent();
        String questionAnswer = questionUpdateRequest.getQuestionAnswer();
        ThrowUtil.throwIf(StrUtil.hasEmpty(questionContent, questionAnswer), ErrorCode.PARAMS_ERROR, "题目内容或答案为空");
        Question question = new Question();
        BeanUtil.copyProperties(questionUpdateRequest, question);
        questionService.updateById(question);
        return RequestUtil.success(true);
    }

    @PostMapping("/deleteQuestion")
    @AuthCheck(mustRole = {UserConstant.TYPE_TEACHER, UserConstant.TYPE_ADMIN})
    public BaseResponse<Boolean> deleteQuestion(Long questionId) {
        boolean res = questionService.removeById(questionId);
        return RequestUtil.success(res);
    }


}
