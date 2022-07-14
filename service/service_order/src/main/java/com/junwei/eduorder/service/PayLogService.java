package com.junwei.eduorder.service;

import com.junwei.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author jww
 * @since 2022-07-08
 */
public interface PayLogService extends IService<PayLog> {

    //根据订单号生成二维码
    Map<String, Object> createNative(String orderNo);

    //查询订单支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //在order表中）更新支付状态,同时需要在支付表中添加支付记录
    void updatePayStatus(Map<String,String> map);
}
