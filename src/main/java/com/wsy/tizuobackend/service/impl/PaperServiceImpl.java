package com.wsy.tizuobackend.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.constant.PageConstant;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.paper.PaperCreateRequest;
import com.wsy.tizuobackend.model.dto.paper.PaperListNoPageRequest;
import com.wsy.tizuobackend.model.dto.paper.PaperQueryRequest;
import com.wsy.tizuobackend.model.entity.Paper;
import com.wsy.tizuobackend.model.vo.PaperVO;
import com.wsy.tizuobackend.model.vo.QuestionVO;
import com.wsy.tizuobackend.model.vo.UserVO;
import com.wsy.tizuobackend.service.PaperService;
import com.wsy.tizuobackend.mapper.PaperMapper;
import com.wsy.tizuobackend.service.SubService;
import com.wsy.tizuobackend.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 15790
 * @description 针对表【tb_paper(试卷表)】的数据库操作Service实现
 * @createDate 2024-03-20 16:19:22
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper>
        implements PaperService {

    @Resource
    private SubService subService;

    @Resource
    private UserService userService;

    /**
     * 创建试卷
     *
     * @param paperCreateRequest
     * @param request
     */
    @Override
    public void createPaper(PaperCreateRequest paperCreateRequest, HttpServletRequest request) {
        //判空
        String questionIdList = paperCreateRequest.getQuestionIdList();
        String scoreInfo = paperCreateRequest.getScoreInfo();
        String totalScore = paperCreateRequest.getTotalScore();
        String subName = paperCreateRequest.getSubName();
        ThrowUtil.throwIf(StrUtil.hasEmpty(questionIdList, subName, totalScore, scoreInfo), ErrorCode.PARAMS_ERROR);
        //判断学科是否存在
        ThrowUtil.throwIf(!subService.existBySubName(subName), ErrorCode.PARAMS_ERROR);
        //拷贝为Paper对象
        Paper paper = new Paper();
        BeanUtils.copyProperties(paperCreateRequest, paper);
        //查询教师id
        UserVO loginUser = userService.getLoginUser(request);
        long id = loginUser.getId();
        paper.setTeacherId(id);
        //插入数据库,判断插入是否成功
        ThrowUtil.throwIf(!this.save(paper), ErrorCode.OPERATION_ERROR);
    }

    @Override
    public List<Long> getQuestionIdList(Long paperId) {
        Paper paper = this.getById(paperId);
        String questionIdListStr = paper.getQuestionIdList();
        List<Long> questionIdList = JSONUtil.toList(questionIdListStr, Long.class);
        System.out.println(questionIdList);
        return questionIdList;
    }

    /**
     * 获取试卷列表（分页）
     *
     * @param request
     * @param queryRequest
     * @return
     */
    @Override
    public Page<PaperVO> getPaperList(HttpServletRequest request, PaperQueryRequest queryRequest) {
        Long teacherId = queryRequest.getTeacherId();
        Long paperId = queryRequest.getPaperId();
        String subName = queryRequest.getSubName();
        Long currentPage = queryRequest.getCurrentPage();

        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq(StrUtil.isNotEmpty(subName), "subName", subName);
        paperQueryWrapper.eq(ObjUtil.isNotEmpty(paperId), "paperId", paperId);
        paperQueryWrapper.eq(ObjUtil.isNotEmpty(teacherId), "teacherId", teacherId);

        Page<Paper> page = this.page(new Page<>(currentPage, PageConstant.PAGE_SIZE), paperQueryWrapper);
        if (page.getRecords().size() < 1) {
            return new Page<>();
        }
        List<PaperVO> paperVOS = page.getRecords().stream().map(PaperVO::objToVO).collect(Collectors.toList());
        Page<PaperVO> paperVOPage = new Page<>(currentPage, PageConstant.PAGE_SIZE, page.getTotal());
        paperVOPage.setRecords(paperVOS);
        return paperVOPage;
    }

    /**
     * 获取试卷列表（不分页）
     * @param paperListNoPageRequest
     * @return
     */
    @Override
    public List<PaperVO> getPaperListNoPage(PaperListNoPageRequest paperListNoPageRequest) {
        //1. 传递参数：
        //  a. 教师id，可选参数
        //  b. 学科名，根据当前选择的学科
        Long teacherId = paperListNoPageRequest.getTeacherId();
        String subName = paperListNoPageRequest.getSubName();
        //2. 参数校验
        ThrowUtil.throwIf(StrUtil.isEmpty(subName), ErrorCode.PARAMS_ERROR, "学科科目为空");
        //3. 构建查询条件
        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq(teacherId > 0, "teacherId", teacherId);
        paperQueryWrapper.eq("subName", subName);
        List<Paper> list = this.list(paperQueryWrapper);
        if (list.size() == 0) {
            return new ArrayList<>();
        }
        //4. 查询后使用Stream流进行VO转换
        List<PaperVO> paperVOList = list.stream().map(PaperVO::objToVO).collect(Collectors.toList());
        //5. 返回转换后的List
        return paperVOList;
    }

}




