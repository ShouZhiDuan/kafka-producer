package com.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class KafkaProducerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(KafkaProducerApplication.class, args);
    }

}
