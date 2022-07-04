package com.junwei.msmservice.service.impl;

import com.junwei.commonutils.HttpUtils;
import com.junwei.msmservice.service.MsmServie;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MsmServiceImpl implements MsmServie {

    /**
     * 发送验证码
     * @param phone
     * @param param
     * @return
     */
    @Override
    public boolean send(String phone, Map<String, Object> param) {
        String host = "http://dingxin.market.alicloudapi.com";
        String path = "/dx/sendSms";
        String appcode = "e390d1cfbb594b349199080883e686df";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105

        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        String newCode = "code:"+param.get("code");
        log.info("随机生成的验证码为："+newCode);
        querys.put("param", newCode);
        querys.put("tpl_id", "TP1711063");
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            HttpResponse response = HttpUtils.doPost(host, path,headers, querys, bodys);
            System.out.println(response.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
