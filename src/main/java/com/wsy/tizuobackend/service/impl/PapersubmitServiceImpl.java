package com.wsy.tizuobackend.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.paperSubmit.SubmitPaperRequest;
import com.wsy.tizuobackend.model.entity.Achievement;
import com.wsy.tizuobackend.model.entity.Paper;
import com.wsy.tizuobackend.model.entity.Papersubmit;
import com.wsy.tizuobackend.model.entity.ScoreInfo;
import com.wsy.tizuobackend.service.*;
import com.wsy.tizuobackend.mapper.PapersubmitMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author 15790
 * @description 针对表【tb_papersubmit(试卷提交表)】的数据库操作Service实现
 * @createDate 2024-03-25 16:01:32
 */
@Service
public class PapersubmitServiceImpl extends ServiceImpl<PapersubmitMapper, Papersubmit> implements PapersubmitService {

    @Resource
    private UserService userService;

    @Resource
    private PaperService paperService;

    @Resource
    private QuestionService questionService;

    @Resource
    @Lazy
    private ExamService examService;

    @Resource
    private AchievementService achievementService;


    /**
     * 创建试卷提交
     *
     * @param examId
     * @param request
     * @return
     */
    @Override
    public Long createPaperSubmit(Long examId, HttpServletRequest request) {
        //获取学生id
        long userId = userService.getLoginUser(request).getId();
        //判断数据库中是否已有本场考试提交记录
        QueryWrapper<Papersubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("examId", examId).and(wrapper -> wrapper.eq("studentId", userId));
        ThrowUtil.throwIf(this.exists(queryWrapper), ErrorCode.PARAMS_ERROR.getCode(), "请勿重复提交");
        //新建提交表对象
        Papersubmit papersubmit = new Papersubmit();
        papersubmit.setStudentId(userId);
        papersubmit.setExamId(examId);
        //保存数据库
        ThrowUtil.throwIf(!this.save(papersubmit), ErrorCode.OPERATION_ERROR);
        //返回提交id
        return papersubmit.getPaperSubmitId();
    }

    @Override
    public Boolean getPaperIsSubmit(Long examId, HttpServletRequest request) {
        long id = userService.getLoginUser(request).getId();
        QueryWrapper<Papersubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId", id).and(papersubmitQueryWrapper -> papersubmitQueryWrapper.eq("examId", examId));
        Papersubmit papersubmit = this.getOne(queryWrapper);
        if (ObjUtil.isNotEmpty(papersubmit)) {
            return true;
        }
        return false;
    }

    /**
     * 提交试卷
     *
     * @param submitPaperRequest
     * @param request
     * @return
     */
    @Override
    public Boolean submitPaper(SubmitPaperRequest submitPaperRequest, HttpServletRequest request) {
        long studentId = userService.getLoginUser(request).getId();
        Long examId = submitPaperRequest.getExamId();
        String[] judgeInfo = submitPaperRequest.getJudgeInfo();
        //根据学生id和考试id获取已创建的考试提交表，如若没有，返回错误
        QueryWrapper<Papersubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId", studentId).and(papersubmitQueryWrapper -> papersubmitQueryWrapper.eq("examId", examId));
        Papersubmit paperSubmit = this.getOne(queryWrapper);
        ThrowUtil.throwIf(ObjUtil.isEmpty(paperSubmit), ErrorCode.OPERATION_ERROR, "交卷信息不存在");
        //根据考试id获取试卷id
        Long paperId = examService.getById(examId).getPaperId();
        //根据试卷id获取试卷对象
        Paper paper = paperService.getById(paperId);
        //获取试卷分数信息
        ScoreInfo scoreInfo = JSONUtil.toBean(paper.getScoreInfo(), ScoreInfo.class);
        //获取试题列表
        List<Long> questionList = JSONUtil.toList(paper.getQuestionIdList(), Long.class);
        //根据试题列表生成学生答案<questionId, answer>mapList
        Map<Long, String> studentAnswer = new HashMap<>();
        for (int i = 0; i < judgeInfo.length; i++) {
            studentAnswer.put(questionList.get(i), judgeInfo[i]);
        }
        //自动判断客观题
        Integer objectiveScore = getObjectiveScore(studentAnswer, questionList, scoreInfo);
        //生成成绩单
        Achievement achievement = new Achievement();
        achievement.setStudentId(studentId);
        achievement.setExamId(examId);
        achievement.setScore(objectiveScore);
        //存入成绩单
        ThrowUtil.throwIf(!achievementService.save(achievement), ErrorCode.OPERATION_ERROR, "成绩单生成失败");
        //更新考试提交表信息
        String judgeInfoStr = JSONUtil.toJsonStr(judgeInfo);
        paperSubmit.setJudgeInfo(judgeInfoStr);
        paperSubmit.setAnswerStatus(1);
        queryWrapper = queryWrapper.eq("paperSubmitId", paperSubmit.getPaperSubmitId());
        boolean update = this.update(paperSubmit, queryWrapper);
        ThrowUtil.throwIf(!update, ErrorCode.OPERATION_ERROR, "更新失败");
        return true;
    }

    /**
     * 计算客观题分数的自动判题机
     *
     * @param studentAnswer
     * @param questionList
     * @param scoreInfo
     * @return
     */
    public Integer getObjectiveScore(Map<Long, String> studentAnswer, List<Long> questionList, ScoreInfo scoreInfo) {
        Integer option = scoreInfo.getOption();
        Integer judge = scoreInfo.getJudge();
        AtomicReference<Integer> objectiveScore = new AtomicReference<>(0);
        //获取试题类型map
        Map<Long, Integer> questionTypeList = questionList.stream().collect(Collectors.toMap(item -> item, item -> questionService.getById(item).getQuestionType()));
        //获取试题答案map
        Map<Long, String> questionAnswer = questionList.stream().collect(Collectors.toMap(item -> item, item -> questionService.getById(item).getQuestionAnswer()));
        //遍历学生答案表，获取试题类型，如果是客观题，获取答案，与标准答案比较，计算得分
        studentAnswer.forEach((questionId, answer) -> {
            Integer type = questionTypeList.get(questionId);
            //选择题
            if (type == 0){
                //获取标准答案
                String standardAnswer = questionAnswer.get(questionId);
                //如果学生没写答案
                if (answer == null) {
                    objectiveScore.updateAndGet(v -> v + 0);
                }
                //比较正确答案
                if (answer.equals(standardAnswer)) {
                    objectiveScore.updateAndGet(v -> v + option);
                }
            }
            //判断题
            if (type == 1) {
                //获取标准答案
                String standardAnswer = questionAnswer.get(questionId);
                //如果学生没写答案
                if (answer == null) {
                    objectiveScore.updateAndGet(v -> v + 0);
                }
                //比较正确答案
                if (answer.equals(standardAnswer)) {
                    objectiveScore.updateAndGet(v -> v + judge);
                }
            }
        });
        return objectiveScore.get();
    }

}





