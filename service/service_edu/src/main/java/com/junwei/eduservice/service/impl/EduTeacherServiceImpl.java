package com.junwei.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junwei.eduservice.entity.EduTeacher;
import com.junwei.eduservice.entity.vo.TeacherInfoVo;
import com.junwei.eduservice.mapper.EduTeacherMapper;
import com.junwei.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    //1 前端分页查询讲师的方法(这里不用element-ui，尝试使用map)
    @Override
    public Map<String, Object> pageListWeb(Page<EduTeacher> page) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //经过这个方法，page中已经有值了
        baseMapper.selectPage(page,wrapper);

        List<EduTeacher> records = page.getRecords();
        long current = page.getCurrent();
        long total = page.getTotal();
        long size = page.getSize();
        long pages = page.getPages();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String,Object> map = new HashMap<>();
        map.put("items",records);

        map.put("current",current);
        map.put("total",total);
        map.put("size",size);
        map.put("pages",pages);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }
}
