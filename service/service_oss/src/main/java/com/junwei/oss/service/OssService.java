package com.junwei.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface OssService {
    //上传头像到oss
    String uploadAvadarService(MultipartFile file);
}
