package com.junwei.eduservice.service.impl;

import com.junwei.eduservice.entity.EduTeacher;
import com.junwei.eduservice.entity.vo.TeacherInfoVo;
import com.junwei.eduservice.mapper.EduTeacherMapper;
import com.junwei.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-13
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //新增方法：根据Id查询到讲师信息，用于数据回显
    @Override
    public TeacherInfoVo getTeacherInfoById(String teacherId) {
        EduTeacher teacher = baseMapper.selectById(teacherId);
        TeacherInfoVo teacherInfoVo = new TeacherInfoVo();
        BeanUtils.copyProperties(teacher,teacherInfoVo);
        return teacherInfoVo;
    }
}
