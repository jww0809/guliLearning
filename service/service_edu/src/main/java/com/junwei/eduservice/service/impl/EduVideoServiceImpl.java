package com.junwei.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junwei.eduservice.client.VodClient;
import com.junwei.eduservice.entity.EduVideo;
import com.junwei.eduservice.entity.chapter.VideoVO;
import com.junwei.eduservice.mapper.EduVideoMapper;
import com.junwei.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //通过vodClient找到VodClient接口，远程调用service_vod中删除视频的方法
    @Autowired
    private VodClient vodClient;

    //根据课程id删小节，同时还要删除对应的视频文件
    @Override
    public void removeVideoByCourseId(String courseId) {
        //先根据一条课程id，找到多条对应的视频id（如果有的话），并删除
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        videoWrapper.select("video_source_id");//只查一列的方法，务必记住！！
        List<EduVideo> eduVideoList = baseMapper.selectList(videoWrapper);

        //将eduVideoList转为videoIdList
        List<String> videoIdList = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            String videoSourceId = eduVideoList.get(i).getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoIdList.add(videoSourceId);
            }

        }
        if(videoIdList.size()>0){
            vodClient.deleteMultiVideo(videoIdList);
        }
        //
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
