package com.junwei.staservice.controller;


import com.junwei.commonutils.R;
import com.junwei.staservice.client.StaClient;
import com.junwei.staservice.entity.StatisticsDaily;
import com.junwei.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author jww
 * @since 2022-07-10
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    /**
     * (远程调用)获取某天注册人数
     */
    @GetMapping("registerCount/{day}")
    public R getRegisterCount(@PathVariable("day") String day){
        staService.getRegisterCount(day);
        return R.ok();
    }

    /**
     * 根据输入类型以及起始、结束日期，查到数据库中 这一段时间内的数据，用于图表显示
     */

    @GetMapping("queryCount/{type}/{begin}/{end}")
    public R queryCountByCondition(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = staService.queryCountByCondition(type,begin,end);
        return R.ok().data(map);

    }

}

