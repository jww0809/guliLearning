package com.junwei.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junwei.commonutils.R;
import com.junwei.eduservice.entity.EduTeacher;
import com.junwei.eduservice.entity.vo.TeacherInfoVo;
import com.junwei.eduservice.entity.vo.TeacherQuery;
import com.junwei.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 * 方便在Swagger中进行显示：
     * 定义在类上：@Api
     * 定义在方法上：@ApiOperation
     * 定义在参数上：@ApiParam
 *
 * 项目的访问地址:http://localhost:8001/eduservice/teacher/findAll
 * 进行swagger测试 http://localhost:8001/swagger-ui.html
 * @author testjava
 * @since 2022-04-13
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    EduTeacherService eduTeacherService;

    /**
     * 查询全部的教师列表
     * @return
     */
    //这里的/可以写，也可以不写。
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher(){

        List<EduTeacher> list = eduTeacherService.list(null);
        //链式编程的写法：成功就是R.ok()；失败就是R.error()
        return R.ok().data("item", list);
    }

    /**
     * 逻辑删除讲师
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable("id")String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 分页查询讲师
     * @param current :当前页
     * @param pageSize：每页多少条数据
     * @return
     */
    @GetMapping("pageTeacher/{current}/{pageSize}")
    public R pageTeacher(@PathVariable("current") long current,@PathVariable("pageSize") long pageSize){
        Page<EduTeacher> pageParam = new Page<EduTeacher>(current,pageSize);
        eduTeacherService.page(pageParam,null);

        long total = pageParam.getTotal();
        List<EduTeacher> records = pageParam.getRecords();
        return R.ok().data("total",total).data("records",records);

    }

    /**
     * 条件查询：可以创建一个类 TeacherQuery，把条件值封装到对象中；
     *
     * 注意点：@RequestBody 这里使用到这个注解,需要使用post方式，作用：使用json传递数据，把json数据封装到对应对象里面
     */
    @ApiOperation(value = "条件查询讲师数据")
    @PostMapping("pageConditionalTeacher/{current}/{limit}")
    public R pageConditionalTeacher(@PathVariable("current") long current,
                                    @PathVariable("limit") long limit,
                                    @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);
        //查询的条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
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

        eduTeacherService.page(eduTeacherPage,wrapper);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    /**
     * 新增teacher对象
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.save(teacher);
        if (flag)
            return R.ok();
        return R.error();
    }

    /**
     * 根据Id查询讲师
     */
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/findTeacherById/{id}")
    public R findTeacherById(@PathVariable("id") String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    /**
     * 根据id修改讲师
     */
    @ApiOperation(value = "修改讲师(必须写Id)")
    @PostMapping("/updateTeacherById")
    public R updateTeacherById(@RequestBody EduTeacher eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);
        if (update)
            return R.ok();
        return R.error();
    }

    /**
     * 新增方法：根据Id查询到讲师信息，用于数据回显
     *
     * 用不上的东西，测试失败
     */
//    @ApiOperation(value = "根据ID查询讲师信息")
//    @GetMapping("getTeacherInfo/{teacherId}")
//    public R getTeacherInfo(@PathVariable("teacherId") String teacherId){
//        TeacherInfoVo teacherInfo = eduTeacherService.getTeacherInfoById(teacherId);
//        return R.ok().data("teacherInfo",teacherInfo);
//    }
}

