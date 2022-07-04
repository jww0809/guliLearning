package com.junwei.eduservice.controller;


import com.junwei.commonutils.R;
import com.junwei.eduservice.client.VodClient;
import com.junwei.eduservice.entity.EduVideo;
import com.junwei.eduservice.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/eduservice/eduvideo")
@CrossOrigin
@Slf4j
public class EduVideoController {

    @Autowired
    private VodClient vodClient;

    @Autowired
    private EduVideoService videoService;

    /**
     * @param eduVideo
     * @return
     */

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok().data("video",eduVideo);
    }



    //根据小节id查询小节的信息
    @GetMapping("{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        System.out.println(videoId);
        EduVideo eduVideo = videoService.getById(videoId);
        System.out.println(eduVideo);
        return R.ok().data("video",eduVideo);
    }

    /**
     * 功能：删除小节时同时要删除小节里面的视频，但是由于小节管理在service_edu模块，而视频管理在service_vod模块，
     * 所以需要学习SpringCloud微服务相关知识：Nacos，通过将两个模块都注册在Nacos中，从而实现在小节模块中调用视频模块中的删除方法
     *
     * 具体做法：
     */
    //删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //新增：根据小节id查询得到视频源文件id，远程调用删除阿里云端视频
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            R result = vodClient.deleteVideo(videoSourceId);
            if(result.getCode()== 20000){
                log.warn("删除视频失败，熔断器...");
            }
        }

        boolean b = videoService.removeById(id);
        if (b){
            return R.ok();
        }else {
            return R.error().message("删除失败");
        }
    }

}

