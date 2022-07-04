package com.junwei.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {

    private String id;
    private String title;

    private List<VideoVO> children = new ArrayList<>();
}
