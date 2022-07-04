package com.junwei.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junwei.commonutils.R;
import com.junwei.eduservice.entity.EduCourse;
import com.junwei.eduservice.entity.EduTeacher;
import com.junwei.eduservice.service.EduCourseService;
import com.junwei.eduservice.service.EduTeacherService;
import com.junwei.eduservice.service.IndexFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用于前端 前台页面的控制器
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {


    @Autowired
    private IndexFrontService indexFrontService;


    /**
     * 查询前8条热门课程,查询前4名讲师
     */
    @GetMapping("index")
    public R index(){
        List<EduCourse> courseList = indexFrontService.getHotCourse();
        List<EduTeacher> teacherList = indexFrontService.getHotTeacher();
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
