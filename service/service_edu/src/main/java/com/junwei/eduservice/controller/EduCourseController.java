package com.junwei.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junwei.commonutils.R;
import com.junwei.eduservice.entity.EduCourse;
import com.junwei.eduservice.entity.EduTeacher;
import com.junwei.eduservice.entity.chapter.CoursePublishVo;
import com.junwei.eduservice.entity.vo.CourseInfoVo;
import com.junwei.eduservice.entity.vo.CourseQuery;
import com.junwei.eduservice.entity.vo.TeacherQuery;
import com.junwei.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
@Api(description = "课程管理")
@Slf4j
public class EduCourseController {

    /**
     * 添加课程
     */
    @Autowired
    EduCourseService courseService;
    @ApiOperation(value = "添加课程")
    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",courseId);

    }

    /**
     * 根据课程id查询课程信息
     */
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    /**
     * 根据课程id修改课程信息,注意这里传入的肯定不是课程id，而是整个修改的对象,使用requestBody
     */

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfoById(courseInfoVo);
        return R.ok();
    }

    /**
     * 根据课程id确认该即将发布的课程的信息
     * @param courseId
     * @return
     */
    @GetMapping("getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable("courseId") String courseId){
        CoursePublishVo publishCourseInfo = courseService.getPublishCourseInfo(courseId);
        return R.ok().data("publishCourseInfo",publishCourseInfo);
    }

    /**
     * 根据课程id发布该课程
     * @param courseId 课程id
     * @return
     */
    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable("courseId") String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        //courseService.saveOrUpdate(eduCourse);
        log.info("变化了...");
        return R.ok();
    }

    /**
     * 查询全部课程列表
     */
    @ApiOperation(value = "所有课程列表")
    @GetMapping("/getAllCourseList")
    public R getAllCourseList(){
        List<EduCourse> list = courseService.list(null);
        //链式编程的写法：成功就是R.ok()；失败就是R.error()
        return R.ok().data("item", list);
    }

    /**
     * 分页查询课程
     * @param current :当前页
     * @param pageSize：每页多少条数据
     * @return
     */
    @GetMapping("pageCourse/{current}/{pageSize}")
    public R pageTeacher(@PathVariable("current") long current,@PathVariable("pageSize") long pageSize){
        Page<EduCourse> pageParam = new Page<EduCourse>(current,pageSize);
        courseService.page(pageParam,null);

        long total = pageParam.getTotal();
        List<EduCourse> records = pageParam.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    /**
     * 条件查询：可以创建一个类 CourseQuery，把条件值封装到对象中；
     *
     * 注意点：@RequestBody 这里使用到这个注解,需要使用post方式，作用：使用json传递数据，把json数据封装到对应对象里面
     */
    @ApiOperation(value = "条件查询课程数据")
    @PostMapping("pageConditionalCourse/{current}/{limit}")
    public R pageConditionalTeacher(@PathVariable("current") long current,
                                    @PathVariable("limit") long limit,
                                    @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> eduCoursePage = new Page<>(current,limit);
        //查询的条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        //大于等于
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        courseService.page(eduCoursePage,wrapper);
        long total = eduCoursePage.getTotal();
        List<EduCourse> records = eduCoursePage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }


    /**
     * 根据课程ID删除课程信息：（视频）、小节、章节、描述、课程
     */

    @ApiOperation(value = "删除课程")
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable("courseId") String courseId){
        boolean flag = courseService.deleteCourseById(courseId);
        if(flag)
            return R.ok();
        else {
            return R.error().message("删除课程失败");
        }
    }

}

