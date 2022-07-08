package com.junwei.eduorder.service.impl;

import com.junwei.eduorder.entity.Order;
import com.junwei.eduorder.mapper.OrderMapper;
import com.junwei.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author jww
 * @since 2022-07-08
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
