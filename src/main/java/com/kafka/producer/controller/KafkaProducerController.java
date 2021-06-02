package com.kafka.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@RestController
@RequestMapping("/kafka")
public class KafkaProducerController {

    @GetMapping(value = "/sendMsg")
    public Object sendMsg(){
        return "OK";
    }

}
