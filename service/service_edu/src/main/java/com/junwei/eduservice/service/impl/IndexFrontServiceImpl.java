package com.junwei.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junwei.commonutils.R;
import com.junwei.eduservice.entity.EduCourse;
import com.junwei.eduservice.entity.EduTeacher;
import com.junwei.eduservice.service.EduCourseService;
import com.junwei.eduservice.service.EduTeacherService;
import com.junwei.eduservice.service.IndexFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexFrontServiceImpl implements IndexFrontService {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 获取首页热门课程，并且存放与redis缓存中
     * @return
     */
    @Cacheable(value = "hotCourse",key = "'hotCourseList'")
    @Override
    public List<EduCourse> getHotCourse() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(wrapper);
        return courseList;

    }

    /**
     * 获取首页热门教师，并且存放与redis缓存中
     * @return
     */
    @Cacheable(value = "hotTeacher",key = "'hotTeacherList'")
    @Override
    public List<EduTeacher> getHotTeacher() {
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);
        return teacherList;
    }
}
