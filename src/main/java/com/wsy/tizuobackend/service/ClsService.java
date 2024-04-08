package com.wsy.tizuobackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsy.tizuobackend.model.dto.cls.ClsCreateRequest;
import com.wsy.tizuobackend.model.dto.cls.ClsDeleteRequest;
import com.wsy.tizuobackend.model.dto.cls.ClsQueryRequest;
import com.wsy.tizuobackend.model.dto.cls.ClsUpdateRequest;
import com.wsy.tizuobackend.model.entity.Cls;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wsy.tizuobackend.model.vo.ClsVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 15790
* @description 针对表【tb_cls(班级表)】的数据库操作Service
* @createDate 2024-03-20 14:39:38
*/
public interface ClsService extends IService<Cls> {

    /**
     * 创建班级方法
     *
     * @param clsCreateRequest
     * @param request
     * @return
     */
    String createCls(ClsCreateRequest clsCreateRequest, HttpServletRequest request);

    /**
     * 获取班级分页列表
     * @param request
     * @param queryRequest
     * @return
     */
    Page<ClsVO> getClassList(HttpServletRequest request, ClsQueryRequest queryRequest);

    /**
     * 向班级添加考试
     * @param examId
     * @return
     */
    boolean addExam (Long examId, Long classId);

    /**
     * 查询所有班级（分页）（管理员）
     * @param queryRequest
     * @return
     */
    Page<ClsVO> queryClass(ClsQueryRequest queryRequest);

    /**
     * 查询教师班级（分页）
     * @param queryRequest
     * @param teacherId
     * @return
     */
    Page<ClsVO> queryClass(ClsQueryRequest queryRequest, Long teacherId);

    /**
     * 更新班级信息
     * @param updateRequest
     * @return
     */
    Boolean updateClass(ClsUpdateRequest updateRequest);

    /**
     * 删除班级
     * @param deleteRequest
     * @return
     */
    Boolean deleteClass(ClsDeleteRequest deleteRequest);
}
