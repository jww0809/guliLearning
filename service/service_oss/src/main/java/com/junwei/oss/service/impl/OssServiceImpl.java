package com.junwei.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.junwei.oss.service.OssService;
import com.junwei.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service

public class OssServiceImpl implements OssService {

    /**
     * 上传头像到oss
     */
    @Override
    public String uploadAvadarService(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            String avatarName = file.getOriginalFilename();
            //加上uuid避免重名问题
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            //按时间进行分类文件
            String date = new DateTime().toString("yyyy/MM/dd");

            avatarName = date +"/"+uuid +avatarName;

            //第二个参数上传到oss中文件路径和文件名，这里直接用头像的源文件名
            ossClient.putObject(bucketName, avatarName, inputStream);
            // https://junwei-edu-guli.oss-cn-beijing.aliyuncs.com/zz.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+avatarName;
            return url;
        }  catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            ossClient.shutdown();
        }

    }

}
