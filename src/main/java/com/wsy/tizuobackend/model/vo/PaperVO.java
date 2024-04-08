package com.wsy.tizuobackend.model.vo;

import cn.hutool.core.date.DateUtil;
import com.wsy.tizuobackend.model.entity.Cls;
import com.wsy.tizuobackend.model.entity.Paper;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
public class PaperVO implements Serializable {

    private static final long serialVersionUID = -7498996618773664496L;

    /**
     * 试卷id
     */
    private Long paperId;

    /**
     * 总分
     */
    private String totalScore;

    /**
     * 科目
     */
    private String subName;

    /**
     * 更新时间
     */
    private String updateTime;

    public static PaperVO objToVO(Paper paper) {
        if (paper == null) {
            return null;
        }
        PaperVO paperVO = new PaperVO();
        BeanUtils.copyProperties(paper, paperVO);
        String formatUpdateTime = DateUtil.format(paper.getUpdateTime(), "yyyy-MM-dd");
        paperVO.setUpdateTime(formatUpdateTime);
        return paperVO;
    }

}
