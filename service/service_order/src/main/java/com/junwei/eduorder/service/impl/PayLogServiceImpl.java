package com.junwei.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.JsonObject;
import com.junwei.eduorder.entity.Order;
import com.junwei.eduorder.entity.PayLog;
import com.junwei.eduorder.mapper.PayLogMapper;
import com.junwei.eduorder.service.OrderService;
import com.junwei.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junwei.eduorder.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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


    @Autowired
    private OrderService orderService;
    /**
     * //根据订单号生成二维码
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, Object> createNative(String orderNo) {

        //1）根据订单编号 查询到订单
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        //这里为什么用orderService，因为使用baseMapper查询到的是关于PayLog实体类的数据，无法查询Order实体类的数据
        Order order = orderService.getOne(wrapper);

        Map<String,String> m = new HashMap<>();
        //1、设置支付参数
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("nonce_str", WXPayUtil.generateNonceStr()); //生成随机的字符串
        m.put("body", order.getCourseTitle());//课程标题
        m.put("out_trade_no", orderNo);//订单号
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");//价格，需要转成字符串
        m.put("spbill_create_ip", "127.0.0.1");//
        m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
        m.put("trade_type", "NATIVE");

        //2、HTTPClient来根据微信提供的固定URL访问第三方接口并且传递参数
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

        try {
            //client设置参数
            client.setHttps(true);
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.post();
            //3、返回第三方的数据
            String content_xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content_xml);

            //4、封装返回结果集

            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }


    /**
     * 查询订单支付状态 :注意这里需要发送client请求进行查询
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //4、转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //5、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * （在order表中）更新支付状态,同时需要在支付表中添加支付记录
     */
    @Override
    public void updatePayStatus(Map<String,String> map) {
        //拿到订单号
        String orderNo = map.get("out_trade_no");
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        //已经支付过了
        if(order.getStatus() ==1){
            return;
        }
        //更新状态
        order.setStatus(1);
        orderService.updateById(order);

        //在支付表中添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型：1 表示微信
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id")); //交易流水号
        payLog.setAttr(JSONObject.toJSONString(map));//其他属性
        baseMapper.insert(payLog);

    }
}
