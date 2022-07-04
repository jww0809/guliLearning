package com.junwei.eduservice.service;

import com.junwei.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.junwei.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-23
 */
public interface EduSubjectService extends IService<EduSubject> {

    void importSubjectData(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getSubject();
}
