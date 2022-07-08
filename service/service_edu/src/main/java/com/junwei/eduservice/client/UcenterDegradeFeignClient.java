package com.junwei.eduservice.client;

import com.junwei.commonutils.R;
import com.junwei.eduservice.entity.UcenterMember;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class UcenterDegradeFeignClient implements UcenterClient{

    //调用ucenter模块失败执行的方法
    @Override
    public UcenterMember getInfoUser(String uid) {
        return null;
    }
}
