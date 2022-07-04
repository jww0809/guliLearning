package com.junwei.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junwei.eduservice.entity.EduChapter;
import com.junwei.eduservice.entity.EduVideo;
import com.junwei.eduservice.entity.chapter.ChapterVo;
import com.junwei.eduservice.entity.chapter.VideoVO;
import com.junwei.eduservice.mapper.EduChapterMapper;
import com.junwei.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junwei.eduservice.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
@Slf4j
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    /**
     * //根据传入的课程id查询所有的章节和小节,其中一个章节对应多个小节，故需要封装成list<ChapterVo>
     * @param courseId 路径变量课程id
     * @return
     */
    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVo> findChapterVideo(String courseId) {
        //1先根据courseId查所有章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterWrapper);

        //2根据courseId查所有小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> videoList = videoService.list(videoWrapper);


        //最终返回的对象
        List<ChapterVo> chapterVideoFinalList = new ArrayList<>();

        //3 遍历所有章节,进行封装

        for (int i = 0; i < chapterList.size(); i++) {
            EduChapter chapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            chapterVideoFinalList.add(chapterVo);

            //遍历所有小节
            //一个章节对应多个小节，用videoVOList来存放
            //给定一个章节id,就要遍历整个小节列表，找到属于当前章节id下的所有小节
            List<VideoVO> videoVOList = new ArrayList<>();
            for (int n = 0; n < videoList.size(); n++) {
                EduVideo video = videoList.get(n);
                if(chapter.getId().equals(video.getChapterId())){
                    VideoVO videoVO = new VideoVO();
                    videoVO.setId(video.getId());
                    videoVO.setTitle(video.getTitle());
                    videoVOList.add(videoVO);
                }
            }
            chapterVo.setChildren(videoVOList);
        }
        //log.info(""+chapterVideoFinalList);
        return chapterVideoFinalList;
    }

    //根据章节id删除章节：如果当前章节下存在小节，则提示无法删除；
    @Override
    public boolean removeChapterById(String chapterId) {
        //通过chapter查询每个章节下是否存在小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(videoWrapper);
        int result = 0;
        if(count>0){
            System.out.println("该章节下还存在小节，请先删除所有小节！");
        }else {
            //删除章节
            result = baseMapper.deleteById(chapterId);

        }
        return result>0;
    }

    //根据课程id删除章节表中的对应的章节：保证course_id相等就行
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);

    }
}
