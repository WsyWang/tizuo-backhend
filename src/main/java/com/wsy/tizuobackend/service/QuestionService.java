package com.wsy.tizuobackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsy.tizuobackend.model.dto.question.QuestionCreateRequest;
import com.wsy.tizuobackend.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wsy.tizuobackend.model.vo.QuestionVO;

/**
* @author 15790
* @description 针对表【tb_question(题目表)】的数据库操作Service
* @createDate 2024-03-20 15:26:59
*/
public interface QuestionService extends IService<Question> {

    /**
     * 创建题目
     * @param questionCreateRequest
     */
    void createQuestion(QuestionCreateRequest questionCreateRequest);

    /**
     * 获取试题分页
     * @param currentPage
     * @return
     */
    Page<QuestionVO> getQuestionList(Long currentPage);

    QuestionVO getQuestion(Long questionId);
}
