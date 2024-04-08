package com.wsy.tizuobackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsy.tizuobackend.model.entity.Achievement;
import com.wsy.tizuobackend.service.AchievementService;
import com.wsy.tizuobackend.mapper.AchievementMapper;
import org.springframework.stereotype.Service;

/**
* @author 15790
* @description 针对表【tb_achievement(成绩表)】的数据库操作Service实现
* @createDate 2024-04-02 11:42:45
*/
@Service
public class AchievementServiceImpl extends ServiceImpl<AchievementMapper, Achievement>
    implements AchievementService{

}




