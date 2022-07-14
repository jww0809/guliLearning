package com.junwei.eduorder.client;


import com.junwei.eduorder.client.impl.EduClientImpl;
import com.junwei.entity.vo.CourseInfoFrom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-edu",fallback = EduClientImpl.class)
public interface EduClient {

    // //Edu模块 根据课程id查询课程信息的方法，
    // 细节：1地址要补全  2 此处@PathVariable("courseId")后面的courseId决定不能少
    @GetMapping("/eduservice/coursefront/courseInfoForOrder/{courseId}")
    CourseInfoFrom courseInfoForOrder(@PathVariable("courseId") String courseId);
}
