package com.junwei.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装一级学科和二级学科的对象
 */

@Data
public class ChapterVo {

    private String id;
    private String title;

    private List<VideoVO> children = new ArrayList<>();
}
