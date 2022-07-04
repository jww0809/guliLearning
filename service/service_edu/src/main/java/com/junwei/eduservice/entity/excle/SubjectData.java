package com.junwei.eduservice.entity.excle;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 与excel文件对应的实体类，也就是课程分类文件
 */
@Data
public class SubjectData {

    @ExcelProperty(index = 0)   //对应文件第一列
    private String oneSubjectName; //一级分类

    @ExcelProperty(index = 1)    //对应文件第二列
    private String twoSubjectName; //二级分类

}
