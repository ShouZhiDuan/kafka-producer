package com.kafka.producer.producer;

import com.kafka.producer.DTO.DataDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: ShouZhi@Duan
 * @Description: 消费者
 */
@Slf4j
@Component
public class MyConsumer {

    @Autowired
    private MsgCurrentService msgCurrentService;


    /**
     * test-topic-88
     */
//    @KafkaListener(topics = "${fillersmart.analyze.car.topic.consumer}", containerFactory = "kafkaListenerContainerFactory")
//    public void carListen(List<ConsumerRecord> records, Acknowledgment ack) {
//        log.info("【消费者】开始执行消息消费");
//        try {
//            for (ConsumerRecord record : records) {
//                log.info("【消费者】开启线程解析消息:{}",record.toString());
//                msgCurrentService.readMsg(record);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("消费者解析数据异常:{}",e.getMessage(),e);
//        } finally {
//            //手动提交偏移量
//            ack.acknowledge();
//        }
//    }

    /**
     * test-topic-88
     */
//    @KafkaListener(topics = "test-topic-89",groupId = "test-topic-89-group-1")
//    public void carListen2(List<DataDTO> data) {
//        System.out.println(data);
//    }


}
