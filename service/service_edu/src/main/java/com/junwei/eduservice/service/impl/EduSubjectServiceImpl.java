package com.junwei.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junwei.eduservice.entity.EduSubject;
import com.junwei.eduservice.entity.excle.SubjectData;
import com.junwei.eduservice.entity.subject.OneSubject;
import com.junwei.eduservice.entity.subject.TwoSubject;
import com.junwei.eduservice.listener.SubjectExcelListener;
import com.junwei.eduservice.mapper.EduSubjectMapper;
import com.junwei.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-23
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void importSubjectData(MultipartFile file,EduSubjectService subjectService) {

        try {
            InputStream stream = file.getInputStream();
            //在这里调用读取Excel文件的方法，也就是会用到监听器
            EasyExcel.read(stream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public List<OneSubject> getSubject() {
        //查所有的一级分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper();
        wrapper.eq("parent_id", '0');
        List<EduSubject> eduSubjectList = baseMapper.selectList(wrapper);

        //查所有的二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper();
        twoWrapper.ne("parent_id", '0');
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);

        List<OneSubject> finalSubjectList = new ArrayList<>();
        //封装一级分类
        for (int i = 0; i < eduSubjectList.size(); i++) {
            OneSubject oneSubject = new OneSubject();

            EduSubject eduSubject = eduSubjectList.get(i);
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setLabel(eduSubject.getTitle());
            //换一种写法,使用工具类BeanUtils
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            //封装二级分类
            //oneSubject的children属性应该设置为twoSubject
            //同时在一级分类循环内部遍历所有的二级分类，因为一个一级分类对应者多个二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject eduSubjectTwo = twoSubjectList.get(m);
                if(eduSubjectTwo.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
//                    String twoTitle = eduSubjectTwo.getTitle();
//                    twoSubject.setId(eduSubjectTwo.getParentId());
//                    twoSubject.setTitle(twoTitle);
                    BeanUtils.copyProperties(eduSubjectTwo,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjectList;
    }
}
