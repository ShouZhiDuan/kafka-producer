package com.kafka.producer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@Component
public class KafkaUtil {
    @Autowired
    private KafkaTemplate kafkaTemplate;


    /**
     * 普通消息发送
     */
    public void sendMsg(){
        for(int i = 0; i < 1; i++){
            //不指定分区，不指定key
            //kafkaTemplate.send("test-topic-1","value" + i);
            //指定key
            //kafkaTemplate.send("test-topic-111111","test"+i,"value" + i);
            //指定partition_index，key。注意这里的第二个参数partition是指kafka集群里面的分区索引，而不是分区的数量，不要混淆。
            //kafkaTemplate.send("test-topic88",1,"test"+i,"value" + i);
            kafkaTemplate.send("test-topic99",1,"test99"+i,"value" + i);
        }
    }








}
