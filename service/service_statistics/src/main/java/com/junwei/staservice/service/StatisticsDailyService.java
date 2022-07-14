package com.junwei.staservice.service;

import com.junwei.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author jww
 * @since 2022-07-10
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getRegisterCount(String day);

    Map<String,Object> queryCountByCondition(String type, String begin, String end);
}
