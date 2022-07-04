package com.junwei.msmservice.controller;


import com.junwei.commonutils.R;
import com.junwei.msmservice.service.MsmServie;
import com.junwei.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmServie msmServie;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("send/{phone}")
    public R sendMessage(@PathVariable("phone") String phone){
        //实现5分钟内验证码有效：
        // 1)如果redis中存在验证码 就直接拿到；
        String code = (String) redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }

        //2)redis中取不到说明过期了，重新生成新的验证码
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean isSend = msmServie.send(phone,param);
        if(isSend){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }

        return R.error().message("验证码发送失败");
    }


}
