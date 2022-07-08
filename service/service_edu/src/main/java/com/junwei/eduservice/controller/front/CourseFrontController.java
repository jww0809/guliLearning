package com.junwei.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junwei.commonutils.R;
import com.junwei.eduservice.entity.EduCourse;
import com.junwei.eduservice.entity.chapter.ChapterVo;
import com.junwei.eduservice.entity.vo.frontvo.CourseBaseInfoVo;
import com.junwei.eduservice.entity.vo.frontvo.CourseQueryVo;
import com.junwei.eduservice.service.EduChapterService;
import com.junwei.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;
    /**
     * 1 （前端）课程 条件查询带分页
     */
    @PostMapping("conditionQuery/{currentPage}/{limit}")
    public R queryCourseList(@PathVariable("currentPage") long currentPage, @PathVariable("limit")long limit,
                             @RequestBody(required = false)CourseQueryVo courseQueryVo){//required = false表示查询条件courseQueryVo可以为null
        Page<EduCourse> coursePage = new Page<>(currentPage,limit);
        Map<String,Object> map = courseService.PageListWeb(coursePage,courseQueryVo);
        return R.ok().data(map);

    }

    /**
     * 2 (前端)根据课程id 查询到课程的基本信息和章节信息
     */

    @GetMapping("getBaseCourseInfo/{courseId}")
    public R getBaseCourseInfo(@PathVariable("courseId")String courseId){

        //基本信息
        CourseBaseInfoVo courseBaseInfo = courseService.getCourseBaseInfo(courseId);

        //章节信息
        List<ChapterVo> chapterVoList = chapterService.findChapterVideo(courseId);
        return R.ok().data("courseBaseInfo",courseBaseInfo).data("chapterList",chapterVoList);
    }


}
