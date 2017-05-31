package com.java.xdd.kafka.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class KafkaTest {

    private KafkaConsumerServiceImpl kafkaConsumerService;
    private KafkaProducerServiceImpl kafkaProducerService;

    @Before
    public void before() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-kafka.xml");
        context.start();
        kafkaProducerService = context.getBean(KafkaProducerServiceImpl.class);
        kafkaConsumerService = context.getBean(KafkaConsumerServiceImpl.class);
    }

    @Test
    public void test() throws Exception{
        kafkaProducerService.sendMessage("topic1", 1, "3");

        //KafkaConsumer<Integer, String> consumer = new KafkaConsumer(null);
        //consumer.subscribe(Arrays.asList("topic1"));

        ConsumerRecord<Integer, String> records = new ConsumerRecord<Integer, String>("topics",3, 6, 2, "5");

        kafkaConsumerService.onMessage(records);
        System.out.println();
    }


}
