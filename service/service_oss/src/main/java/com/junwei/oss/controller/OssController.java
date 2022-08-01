package com.junwei.oss.controller;

import com.junwei.commonutils.R;
import com.junwei.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.POST;

/**
 * 上传头像
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@Slf4j
//@CrossOrigin
public class OssController {

    @Autowired
    OssService ossService;

    @PostMapping("/upload")
    public R uploadAvadar(MultipartFile file){
        //存储到数据库中的是一个头像url地址,所以返回url
        // alt+enter 创建方法
        String url = ossService.uploadAvadarService(file);
        log.info(url);
        return R.ok().data("url",url);

    }


}
