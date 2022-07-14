package com.junwei.eduservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderClientImpl implements OrderClient {

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        log.error("当前课程并未购买，无法观看");
        return false;
    }
}
