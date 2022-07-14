package com.junwei.staservice.schedule;

import com.junwei.staservice.service.StatisticsDailyService;
import com.junwei.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataUnit;

import java.util.Date;

/**
 * 创建定时任务的类：使用 @Scheduled(cron = 一个【七子表达式】)，按照表达式执行定义的方法task1
 */

@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService dailyService;
    /**
     * 每天临晨1点，查询前一天的数据，进行数据统计并添加到数据表中
     */
    @Scheduled(cron = "0 0 1 * * ?" ) //虽然是七子表达式，但是只能写6位，否则报错
    public void task1(){
        dailyService.getRegisterCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));

    }



}
