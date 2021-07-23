package com.kafka.producer;

import com.kafka.producer.util.KafkaUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaProducerApplication.class)
public class KafkaProducerApplicationTests {

    @Autowired
    private KafkaUtil kafkaUtil;

    @Test
    public void sendMsg() {
        kafkaUtil.sendMsg();
    }


}
