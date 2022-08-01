package com.junwei.eduservice.controller;


import com.junwei.commonutils.R;
import com.junwei.eduservice.entity.subject.OneSubject;
import com.junwei.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/eduservice/subject")
@Api(description = "课程添加管理")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService subjectService;

    /**
     * 读取excel文件，存储课程信息到数据库中
     * @param file
     * @return
     */
    @PostMapping("/addSubject")
    //获取上传的文件.xsl
    public R addSubject(MultipartFile file){
        subjectService.importSubjectData(file,subjectService);
        return R.ok();
    }

    /**
     * 从数据库中查询到所有的课程信息，并封装成符合前端显示的格式
     * @return ：所有的一级二级分类信息
     */
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> oneSubjectList =  subjectService.getSubject();
        return R.ok().data("list",oneSubjectList);
    }

}

