package cn.k.producer.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class Producer1 {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("/index1")
    public String index1() {
//        rocketMQTemplate.convertAndSend("TopicTest","this is my first convertAndSend "+new Date());

        /**
         * 参数1：主题；参数2：消息内容
         */
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.syncSend("TopicTest", "this is my first syncSend " + new Date() + ",序号：" + i);
        }
        return "当前时间>>>" + new Date();
    }

    /**
     * 异步发送
     * @param topic
     * @return
     */
    @RequestMapping("/index2/{topic}")
    public String index2(@PathVariable("topic")String topic) {
        rocketMQTemplate.asyncSend(topic, "asyncSend" + new Date(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("onSuccess>>>"+sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("onException>>>"+throwable);
            }
        });
        return "当前时间>>>" + new Date();
    }

    /**
     * 单向发送
     * @param topic
     * @return
     */
    @RequestMapping("/index3/{topic}")
    public String index3(@PathVariable("topic")String topic) {
        rocketMQTemplate.sendOneWay(topic,"this is sendOneWay"+new Date());
        return "当前时间>>>" + new Date();
    }
    /**
     * 顺序消息,需要消费端配置一下，表示按顺序消费。consumeMode = ConsumeMode.ORDERLY。默认消费是并发消费
     */
    @RequestMapping("/index4/{topic}")
    public String index4(@PathVariable("topic")String topic) {
        for(int i=0;i<10;i++) {
            rocketMQTemplate.syncSendOrderly(topic, "this is sendOneWay" + new Date()+",序号："+i, "1");
        }
        return "当前时间>>>" + new Date();
    }
}
