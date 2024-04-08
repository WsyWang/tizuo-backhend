package com.wsy.tizuobackend.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wsy.tizuobackend.model.entity.Cls;
import com.wsy.tizuobackend.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ClsVO implements Serializable {

    private static final long serialVersionUID = -7498996618773664496L;

    /**
     * 班级id
     */
    private Long classId;

    /**
     * 班级名
     */
    private String className;

    /**
     * 学科名
     */
    private String subName;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 班级口令
     */
    private String classKey;

    public static ClsVO objToVO(Cls cls) {
        if (cls == null) {
            return null;
        }
        ClsVO clsVO = new ClsVO();
        BeanUtils.copyProperties(cls, clsVO);
        return clsVO;
    }

}
