package com.junwei.eduservice.mapper;

import com.junwei.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.junwei.eduservice.entity.chapter.CoursePublishVo;
import com.junwei.eduservice.entity.vo.frontvo.CourseBaseInfoVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String courseId);

    //根据课程id 查询到课程的基本信息
    CourseBaseInfoVo getCourseBaseInfo(String courseId);
}
