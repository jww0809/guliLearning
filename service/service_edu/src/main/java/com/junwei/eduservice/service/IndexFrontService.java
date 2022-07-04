package com.junwei.eduservice.service;

import com.junwei.eduservice.entity.EduCourse;
import com.junwei.eduservice.entity.EduTeacher;

import java.util.List;

public interface IndexFrontService {
    List<EduCourse> getHotCourse();

    List<EduTeacher> getHotTeacher();

}
