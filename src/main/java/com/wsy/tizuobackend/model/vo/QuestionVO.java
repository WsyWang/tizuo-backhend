package com.wsy.tizuobackend.model.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wsy.tizuobackend.model.entity.Question;
import com.wsy.tizuobackend.model.jsonobject.QuestionOption;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * 题目VO
 */
@Data
public class QuestionVO implements Serializable {


    private static final long serialVersionUID = 5177896431132170220L;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 题目类型 -0 选择 -1 判断 -2 综合
     */
    private Integer questionType;

    /**
     * 题目内容
     */
    private String questionContent;

    /**
     * 题目选项（json对象）
     */
    private QuestionOption questionOption;

    /**
     * 题目答案
     */
    private String questionAnswer;

    /**
     * 学科名
     */
    private String subName;

    /**
     * 创建时间 默认CURRENT_TIMESTAMP
     */
    private Date createTime;

    /**
     * 对象转包装类
     * @return
     */
    public static QuestionVO objToVO(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question,questionVO);
        JSONObject parse = JSONUtil.parseObj(question.getQuestionOption());
        QuestionOption questionOption = JSONUtil.toBean(parse, QuestionOption.class);
        questionVO.setQuestionOption(questionOption);
        return questionVO;
    }
}
