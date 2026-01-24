package com.demo.kafka2kafka.service;

import com.demo.kafka2kafka.dto.MessageDto;
import com.demo.kafka2kafka.entity.Message;
import com.demo.kafka2kafka.event.MessageEvent;
import com.demo.kafka2kafka.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final KafkaTemplate<Long, MessageEvent> kafkaTemplate;

    public MessageEvent add(MessageDto message) {
        //Add to external message queue
        log.info("Message received from API. Adding message to external MQ.");
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setMessage(message.getMessage());
        kafkaTemplate.send("external-mq-topic", messageEvent);

        //Add to database
        log.info("Message added to external MQ: {}.", message.getMessage());
        Message mssg = new Message();
        mssg.setMessage(message.getMessage());
        messageRepository.save(mssg);
        return messageEvent;
    }

}
