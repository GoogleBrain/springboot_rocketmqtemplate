package cn.k.producer.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
public class Producer2 {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("/t1/{tr}")
    public String index1(@PathVariable("tr")String tr) {
        log.info("发送半事务消息.....");
        Message<String> hehe = MessageBuilder.withPayload("hehe"+new Date()).setHeader("trid",tr).build();
        rocketMQTemplate.sendMessageInTransaction("tx_producer_group","TopicTest",hehe,"hehe");
        return "当前时间>>>>>"+new Date();
    }
}