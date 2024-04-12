package com.wsy.tizuobackend.model.dto;

import com.wsy.tizuobackend.constant.PageConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用分页对象
 */
@Data
public class CommonPage implements Serializable {

    private static final long serialVersionUID = 8620458997999347688L;

    /**
     * 当前页面
     */
    private long currentPage;

    /**
     * 页面数据大小
     */
    private long pageSize = PageConstant.PAGE_SIZE;

}
