package com.wsy.tizuobackend.service;

import com.wsy.tizuobackend.model.dto.exam.ExamCreateRequest;
import com.wsy.tizuobackend.model.entity.Exam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wsy.tizuobackend.model.vo.ExamDetailVO;
import com.wsy.tizuobackend.model.vo.ExamInfoVO;
import com.wsy.tizuobackend.model.vo.MyExamVO;
import com.wsy.tizuobackend.model.vo.StartExamVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 15790
* @description 针对表【tb_exam(考试信息表)】的数据库操作Service
* @createDate 2024-03-24 12:00:31
*/
public interface ExamService extends IService<Exam> {

    /**
     * 创建考试方法
     * @param examCreateRequest
     * @param request
     */
    void createExam(ExamCreateRequest examCreateRequest, HttpServletRequest request);

    /**
     * 获取我的考试
     * @param request
     * @return
     */
    List<MyExamVO> getMyExam(HttpServletRequest request);

    /**
     * 获取考试详情
     * @param examId
     * @return
     */
    ExamDetailVO getStartExamDetail(Long examId);

    /**
     * 开始考试
     * @param examId
     * @param request
     * @return
     */
    StartExamVO startExam(Long examId, HttpServletRequest request);

    /**
     * 获取做题界面考试信息
     * @param examId
     * @return
     */
    ExamInfoVO getExamInfo(Long examId);

    /**
     * 获取题目id列表
     * @param examId
     * @return
     */
    List<Long> getQuestionIdList(Long examId);
}
