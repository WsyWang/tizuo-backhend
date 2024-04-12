package com.wsy.tizuobackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsy.tizuobackend.model.dto.paper.PaperCreateRequest;
import com.wsy.tizuobackend.model.dto.paper.PaperListNoPageRequest;
import com.wsy.tizuobackend.model.dto.paper.PaperQueryRequest;
import com.wsy.tizuobackend.model.entity.Paper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wsy.tizuobackend.model.vo.PaperVO;
import com.wsy.tizuobackend.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 15790
* @description 针对表【tb_paper(试卷表)】的数据库操作Service
* @createDate 2024-03-20 16:19:23
*/
public interface PaperService extends IService<Paper> {

    /**
     * 创建试卷
     * @param paperCreateRequest
     * @param request
     */
    void createPaper(PaperCreateRequest paperCreateRequest, HttpServletRequest request);

    /**
     * 获取题目id列表
     */
    List<Long> getQuestionIdList(Long paperId);

    /**
     * 获取试卷列表（分页）
     * @param request
     * @param queryRequest
     * @return
     */
    Page<PaperVO> getPaperList(HttpServletRequest request, PaperQueryRequest queryRequest);

    /**
     * 获取试卷列表（不分页）
     * @param paperListNoPageRequest
     * @return
     */
    List<PaperVO> getPaperListNoPage(PaperListNoPageRequest paperListNoPageRequest);
}
