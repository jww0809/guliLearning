package com.junwei.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junwei.commonutils.JwtUtils;
import com.junwei.commonutils.R;
import com.junwei.eduorder.entity.Order;
import com.junwei.eduorder.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author jww
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 1根据课程id和用户id，生成订单id
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("saveOrder/{courseId}")
    public R saveOrder(@PathVariable("courseId")String courseId, HttpServletRequest request){
        //获取用户的id：因为这个时候已经是登录状态了，用户的数据已经存在cookie的header部分，所以可以根据Jwt工具类从token中取出用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        log.info("memberId==============="+memberId);
        //登录成功的
        if(!StringUtils.isEmpty(memberId)){
            //根据课程id查课程信息，根据用户id查用户信息，从而生成订单
            String orderNo = orderService.createOrder(courseId,memberId);
            return R.ok().data("orderNo",orderNo);
        }
        //未登录的
        return R.error().code(28004).message("请先登录");

    }

    /**
     * 2 根据订单号查询订单信息，注意不是订单表的id字段
     */
    @GetMapping("getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable("orderNo") String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order",order);
    }

    /**
     * 根据课程id和用户id查询订单表 当前订单是否已经购买（status为1表示已经购买），用于前端的显示
     */
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        //已经支付
        int count = orderService.count(wrapper);
        if(count>0){
            return true;
        }else {
            return false;
        }
    }

}

