package com.wsy.tizuobackend.service;

import com.wsy.tizuobackend.model.dto.paperSubmit.SubmitPaperRequest;
import com.wsy.tizuobackend.model.entity.Papersubmit;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 15790
* @description 针对表【tb_papersubmit(试卷提交表)】的数据库操作Service
* @createDate 2024-03-25 16:01:32
*/
public interface PapersubmitService extends IService<Papersubmit> {

    /**
     * 创建试卷提交表
     * @param examId
     * @param request
     * @return
     */
    Long createPaperSubmit(Long examId, HttpServletRequest request);

    Boolean getPaperIsSubmit(Long examId, HttpServletRequest request);

    /**
     * 提交试卷
     * @param submitPaperRequest
     * @param request
     * @return
     */
    Boolean submitPaper(SubmitPaperRequest submitPaperRequest, HttpServletRequest request);
}
