package com.junwei.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junwei.eduservice.entity.EduCourseDescription;
import com.junwei.eduservice.entity.EduVideo;
import com.junwei.eduservice.mapper.EduCourseDescriptionMapper;
import com.junwei.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    //根据课程id删除课程描述表中的描述记录
    @Override
    public void removeDescriptionByCourseId(String courseId) {
        QueryWrapper<EduCourseDescription> wrapper = new QueryWrapper<>();
        wrapper.eq("id",courseId);
        baseMapper.delete(wrapper);
    }
}
