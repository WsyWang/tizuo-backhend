package com.wsy.tizuobackend.model.vo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.wsy.tizuobackend.model.entity.Exam;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回考试详情
 */
@Data
public class ExamInfoVO implements Serializable {

    private static final long serialVersionUID = 210460366566522111L;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 考试科目
     */
    private String subName;

    /**
     * 考试持续时间
     */
    private int examDurationTime;

    public static ExamInfoVO obj2VO(Exam exam) {
        if (exam == null) {
            return null;
        }
        ExamInfoVO examInfoVO = new ExamInfoVO();
        BeanUtils.copyProperties(exam, examInfoVO);
        String examDurationTime = exam.getExamDurationTime();
        DateTime parse = DateUtil.parse(examDurationTime, "hh:mm:ss");
        int hour = parse.hour(true);
        int minute = parse.minute();
        int second = parse.second();
        int total = 1000 * second + 1000 * 60 * minute + 1000 * 60 * 60 * hour;
        examInfoVO.setExamDurationTime(total);
        return examInfoVO;
    }

}
