package com.junwei.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.junwei.commonutils.R;
import com.junwei.vod.service.VodService;
import com.junwei.vod.utils.ConstantVodUtils;
import com.junwei.vod.utils.InitVodClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
@Slf4j
public class VodController {

    @Autowired
    private VodService vodService;
    /**
     * 上传视频
     * @param file 你在上传界面添加的视频 所在的的本地文件
     * @return
     */
    @PostMapping("/uploadVideo")
    public R uploadVideo(@RequestParam("file") MultipartFile file) throws Exception {
        String videoId = vodService.uploadVideoAliyun(file);
        log.info(videoId);
        return R.ok().data("videoId",videoId);
    }

    /**
     *  根据videoId删除视频
     */
    @DeleteMapping("removeVideo/{AliyunvideoId}")
    public R deleteVideo(@PathVariable("AliyunvideoId") String AliyunvideoId) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(AliyunvideoId);
            client.getAcsResponse(request);
            return R.ok().message("删除成功");
        } catch (ClientException e) {
            e.printStackTrace();
            return R.error().message("删除视频失败");
        }
    }

    //在删除课程的时候，如果多个小节都存在视频，那就需要根据多个视频id删除相应的视频
    @DeleteMapping("deleteBatchVideo")
    public R deleteMultiVideo(@RequestParam("videoList") List<String> videoList){
        vodService.deleteMultiVideoAliyun(videoList);
        return R.ok().message("多个视频删除成功");
    }

}

