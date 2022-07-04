package com.junwei.eduservice.service.impl;

import com.junwei.eduservice.entity.EduChapter;
import com.junwei.eduservice.entity.EduCourse;
import com.junwei.eduservice.entity.EduCourseDescription;
import com.junwei.eduservice.entity.chapter.CoursePublishVo;
import com.junwei.eduservice.entity.vo.CourseInfoVo;
import com.junwei.eduservice.mapper.EduCourseMapper;
import com.junwei.eduservice.service.EduChapterService;
import com.junwei.eduservice.service.EduCourseDescriptionService;
import com.junwei.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junwei.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert==0){
            System.out.println("添加课程失败");
        }
        //添加的同时需要添加课程描述
        String courseId = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //特别注意需要设置描述表的id，与课程表进行一对一的关联
        eduCourseDescription.setId(courseId);
        descriptionService.save(eduCourseDescription);

        //由于在前端页面添加课程章节的时候需要获取到课程的id，所以直接在当前方法中返回课程id
        System.out.println("当前添加的课程的id为"+courseId);
        return courseId;
    }

    //根据课程id查询课程信息
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        EduCourseDescription description = descriptionService.getById(courseId);
        courseInfoVo.setDescription(description.getDescription());

        return courseInfoVo;
    }

    /**
     * //根据课程id（但传入的是courseInfoVo）修改课程信息
     * @param courseInfoVo :需要修改的对象
     */
    @Override
    public void updateCourseInfoById(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if(result ==0){
            System.out.println("修改失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        descriptionService.updateById(eduCourseDescription);

    }

    /**
     * 根据课程id得到即将发布的课程的信息
     * @param courseId
     * @return
     */
    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    /**
     * 删除课程
     * @param courseId
     * @return
     */
    @Override
    public boolean deleteCourseById(String courseId) {
        //先删除小节
        videoService.removeVideoByCourseId(courseId);

        //在删除章节
        chapterService.removeChapterByCourseId(courseId);

        //删除描述
        descriptionService.removeDescriptionByCourseId(courseId);

        int result = baseMapper.deleteById(courseId);
        return result > 0;

    }
}
