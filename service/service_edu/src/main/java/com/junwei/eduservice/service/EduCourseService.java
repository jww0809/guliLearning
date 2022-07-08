package com.junwei.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junwei.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.junwei.eduservice.entity.chapter.CoursePublishVo;
import com.junwei.eduservice.entity.vo.CourseInfoVo;
import com.junwei.eduservice.entity.vo.frontvo.CourseBaseInfoVo;
import com.junwei.eduservice.entity.vo.frontvo.CourseQueryVo;

import java.util.List;
import java.util.Map;

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

    //1 （前端）课程 条件查询带分页
    Map<String, Object> PageListWeb(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo);
    //2 (前端)  根据课程id 查询到课程的基本信息
    CourseBaseInfoVo getCourseBaseInfo(String courseId);
}
