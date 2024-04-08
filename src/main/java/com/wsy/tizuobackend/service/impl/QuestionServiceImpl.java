package com.wsy.tizuobackend.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.constant.PageConstant;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.question.QuestionCreateRequest;
import com.wsy.tizuobackend.model.entity.Question;
import com.wsy.tizuobackend.model.vo.QuestionVO;
import com.wsy.tizuobackend.model.vo.UserVO;
import com.wsy.tizuobackend.service.QuestionService;
import com.wsy.tizuobackend.mapper.QuestionMapper;
import com.wsy.tizuobackend.service.SubService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author 15790
 * @description 针对表【tb_question(题目表)】的数据库操作Service实现
 * @createDate 2024-03-20 15:26:59
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    @Resource
    private SubService subService;

    /**
     * 创建题目方法
     *
     * @param questionCreateRequest
     */
    @Override
    public void createQuestion(QuestionCreateRequest questionCreateRequest) {
        Integer questionType = questionCreateRequest.getQuestionType();
        String questionContent = questionCreateRequest.getQuestionContent();
        String questionOption = questionCreateRequest.getQuestionOption();
        String questionAnswer = questionCreateRequest.getQuestionAnswer();
        String subName = questionCreateRequest.getSubName();
        //判空
        ThrowUtil.throwIf(ObjUtil.isEmpty(questionType), ErrorCode.PARAMS_ERROR);
        ThrowUtil.throwIf(StrUtil.hasEmpty(questionContent, questionOption, questionAnswer, subName), ErrorCode.PARAMS_ERROR);
        //判断学科名是否存在，如果存在则继续
        boolean existBySubName = subService.existBySubName(subName);
        ThrowUtil.throwIf(!existBySubName, ErrorCode.PARAMS_ERROR);
        //拷贝成Question类型
        Question question = new Question();
        BeanUtils.copyProperties(questionCreateRequest, question);
        //插入数据库
        boolean save = this.save(question);
        //判断是否插入成功
        ThrowUtil.throwIf(!save, ErrorCode.OPERATION_ERROR);
    }

    /**
     * 获取试题分页
     *
     * @param currentPage
     * @return
     */
    @Override
    public Page<QuestionVO> getQuestionList(Long currentPage) {
        //新建分页，获取Question分页
        Page<Question> page = this.page(new Page<>(currentPage, PageConstant.PAGE_SIZE));
        //如果为空直接返回
        if (page.getRecords().size() < 1) {
            return new Page<>(currentPage, PageConstant.PAGE_SIZE, page.getTotal());
        }
        //新建VO分页
        Page<QuestionVO> questionVOPage = new Page<>(currentPage, PageConstant.PAGE_SIZE, page.getTotal());
        //取出records，stream流转换为VO,设置进VO中
        questionVOPage.setRecords(page.getRecords().stream().map(QuestionVO::objToVO).collect(Collectors.toList()));
        //返回
        return questionVOPage;
    }

    @Override
    public QuestionVO getQuestion(Long questionId) {
        Question question = this.getById(questionId);
        QuestionVO questionVO = QuestionVO.objToVO(question);
        return questionVO;
    }
}




