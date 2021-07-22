package com.kafka.producer;

import com.kafka.producer.producer.MsgProducerListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
public class KafkaProducerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaProducerApplication.class, args);
        String[] beanNamesForType = context.getBeanNamesForType(MsgProducerListener.class);
        System.out.println(beanNamesForType);
    }
}
