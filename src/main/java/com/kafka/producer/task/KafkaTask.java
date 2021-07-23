package com.kafka.producer.task;

import com.kafka.producer.DTO.DataDTO;
import com.kafka.producer.producer.MsgProducerListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@Slf4j
@Component
@EnableScheduling
public class KafkaTask {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private MsgProducerListener producerListener;

    private static int counter;

    @Value("${fillersmart.analyze.car.topic.consumer}")
    private String topic1;

//    @Scheduled(cron = "*/30 * * * * ?")
//    public void schedule(){
//        long start = System.currentTimeMillis();
//        counter = ++counter;
//         kafkaTemplate.send(topic1, "【msg-" + (counter) + "】");
//        long end = System.currentTimeMillis();
//        log.info("【生产者】执行发送消息，耗时：" + (end-start) + "ms");
//    }

    @Scheduled(cron = "*/30 * * * * ?")
    public void schedule2() {
        DataDTO data = new DataDTO();
        for (int i = 0; i < 500; i++) {
            //{"addr":"地址", "age":16, "name":"testName"}
            //kafkaTemplate.send("test-topic-89", JSON.toJSONString(data));

            //1,2,3
            //kafkaTemplate.send("test-topic-fix", JSON.toJSONString(data));

//            Map<String, Object> map = new HashMap();
//            map.put("key",data);
            kafkaTemplate.send("test-topic-fix", data);
        }
    }

}
