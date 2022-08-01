package com.junwei.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junwei.commonutils.R;
import com.junwei.eduservice.entity.EduCourse;
import com.junwei.eduservice.entity.EduTeacher;
import com.junwei.eduservice.service.EduCourseService;
import com.junwei.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 定义一些 前端关于讲师模块的接口
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //1 前端分页查询讲师的方法(不是条件查询)
    @GetMapping("/conditionPageTeacher/{currentPage}/{limit}")
    public R conditionPageQueryTeacher(@PathVariable("currentPage") long currentPage, @PathVariable("limit") long limit){
        Page<EduTeacher> page = new Page<>(currentPage,limit);

        Map<String,Object> map = teacherService.pageListWeb(page);
        return R.ok().data(map);
    }
    //2 根据讲师id查询讲师基本信息和讲师所讲的课程
    @GetMapping("teacherFrontInfo/{teacherId}")
    public R getTeacherInfo(@PathVariable("teacherId")String teacherId){
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("eduTeacher",eduTeacher).data("teacherCourseList",courseList);
    }

}
