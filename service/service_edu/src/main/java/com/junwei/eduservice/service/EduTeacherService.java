package com.junwei.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junwei.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.junwei.eduservice.entity.vo.TeacherInfoVo;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-13
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //新增方法：根据Id查询到讲师信息，用于数据回显
    TeacherInfoVo getTeacherInfoById(String teacherId);

    //1 前端分页查询讲师的方法
    Map<String, Object> pageListWeb(Page<EduTeacher> page);
}
