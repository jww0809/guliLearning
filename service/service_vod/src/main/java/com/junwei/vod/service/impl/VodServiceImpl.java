package com.junwei.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.junwei.commonutils.R;
import com.junwei.vod.service.VodService;
import com.junwei.vod.utils.ConstantVodUtils;
import com.junwei.vod.utils.InitVodClient;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAliyun(MultipartFile file){
        String videoId = "";
        try {
            InputStream inputStream = file.getInputStream();
            //上传文件原始名称
            String fileName = file.getOriginalFilename();
            //上传以后显示的名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            videoId = response.getVideoId();
            if (response.isSuccess()) {
                String errorMessage = "阿里云上传成功：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.info(errorMessage);
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoId;
    }

    /**
     *
     * @param videoIdList ：多个video的id组成list
     */
    @Override
    public void deleteMultiVideoAliyun(List<String> videoIdList) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //将id组成的list连接为一个字符串
            String multiVideoId = StringUtils.join(videoIdList.toArray(), ",");
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(multiVideoId);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
