package com.junwei.eduorder.service;

import com.junwei.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author jww
 * @since 2022-07-08
 */
public interface OrderService extends IService<Order> {

    //根据课程id和用户id，生成订单id
    String createOrder(String courseId, String memberId);
}
