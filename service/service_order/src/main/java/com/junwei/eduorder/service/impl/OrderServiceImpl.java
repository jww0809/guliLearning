package com.junwei.eduorder.service.impl;

import com.junwei.eduorder.utils.OrderNoUtil;
import com.junwei.eduorder.client.EduClient;
import com.junwei.eduorder.client.UcenterClient;
import com.junwei.eduorder.entity.Order;
import com.junwei.eduorder.mapper.OrderMapper;
import com.junwei.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junwei.entity.vo.CenterMemberNew;
import com.junwei.entity.vo.CourseInfoFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;
    /**
     * 根据课程id和用户id，生成订单id：远程调用edu模块，查询课程信息；远程调用ucenter模块，查询用户信息；
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public String createOrder(String courseId, String memberId) {
        //远程调用其他模块的方法
        CourseInfoFrom courseInfoFrom = eduClient.courseInfoForOrder(courseId);
        System.out.println("**********"+courseInfoFrom);
        CenterMemberNew memberNew = ucenterClient.getInfoUser(memberId);
        System.out.println("==========="+memberNew);
        //创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());  //随机唯一的订单号，和id不一样
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoFrom.getTitle());
        order.setCourseCover(courseInfoFrom.getCover());
        order.setTeacherName(courseInfoFrom.getTeacherName());
        order.setTotalFee(courseInfoFrom.getPrice());
        order.setMemberId(memberId);
        order.setMobile(memberNew.getMobile());
        order.setNickname(memberNew.getNickname());
        order.setStatus(0); //支付状态：0表示未支付
        order.setPayType(1); //支付类型：1表示微信
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
