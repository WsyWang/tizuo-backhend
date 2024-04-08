package com.wsy.tizuobackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.sub.SubCreateRequest;
import com.wsy.tizuobackend.model.entity.Sub;
import com.wsy.tizuobackend.service.SubService;
import com.wsy.tizuobackend.mapper.SubMapper;
import org.springframework.stereotype.Service;

/**
* @author 15790
* @description 针对表【tb_sub(学科表)】的数据库操作Service实现
* @createDate 2024-03-20 14:05:39
*/
@Service
public class SubServiceImpl extends ServiceImpl<SubMapper, Sub>
    implements SubService{

    /**
     * 创建学科方法
     * @param subCreateRequest
     * @return
     */
    @Override
    public boolean createSub(SubCreateRequest subCreateRequest) {

        String subName = subCreateRequest.getSubName();
        ThrowUtil.throwIf(subName == null, ErrorCode.PARAMS_ERROR);
        QueryWrapper<Sub> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subName", subName);
        boolean exists = this.exists(queryWrapper);
        ThrowUtil.throwIf(exists, ErrorCode.PARAMS_ERROR);
        Sub sub = new Sub();
        sub.setSubName(subName);
        boolean result = this.save(sub);
        ThrowUtil.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return true;
    }

    /**
     * 根据学科名判断学科是否存在
     * @param subName
     * @return
     */
    @Override
    public boolean existBySubName(String subName) {
        QueryWrapper<Sub> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subName", subName);
        boolean exists = this.exists(queryWrapper);
        return exists;
    }
}




