package com.junwei.eduservice.client;

import com.junwei.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
//调用服务的名称，这个服务在另外的一个模块中
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)


@Component
/**
 * 这里定义一个被调用模块的接口
 */
public interface VodClient {

    /**
     *  根据videoId删除视频
     */
    @DeleteMapping("/eduvod/video/removeVideo/{AliyunvideoId}")
    R deleteVideo(@PathVariable("AliyunvideoId") String AliyunvideoId);

    /**
     *  根据videoList删除多条视频：在EduCourseService-->deleteCourseById()需要用到
     */
    @DeleteMapping("/eduvod/video/deleteBatchVideo")
    R deleteMultiVideo(@RequestParam("videoList") List<String> videoList);


}
