package com.demo.kafka2kafka.consumer;

import com.demo.kafka2kafka.entity.Message;
import com.demo.kafka2kafka.event.MessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalMQConsumer {

    private final KafkaTemplate<Long, MessageEvent> kafkaTemplate;

    @KafkaListener(topics = "external-mq-topic")
    public void handleMessage(MessageEvent messageEvent) {
        //add to kafka MQ
        log.info("Message received in external MQ. Adding to kafka MQ.");
        kafkaTemplate.send("kafka-mq-topic", messageEvent);
        log.info("Message added to kafka MQ: {}.", messageEvent.getMessage());

    }
}
