package com.wsy.tizuobackend.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.constant.ExamConstant;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.mapper.ExamMapper;
import com.wsy.tizuobackend.model.dto.exam.ExamCreateRequest;
import com.wsy.tizuobackend.model.dto.exam.GetExamListRequest;
import com.wsy.tizuobackend.model.entity.Cls;
import com.wsy.tizuobackend.model.entity.Exam;
import com.wsy.tizuobackend.model.vo.*;
import com.wsy.tizuobackend.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 15790
 * @description 针对表【tb_exam(考试信息表)】的数据库操作Service实现
 * @createDate 2024-03-24 12:00:31
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam>
        implements ExamService {


    @Resource
    private SubService subService;

    @Resource
    private ClsService clsService;

    @Resource
    private UserService userService;

    @Resource
    private PaperService paperService;

    @Resource
    private PapersubmitService papersubmitService;

    /**
     * 创建考试方法
     *
     * @param examCreateRequest
     * @param request
     */
    @Override
    public void createExam(ExamCreateRequest examCreateRequest, HttpServletRequest request) {
        String examName = examCreateRequest.getExamName();
        String examStartTime = examCreateRequest.getExamStartTime();
        String examEndTime = examCreateRequest.getExamEndTime();
        String subName = examCreateRequest.getSubName();
        Long classid = examCreateRequest.getClassid();
        String examDurationTime = examCreateRequest.getExamDurationTime();
        Long paperId = examCreateRequest.getPaperId();
        //判空
        ThrowUtil.throwIf(StrUtil.hasEmpty(examName, examStartTime, examEndTime, subName, examDurationTime), ErrorCode.PARAMS_ERROR);
        //判断学科名是否存在
        ThrowUtil.throwIf(!subService.existBySubName(subName), ErrorCode.PARAMS_ERROR);
        //判断班级是否存在
        Cls cls = clsService.getById(classid);
        ThrowUtil.throwIf(cls == null, ErrorCode.PARAMS_ERROR);
        //判断试卷是否存在
        ThrowUtil.throwIf(paperService.getById(paperId) == null, ErrorCode.PARAMS_ERROR);
        //获取教师id
        long teacherId = userService.getLoginUser(request).getId();
        //插入教师id
        Exam exam = new Exam();
        BeanUtils.copyProperties(examCreateRequest, exam);
        exam.setTeacherId(teacherId);
        //保存数据库
        ThrowUtil.throwIf(!this.save(exam), ErrorCode.OPERATION_ERROR);
        //将考试id加入到班级考试列表
        Long examId = exam.getExamId();
        clsService.addExam(examId, classid);
    }

    /**
     * 获取我的考试
     *
     * @param request
     * @return
     */
    @Override
    public List<MyExamVO> getMyExam(HttpServletRequest request) {
        UserVO loginUser = userService.getLoginUser(request);
        List<String> classIdStrList = loginUser.getClassIdList();
        if (classIdStrList.size() < 1) {
            return null;
        }
        List<Long> classLongId = classIdStrList.stream().map(Long::parseLong).collect(Collectors.toList());
        List<Cls> cls = clsService.listByIds(classLongId);
        List<Long> examId = new ArrayList<>();
        //通过班级列表获取所有的考试列表
        for (Cls cl : cls) {
            String examInfoIdList = cl.getExamInfoIdList();
            List<Long> longs = JSONUtil.toList(examInfoIdList, Long.class);
            if (longs.size() > 0) {
                examId.addAll(longs);
            }
        }
        if (examId.size() < 1) {
            return null;
        }
        List<Exam> examList = this.listByIds(examId);
        if (examList.size() < 1) {
            return null;
        }
        List<MyExamVO> myExamVOList = examList.stream().map(item -> {
            Long teacherId = item.getTeacherId();
            String userName = userService.getById(teacherId).getUserName();
            MyExamVO myExamVO = new MyExamVO();
            BeanUtils.copyProperties(item, myExamVO);
            myExamVO.setTeacherName(userName);
            return myExamVO;
        }).collect(Collectors.toList());
        return myExamVOList;
    }

    /**
     * 获取考试详情
     *
     * @param examId
     * @return
     */
    @Override
    public ExamDetailVO getStartExamDetail(Long examId) {
        //获取考试
        Exam exam = this.getById(examId);
        ThrowUtil.throwIf(ObjUtil.isEmpty(exam), ErrorCode.PARAMS_ERROR);
        //获取班级id获取班级名
        Long classId = exam.getClassid();
        Cls cls = clsService.getById(classId);
        String className = cls.getClassName();
        //获取教师id获取发起人
        Long teacherId = exam.getTeacherId();
        String teacherName = userService.getById(teacherId).getUserName();
        //判断考试是否到开始时间或已过结束时间
        DateTime startTime = DateUtil.parse(exam.getExamStartTime(), DatePattern.NORM_DATETIME_FORMAT);
        DateTime endTime = DateUtil.parse(exam.getExamEndTime(), DatePattern.NORM_DATETIME_FORMAT);
        if (startTime.before(DateUtil.date())) {
            exam.setExamStatus(ExamConstant.START_EXAM);
        }
        if (endTime.before(DateUtil.date())) {
            exam.setExamStatus(ExamConstant.FINISH_EXAM);
        }
        //更新数据库数据
        UpdateWrapper<Exam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("examId", examId);
        ThrowUtil.throwIf(!this.update(exam, updateWrapper), ErrorCode.OPERATION_ERROR);
        //转化成VO类
        ExamDetailVO examDetailVO = new ExamDetailVO();
        BeanUtils.copyProperties(exam, examDetailVO);
        examDetailVO.setClassName(className);
        examDetailVO.setTeacherName(teacherName);
        return examDetailVO;
    }

    /**
     * 开始考试
     *
     * @param examId
     * @param request
     * @return
     */
    @Override
    public StartExamVO startExam(Long examId, HttpServletRequest request) {
        //创建提交状态
        papersubmitService.createPaperSubmit(examId, request);
        //获取试卷id
        Exam exam = this.getById(examId);
        Long paperId = exam.getPaperId();
        //获取试题列表
        String questionIdListStr = paperService.getById(paperId).getQuestionIdList();
        List<Long> questionIdList = JSONUtil.toList(questionIdListStr, Long.class);
        //返回试题列表
        StartExamVO startExamVO = new StartExamVO();
        BeanUtils.copyProperties(exam, startExamVO);
        startExamVO.setQuestionIdList(questionIdList);
        return startExamVO;
    }

    /**
     * 获取做题界面考试信息
     *
     * @param examId
     * @return
     */
    @Override
    public ExamInfoVO getExamInfo(Long examId) {
        Exam exam = this.getById(examId);
        return ExamInfoVO.obj2VO(exam);
    }

    @Override
    public List<Long> getQuestionIdList(Long examId) {
        Exam exam = this.getById(examId);
        Long paperId = exam.getPaperId();
        List<Long> questionIdList = paperService.getQuestionIdList(paperId);
        return questionIdList;
    }

    /**
     * 教师获取考试列表接口
     *
     * @param getExamListRequest
     * @param request
     * @return
     */
    @Override
    public Page<ExamListVO> getExamListTeacher(GetExamListRequest getExamListRequest, HttpServletRequest request) {
        //  a. 获取所有传输字段，判断当前页传递是否为空
        String examName = getExamListRequest.getExamName();
        String subName = getExamListRequest.getSubName();
        long currentPage = getExamListRequest.getCurrentPage();
        long pageSize = getExamListRequest.getPageSize();
        ThrowUtil.throwIf(currentPage <= 0, ErrorCode.PARAMS_ERROR, "未填写当前页");
        //  b. 获取当前用户id（教师id）
        long teacherId = userService.getLoginUser(request).getId();
        //  c. 构建查询条件：不为空的都是查询条件
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(examName), "examName", examName);
        queryWrapper.eq(StrUtil.isNotEmpty(subName), "subName", subName);
        //  d. 构建分页，分页查询
        Page<Exam> examListPage = this.page(new Page<>(currentPage, pageSize), queryWrapper);
        //  e. 如果record大小为空，直接返回空分页对象
        if (examListPage.getRecords().size() == 0) {
            return new Page<>(currentPage, pageSize);
        }
        //  f. 取出record，stream流进行班级名查询、VO转换
        List<ExamListVO> examListVOS = examListPage.getRecords().stream()
                .map(exam -> {
                    String className = clsService.getById(exam.getClassid()).getClassName();
                    return ExamListVO.objToVO(exam, className);
                }).collect(Collectors.toList());
        //  g. 构建新分页，设置新的record
        Page<ExamListVO> examListVOPage = new Page<>(currentPage, pageSize, examListPage.getTotal());
        examListVOPage.setRecords(examListVOS);
        //  h. 返回数据
        return examListVOPage;
    }


}




