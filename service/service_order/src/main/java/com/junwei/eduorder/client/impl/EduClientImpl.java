package com.junwei.eduorder.client.impl;

import com.junwei.eduorder.client.EduClient;
import com.junwei.entity.vo.CourseInfoFrom;
import org.springframework.stereotype.Component;

@Component
public class EduClientImpl  implements EduClient {

    @Override
    public CourseInfoFrom courseInfoForOrder(String courseId) {
        return null;
    }
}
