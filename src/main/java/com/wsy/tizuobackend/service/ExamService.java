package com.wsy.tizuobackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsy.tizuobackend.model.dto.exam.ExamCreateRequest;
import com.wsy.tizuobackend.model.dto.exam.GetExamListRequest;
import com.wsy.tizuobackend.model.dto.exam.ListExamCreateRequest;
import com.wsy.tizuobackend.model.entity.Exam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wsy.tizuobackend.model.vo.*;

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

    /**
     * 教师获取考试列表接口
     * @param getExamListRequest
     * @param request
     * @return
     */
    Page<ExamListVO> getExamListTeacher(GetExamListRequest getExamListRequest, HttpServletRequest request);

    /**
     * 创建考试列表
     * @param listExamCreateRequest
     * @param request
     */
    void teacherCreateExamByList(ListExamCreateRequest listExamCreateRequest, HttpServletRequest request);

    /**
     * 管理员获取考试列表接口
     * @param getExamListRequest
     * @return
     */
    Page<ExamListVO> getExamListAdmin(GetExamListRequest getExamListRequest);
}
