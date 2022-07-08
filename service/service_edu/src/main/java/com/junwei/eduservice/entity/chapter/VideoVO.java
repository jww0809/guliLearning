package com.junwei.eduservice.entity.chapter;

import lombok.Data;

@Data
public class VideoVO {

    private String id;
    private String title;

    //后期在整合阿里云视频播放器添加上的属性
    private String videoSourceId;

}
