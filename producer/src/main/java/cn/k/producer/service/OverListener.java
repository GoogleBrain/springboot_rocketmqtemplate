package cn.k.producer.service;

import lombok.extern.slf4j.XSlf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RocketMQTransactionListener(txProducerGroup = "tx_producer_group")
public class OverListener implements RocketMQLocalTransactionListener {

    @Resource
    private OrderService orderService;
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        /**
         *  执行本地事务
         */
        try {
            String tr = message.getHeaders().get("trid").toString();
            System.out.println(">>>执行本地事务开始>>>>"+o);
            orderService.createorder(tr,o);//模拟执行本地事务
            System.out.println(">>>执行本地事务结束>>>>"+o);
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            return RocketMQLocalTransactionState.UNKNOWN;
        }

    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        /**
         * 消息回查,一般是本地事务入库的时候入库一条记录，然后查数据库来解决。
         */
        String tr = message.getHeaders().get("trid").toString();

        System.out.println("回查》》》》》》"+tr);

        return RocketMQLocalTransactionState.COMMIT;
    }
}
