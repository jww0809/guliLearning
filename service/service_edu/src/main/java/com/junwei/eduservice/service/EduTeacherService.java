package com.junwei.eduservice.service;

import com.junwei.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.junwei.eduservice.entity.vo.TeacherInfoVo;

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
}
