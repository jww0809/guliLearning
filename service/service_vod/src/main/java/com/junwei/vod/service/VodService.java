package com.junwei.vod.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VodService {

    String uploadVideoAliyun(MultipartFile file) throws Exception;

    void deleteMultiVideoAliyun(List<String> videoList);
}
