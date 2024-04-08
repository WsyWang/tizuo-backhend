package com.wsy.tizuobackend.constant;

import java.io.Serializable;

/**
 * 考试常量类
 */
public class ExamConstant implements Serializable {
    private static final long serialVersionUID = 7945841820938149203L;

    private ExamConstant() {}

    public static final Integer NOT_START_EXAM = 0;

    public static final Integer START_EXAM = 1;

    public static final Integer FINISH_EXAM = 2;

}
