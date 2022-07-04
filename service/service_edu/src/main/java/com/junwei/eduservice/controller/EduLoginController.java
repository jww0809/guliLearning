package com.junwei.eduservice.controller;

import com.junwei.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
//用于解决跨域问题
@CrossOrigin
public class EduLoginController {

    //login
    @PostMapping("login")
    public R login(){
        return R.ok().data("toker","admin");
    }

    //info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic%2Fcb%2F9f%2Fbc%2Fcb9fbccaf2a6814994df8d5e72b82b1c.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653052499&t=1c8f51ccaa8bd46423d51186a5a433cd");
    }

}
