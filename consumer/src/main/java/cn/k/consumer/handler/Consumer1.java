package cn.k.consumer.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

//发送短信的服务
@Service
@Slf4j
@RocketMQMessageListener(consumerGroup = "shop-user", topic = "TopicTest")
public class Consumer1 implements RocketMQListener<String> {
    @Override
    public void onMessage(String order) {
        log.info("收到一个订单信息{},接下来发送短信", JSON.toJSONString(order));
    }
}