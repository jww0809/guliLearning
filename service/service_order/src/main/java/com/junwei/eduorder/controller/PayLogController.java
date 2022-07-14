package com.junwei.eduorder.controller;


import com.junwei.commonutils.R;
import com.junwei.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author jww
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    /**
     * 1 根据订单号生成二维码：因为不光有二维码，还有其他信息，所以返回了一个map，方便在前端进行取值
     * @param orderNo 订单号
     * @return
     */
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable("orderNo") String orderNo){
        Map<String,Object> map = payLogService.createNative(orderNo);
        System.out.println("=======生成的二维码集合："+map);
        return R.ok().data(map);
    }

    /**
     * 2 根据订单号查询订单支付状态
     */
    @GetMapping("getOrderPayInfo/{orderNo}")
    public R getOrderPayInfo(@PathVariable("orderNo") String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("（*********查询订单状态的二维码集合："+map);
        if(map == null){
            return R.error().message("支付失败");
        }
        //固定的，判断是不是success
        if(map.get("trade_state").equals("SUCCESS")){
            //更新支付表的状态,注意：这里是传递map
            payLogService.updatePayStatus(map);
            return R.ok().message("支付成功");
        }

        return R.ok().code(25000).message("正在支付中");
    }

}

