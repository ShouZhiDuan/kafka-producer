package com.kafka.producer.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: ShouZhi@Duan
 * @Description: kafka事务控制
 * 参考：https://blog.csdn.net/weixin_34191845/article/details/88003858
 */
@Slf4j
public class DszKafkaTransactionProducer extends Thread{

    private KafkaProducer producer;
    private String topic;

    public DszKafkaTransactionProducer(String topic) {
        this.topic = topic;
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.10.33:9092,192.168.10.34:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"dsz-producer");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.kafka.producer.partition.MyPartition");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put("transactional.id", "my-transactional-id");
        //连接的字符串  通过工厂  new
        producer=new KafkaProducer<Integer, String>(properties);
    }

    @Override
    public void run() {
        int num=0;
        //初始化事务
        producer.initTransactions();
        //开始事务
        producer.beginTransaction();
        while(num<2) {
                String msg="dsz kafka transaction msg:"+num;
                Object o = producer.send(
                        //消息体
                        new ProducerRecord<>(topic, 1, "测试事务消息" + num),
                        //发送成功异步回调(非阻塞)
                        (metadata, exception) -> {
                            log.info("异步回调：主题->" + metadata.topic() + " 分区编号->" + metadata.partition() + "当前分区上的索引->" + metadata.offset());
                        }
                );
                ++num;
        }
        //提交事务
        producer.commitTransaction();
        //producer.abortTransaction();终止事务
    }

    public static void main(String[] args) {
        new DszKafkaTransactionProducer("test-topic-3").start();
    }
}
