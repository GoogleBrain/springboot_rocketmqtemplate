package cn.k.producer.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

//    @Transactional
    public void createorder(String tr,Object o){
        System.out.println("订单保存"+o);
        System.out.println("入库一条单独的记录，方便rocketmq调用回查接口的时候使用。"+tr);
    }
}
