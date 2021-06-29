package com.kafka.producer.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@Slf4j
@Service
public class MsgCurrentService {

    @Async
    public void readMsg(ConsumerRecord record){
        log.info("线程：" + Thread.currentThread().getName() + "处理消息：" + record.value());
    }

}
