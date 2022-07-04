package com.junwei.eduservice.service;

import com.junwei.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.junwei.eduservice.entity.chapter.CoursePublishVo;
import com.junwei.eduservice.entity.vo.CourseInfoVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程信息
    CourseInfoVo getCourseInfoById(String courseId);

    //根据课程id（但传入的是courseInfoVo）修改课程信息
    void updateCourseInfoById(CourseInfoVo courseInfoVo);

    //根据课程id得到即将发布的课程的信息
    CoursePublishVo getPublishCourseInfo(String courseId);

    boolean deleteCourseById(String courseId);


}
