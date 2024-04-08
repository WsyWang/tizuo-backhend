package com.wsy.tizuobackend.service;

import com.wsy.tizuobackend.model.dto.sub.SubCreateRequest;
import com.wsy.tizuobackend.model.entity.Sub;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 15790
* @description 针对表【tb_sub(学科表)】的数据库操作Service
* @createDate 2024-03-20 14:05:39
*/
public interface SubService extends IService<Sub> {

    /**
     * 创建学科方法
     * @param subCreateRequest
     * @return
     */
    boolean createSub(SubCreateRequest subCreateRequest);

    /**
     * 根据学科名判断是否存在学科
     * @param subName
     * @return
     */
    boolean existBySubName(String subName);
}
