package com.junwei.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junwei.eduservice.entity.EduSubject;
import com.junwei.eduservice.entity.excle.SubjectData;
import com.junwei.eduservice.service.EduSubjectService;

import java.sql.Wrapper;

/**
 * SubjectExcelListener这个监听器不能注入到Spring中，需要自己new出来，
 * 所以考虑使用构造函数的方式，传入service对象进行数据库访问
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {


    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取Excel文件内容一行一行地读取内容

    /**
     *
     * @param subjectData 读取的excel文件对象，有两列，第一列是一级分类，第二列是二级分类
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            System.out.println("excel文件为空");
        }
        String name = subjectData.getOneSubjectName();
        EduSubject eduOneSubject = existOneSubjectName(subjectService, name);
        //添加一级分类
        if(eduOneSubject == null){
            eduOneSubject = new EduSubject();
            eduOneSubject.setTitle(name);
            eduOneSubject.setParentId("0");
            subjectService.save(eduOneSubject);
        }

        //添加二级分类
        String pid = eduOneSubject.getId();
        String twoSubjectName = subjectData.getTwoSubjectName();
        EduSubject eduTwoSubject = existTwoSubjectName(subjectService, twoSubjectName, pid);
        if(eduTwoSubject ==null){
            eduTwoSubject = new EduSubject();
            eduTwoSubject.setParentId(pid);
            eduTwoSubject.setTitle(twoSubjectName);
            subjectService.save(eduTwoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    //由于在添加excel时有可能第一列和第二列有相同的内容，不能重复添加
    //判断数据库中是否已经存在当前的title
    public EduSubject existOneSubjectName(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<EduSubject>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id",0);
        return subjectService.getOne(queryWrapper);
    }

    public EduSubject existTwoSubjectName(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<EduSubject>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id",pid);
        return subjectService.getOne(queryWrapper);
    }
}
