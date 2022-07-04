package com.junwei.eduservice.service;

import com.junwei.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.junwei.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
public interface EduChapterService extends IService<EduChapter> {
    //根据传入的课程id查询所有的章节和小节
    List<ChapterVo> findChapterVideo(String courseId);

    boolean removeChapterById(String chapterId);

    void removeChapterByCourseId(String courseId);
}
