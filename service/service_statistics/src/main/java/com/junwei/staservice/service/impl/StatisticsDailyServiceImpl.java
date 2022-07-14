package com.junwei.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junwei.commonutils.R;
import com.junwei.staservice.client.StaClient;
import com.junwei.staservice.entity.StatisticsDaily;
import com.junwei.staservice.mapper.StatisticsDailyMapper;
import com.junwei.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author jww
 * @since 2022-07-10
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private StaClient staClient;

    /**
     * 远程调用接口，获取注册人数
     * @param day
     */
    @Override
    public void getRegisterCount(String day) {

        //存在的问题：相同的日期，也就是date_calculated字段会重复出现在表中

        //解决：1）(老师的)每次向statistics_daily添加记录时，先根据日期day删除表中存在的记录，再进行添加，这样就没问题
        //      2) 直接根据日期day判断是否有记录，有的话就更新数据
        R r = staClient.getRegisterCount(day);
        Integer registerCount = (Integer) r.getData().get("registerCount");

        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(registerCount);
        sta.setDateCalculated(day);

        sta.setCourseNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setVideoViewNum(RandomUtils.nextInt(100,200));

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        if(baseMapper.selectCount(wrapper) > 0){
            baseMapper.update(sta,wrapper);
            return;
        }
        baseMapper.insert(sta);
    }

    /**
     * 根据输入类型以及起始、结束日期，查到数据库中 这一段时间内的数据，用于图表显示
     * @param type 输入类型:登录人数、注册人数、课程播放数量等
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String,Object> queryCountByCondition(String type, String begin, String end) {

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        //注意加上这个，根据统计日期date_calculated和统计类型 两个字段来查
        wrapper.select("date_calculated",type);

        //前端需要得到json数组形式的数据，所以这里设置两个list用于存放统计数据，并将两个list放到map中，在前端可直接从map中取到两个list

        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);
        List<Integer> countList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();

        for (int i = 0; i < dailyList.size(); i++) {
            StatisticsDaily daily = dailyList.get(i);
            //统计日期
            dateList.add(daily.getDateCalculated());

            //分类型来统计类型
            switch (type){
                case "login_num":
                    countList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    countList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    countList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    countList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("countList",countList);
        map.put("dateList",dateList);
        return map;
    }
}
