package com.wsy.tizuobackend.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsy.tizuobackend.common.ErrorCode;
import com.wsy.tizuobackend.constant.PageConstant;
import com.wsy.tizuobackend.constant.UserConstant;
import com.wsy.tizuobackend.exception.ThrowUtil;
import com.wsy.tizuobackend.model.dto.cls.ClsCreateRequest;
import com.wsy.tizuobackend.model.dto.cls.ClsDeleteRequest;
import com.wsy.tizuobackend.model.dto.cls.ClsQueryRequest;
import com.wsy.tizuobackend.model.dto.cls.ClsUpdateRequest;
import com.wsy.tizuobackend.model.entity.*;
import com.wsy.tizuobackend.model.vo.ClsVO;
import com.wsy.tizuobackend.model.vo.UserVO;
import com.wsy.tizuobackend.service.*;
import com.wsy.tizuobackend.mapper.ClsMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 15790
 * @description 针对表【tb_cls(班级表)】的数据库操作Service实现
 * @createDate 2024-03-20 14:39:38
 */
@Service
public class ClsServiceImpl extends ServiceImpl<ClsMapper, Cls>
        implements ClsService {

    @Resource
    private SubService subService;

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private ExamService examService;

    @Resource
    private PapersubmitService papersubmitService;

    @Resource
    private AchievementService achievementService;

    /**
     * 创建班级方法
     *
     * @param clsCreateRequest
     * @param request
     * @return
     */
    @Override
    public String createCls(ClsCreateRequest clsCreateRequest, HttpServletRequest request) {
        //判空
        ThrowUtil.throwIf(ObjUtil.hasEmpty(clsCreateRequest, request), ErrorCode.PARAMS_ERROR);
        String className = clsCreateRequest.getClassName();
        String subName = clsCreateRequest.getSubName();
        //判空
        ThrowUtil.throwIf(StrUtil.hasEmpty(className, subName), ErrorCode.PARAMS_ERROR);
        //查询学科是否存在
        QueryWrapper<Sub> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subName", subName);
        boolean exists = subService.exists(queryWrapper);
        ThrowUtil.throwIf(!exists, ErrorCode.PARAMS_ERROR.getCode(), "学科不存在");
        //查询班级是否存在
        QueryWrapper<Cls> queryWrapperCls = new QueryWrapper<>();
        queryWrapperCls.eq("className", className);
        boolean existsCls = this.exists(queryWrapperCls);
        ThrowUtil.throwIf(existsCls, ErrorCode.PARAMS_ERROR);
        //获取当前登录教师id
        UserVO loginUser = userService.getLoginUser(request);
        long id = loginUser.getId();
        //构建插入数据库的对象
        Cls cls = new Cls();
        BeanUtils.copyProperties(clsCreateRequest, cls);
        cls.setTeacherId(id);
        //创建班级口令
        UUID uuid = UUID.fastUUID();
        String clsCode = uuid.toString();
        cls.setClassKey(clsCode);
        //插入数据库
        boolean result = this.save(cls);
        ThrowUtil.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return clsCode;
    }

    /**
     * 获取班级分页列表
     *
     * @param request
     * @param queryRequest
     * @return
     */
    @Override
    public Page<ClsVO> getClassList(HttpServletRequest request, ClsQueryRequest queryRequest) {
        //获取当前用户
        UserVO loginUser = userService.getLoginUser(request);
        //获取当前用户权限
        String userRole = loginUser.getUserRole();
        //获取查询参数
        String subName = queryRequest.getSubName();
        String className = queryRequest.getClassName();
        Long currentPage = queryRequest.getCurrentPage();
        //如果是管理员
        if (UserConstant.TYPE_ADMIN.equals(userRole)) {
            //展示所有的班级
            return queryClass(queryRequest);
        } else { //如果是教师
            //获取教师id
            long teacherId = loginUser.getId();
            return queryClass(queryRequest, teacherId);
        }

    }

    /**
     * 向班级添加考试
     *
     * @param examId
     * @return
     */
    @Override
    public boolean addExam(Long examId, Long classId) {
        //判空
        ThrowUtil.throwIf(ObjUtil.hasEmpty(examId, classId), ErrorCode.PARAMS_ERROR);
        //获取存在的班级
        Cls cls = this.getById(classId);
        ThrowUtil.throwIf(cls == null, ErrorCode.PARAMS_ERROR);
        //获取班级列表判断是否为空
        String examInfoIdList = cls.getExamInfoIdList();
        List<Long> longs = JSONUtil.toList(examInfoIdList, Long.class);
        longs.add(examId);
        //转为Json字符串存入班级
        String newExamId = longs.toString();
        cls.setExamInfoIdList(newExamId);
        QueryWrapper<Cls> clsQueryWrapper = new QueryWrapper<>();
        clsQueryWrapper.eq("classId", classId);
        ThrowUtil.throwIf(!this.update(cls, clsQueryWrapper), ErrorCode.OPERATION_ERROR);
        return true;
    }

    /**
     * 查询所有班级（分页）（管理员）
     * @param queryRequest
     * @return
     */
    @Override
    public Page<ClsVO> queryClass(ClsQueryRequest queryRequest) {
        //得到查询参数
        String subName = queryRequest.getSubName();
        String className = queryRequest.getClassName();
        Long currentPage = queryRequest.getCurrentPage();
        //构建查询条件
        QueryWrapper<Cls> clsQueryWrapper = new QueryWrapper<>();
        clsQueryWrapper.eq(StrUtil.isNotEmpty(subName), "subName", subName);
        clsQueryWrapper.eq(StrUtil.isNotEmpty(className), "className", className);
        clsQueryWrapper.orderByDesc("updateTime");
        //分页查询
        Page<Cls> clsPage = this.page(new Page<>(currentPage, PageConstant.PAGE_SIZE), clsQueryWrapper);
        if (clsPage.getRecords().size() < 1) {
            return new Page<>(currentPage, PageConstant.PAGE_SIZE);
        }
        //新建VO分页
        Page<ClsVO> clsVOPage = new Page<>(currentPage, PageConstant.PAGE_SIZE, clsPage.getTotal());
        //取出原分页records
        //用stream流的方式拷贝为VO对象
        List<ClsVO> clsVOList = clsPage.getRecords().stream().map(ClsVO::objToVO).collect(Collectors.toList());
        //添加到VO分页
        clsVOPage.setRecords(clsVOList);
        return clsVOPage;
    }

    /**
     * 查询教师班级（分页）
     * @param queryRequest
     * @param teacherId
     * @return
     */
    @Override
    public Page<ClsVO> queryClass(ClsQueryRequest queryRequest, Long teacherId) {
        //获取查询参数
        String subName = queryRequest.getSubName();
        String className = queryRequest.getClassName();
        Long currentPage = queryRequest.getCurrentPage();
        //构建查询条件
        QueryWrapper<Cls> clsQueryWrapper = new QueryWrapper<>();
        clsQueryWrapper.eq("teacherId", teacherId);
        clsQueryWrapper.eq(StrUtil.isNotEmpty(subName), "subName", subName);
        clsQueryWrapper.eq(StrUtil.isNotEmpty(className), "className", className);
        clsQueryWrapper.orderByDesc("updateTime");
        //分页查询
        Page<Cls> clsPage = this.page(new Page<>(currentPage, PageConstant.PAGE_SIZE), clsQueryWrapper);
        if (clsPage.getRecords().size() < 1) {
            return new Page<>(currentPage, PageConstant.PAGE_SIZE);
        }
        //新建VO分页
        Page<ClsVO> clsVOPage = new Page<>(currentPage, PageConstant.PAGE_SIZE, clsPage.getTotal());
        //取出原分页records
        //用stream流的方式拷贝为VO对象
        List<ClsVO> clsVOList = clsPage.getRecords().stream().map(ClsVO::objToVO).collect(Collectors.toList());
        //添加到VO分页
        clsVOPage.setRecords(clsVOList);
        return clsVOPage;
    }

    /**
     * 更新班级信息
     * @param updateRequest
     * @return
     */
    @Override
    public Boolean updateClass(ClsUpdateRequest updateRequest) {
        Long classId = updateRequest.getClassId();
        String className = updateRequest.getClassName();
        ThrowUtil.throwIf(ObjUtil.hasEmpty(className, classId), ErrorCode.PARAMS_ERROR, "班级名称为空");
        QueryWrapper<Cls> clsQueryWrapper = new QueryWrapper<>();
        clsQueryWrapper.eq("className", className);
        ThrowUtil.throwIf(this.exists(clsQueryWrapper), ErrorCode.PARAMS_ERROR, "班级名称已存在");
        Cls cls = new Cls();
        BeanUtils.copyProperties(updateRequest, cls);
        boolean updateResult = this.updateById(cls);
        ThrowUtil.throwIf(!updateResult, ErrorCode.OPERATION_ERROR, "更新数据失败");
        return true;
    }

    /**
     * 删除班级
     * @param deleteRequest
     * @return
     */
    @Override
    public Boolean deleteClass(ClsDeleteRequest deleteRequest) {
        Long classId = deleteRequest.getClassId();
        ThrowUtil.throwIf(ObjUtil.isEmpty(classId), ErrorCode.PARAMS_ERROR, "班级编号为空");
        ThrowUtil.throwIf(!this.removeById(classId), ErrorCode.OPERATION_ERROR, "删除班级失败");
        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        examQueryWrapper.eq("classId", classId);
        List<Exam> examList = examService.list(examQueryWrapper);
        List<Long> examIds = examList.stream().map(Exam::getExamId).collect(Collectors.toList());
        ThrowUtil.throwIf(!examService.removeByIds(examIds), ErrorCode.OPERATION_ERROR, "删除考试失败");
        for (Long examId : examIds) {
            QueryWrapper<Papersubmit> papersubmitQueryWrapper = new QueryWrapper<>();
            papersubmitQueryWrapper.eq("examId", examId);
            papersubmitService.remove(papersubmitQueryWrapper);
        }
        for (Long examId : examIds) {
            QueryWrapper<Achievement> achievementQueryWrapper = new QueryWrapper<>();
            achievementQueryWrapper.eq("examId", examId);
            achievementService.remove(achievementQueryWrapper);
        }
        return true;
    }
}




