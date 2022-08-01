package com.junwei.educenter.controller;


import com.google.gson.Gson;
import com.junwei.commonutils.JwtUtils;
import com.junwei.educenter.entity.UcenterMember;
import com.junwei.educenter.service.UcenterMemberService;
import com.junwei.educenter.utils.ConstantWxUtils;
import com.junwei.educenter.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller  //这里不能用RestController，因为这里不需要返回数据
//@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    //2)获取扫描人信息，添加数据
    @GetMapping("callback")
    public String callback(String code,String state){
        //（1）二维码只要就扫，就能得到授权临时票据code
//        System.out.println("code="+code); code=061P6PFa1C7StD0z74Ha1VSJzZ3P6PFQ
//        System.out.println("state="+state); state=atguigu
        //（2）根据临时票据code，向认证服务器发送请求换取access_token和openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,ConstantWxUtils.WX_OPEN_APP_SECRET,code);

        try {
            String result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenUrl====="+result);
            //accessTokenUrl====={"access_token":"58_L0PBQAGa8FAUIQyIYPPVMEdohVlIQmf0nTM2uOvZr6A1b9_9vq29sy4PDkjNxf-r-nknTcJ5SiST76jXiHbSPbnonpeYpbccCLBHufJD0VA",
            // "expires_in":7200,"refresh_token":"58_4xvK7WVldar1czvxUPyJZG4BFLjm5swZJOMBD0kFXxQJ01Eh1blZdFgfdCz75Kgnw7NaQ8fBtonJYVWQjE9V5qBulKjAYVgOxFdeRY9M76k",
            // "openid":"o3_SC58m9cLiIT7lO6JxN6WbHkuo","scope":"snsapi_login","unionid":"oWgGz1K4Qwj5oQHtE-wDzI-AvR1s"}

            //（3）从result中取出access_token和openid
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(result, HashMap.class);
            String accessToken = (String) mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");


            //根据openid查询数据库中是否已经有该微信用户了
            UcenterMember member = memberService.getOpenIdInfo(openid);
            if(member ==null){
                //（4）访问微信的资源服务器，获取扫码的用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                String resultUserInfo = HttpClientUtils.get(userInfoUrl);
                //resultUserInfo{"openid":"o3_SC58m9cLiIT7lO6JxN6WbHkuo","nickname":"归零","sex":0,"language":"","city":"","province":"","country":"","headimgurl":"https:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/DYAIOgq83erocXUbjVsGKaA6a4jwgAfaRjnxVGzqwCNw0Ce36G1Q2RrDDvNT80FStWJKCUT7hg5K2gHkdZMnlg\/132","privilege":[],"unionid":"oWgGz1K4Qwj5oQHtE-wDzI-AvR1s"}
                System.out.println("resultUserInfo"+resultUserInfo);
                HashMap userInfoMap = gson.fromJson(resultUserInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String)userInfoMap.get("headimgurl");
                System.out.println("nickname========"+nickname);
                System.out.println("headimgurl======"+headimgurl);

                member = new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                memberService.save(member);

            }

            //为了解决cookie不能跨域的问题，为了能在首页获取到扫码用户的昵称、头像等信息，需要在这里使用jwt生成token字符串，
            // 直接拼接在请求地址的后面，这样在别处也能根据地址栏的token字符串取出扫码用户的信息
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            //回到首页
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            //微信登录失败
            return "";
        }
    }



    //1)生成要去扫描的二维码，访问固定的地址
    @GetMapping("/login")
   public String getWxCode(){
        // 微信开放平台授权baseUrl,拼接参数，后面的%s相当于占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            //对redirectUrl进行编码
            URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 回调地址
        String url = String.format(baseUrl,ConstantWxUtils.WX_OPEN_APP_ID,redirectUrl,"atguigu");
        return "redirect:"+url;
    }

}
