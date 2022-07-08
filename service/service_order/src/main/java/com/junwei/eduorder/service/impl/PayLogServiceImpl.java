package com.junwei.eduorder.service.impl;

import com.junwei.eduorder.entity.PayLog;
import com.junwei.eduorder.mapper.PayLogMapper;
import com.junwei.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author jww
 * @since 2022-07-08
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
