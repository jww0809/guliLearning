package com.junwei.educenter.controller;


import com.junwei.commonutils.JwtUtils;
import com.junwei.commonutils.R;
import com.junwei.educenter.entity.UcenterMember;
import com.junwei.educenter.entity.vo.LoginVo;
import com.junwei.educenter.entity.vo.RegisterVo;
import com.junwei.educenter.service.UcenterMemberService;
import com.junwei.entity.vo.CenterMemberNew;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author jww
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        return R.ok().data("token",token);
    }

    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //使用jwt工具类根据request(实际上是从request中的header部分取出token字符串进行解析，所以前端需要使用到cookie和请求拦截器)查到用户id，再返回用户信息
    @GetMapping("getLoginInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember= memberService.getLoginInfo(memberId);
        return R.ok().data("userInfo",ucenterMember);
    }

    /**
     * 课程评论功能中，根据用户id获取用户的信息：
     * 注意这里的返回类型，不是R类型，而是common_utils模块中定义的一个公共类，用于返回
     * 生成订单也能共用该接口
     */

    @GetMapping("getInfoUser/{uid}")
    public CenterMemberNew getInfoUser(@PathVariable("uid") String uid){
        UcenterMember ucenterMember = memberService.getById(uid);
        System.out.println("==========="+ucenterMember);
        CenterMemberNew memberNew = new CenterMemberNew();
        BeanUtils.copyProperties(ucenterMember,memberNew);
        System.out.println("********"+memberNew);
        return memberNew;
    }

    /**
     * 查询某一天的注册人数
     */
    @GetMapping("getRegisterCount/{day}")
    public R getRegisterCount(@PathVariable("day") String day){
        Integer count = memberService.selectRegisterCount(day);
        return R.ok().data("registerCount",count);
    }
}

