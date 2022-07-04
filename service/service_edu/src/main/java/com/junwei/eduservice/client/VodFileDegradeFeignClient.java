package com.junwei.eduservice.client;

import com.junwei.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断器：VodClient中的类中方法出错的话，会执行相应的方法
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public R deleteVideo(String AliyunvideoId) {
        return R.error().message("删除单个视频失败");
    }

    @Override
    public R deleteMultiVideo(List<String> videoList) {
        return R.error().message("删除多个视频失败");
    }
}
