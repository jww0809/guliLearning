package com.junwei.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {

    private String id;
    private String title;

    //设置一个一级分类下存在多个二级分类的结构
    private List<TwoSubject> children = new ArrayList<>();
}
