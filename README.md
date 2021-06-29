#Kafuka生产端

##1、指定分区offset消费。
com.kafka.producer.util.DszConsumer3
##2、阻塞队列模拟实现多线程消息发送。
com.kafka.producer.util.CurrentProducerMsgByBlockQueue
##3、手动注入KafkaTemplate，以及线程池提高Kafka消费能力。
com.kafka.producer.producer