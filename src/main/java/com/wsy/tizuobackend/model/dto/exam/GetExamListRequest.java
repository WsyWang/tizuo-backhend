package com.wsy.tizuobackend.model.dto.exam;

import com.wsy.tizuobackend.model.dto.CommonPage;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取考试列表请求体
 */
@Data
public class GetExamListRequest extends CommonPage implements Serializable {

    private static final long serialVersionUID = 8620458997999347688L;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 考试科目
     */
    private String subName;

}
