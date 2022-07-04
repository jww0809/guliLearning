package com.junwei.eduservice.service;

import com.junwei.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author jww
 * @since 2022-04-25
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删小节，同时还要删除对应的视频文件
     * @param courseId
     */
    void removeVideoByCourseId(String courseId);
}
