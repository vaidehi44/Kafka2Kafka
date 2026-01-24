package com.demo.kafka2kafka.config;

import com.demo.kafka2kafka.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String KAFKA_SERVER;

    @Bean
    public ProducerFactory<Long, MessageEvent> producerFactory() {
        log.info("Kafka: ", KAFKA_SERVER);
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        return new DefaultKafkaProducerFactory(config);
    }

    @Bean
    public KafkaTemplate<Long, MessageEvent> kafkaTemplate() {
        return new KafkaTemplate<Long, MessageEvent>(producerFactory());
    }

    @Bean
    public ConsumerFactory<Long, MessageEvent> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka2kafka");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongSerializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new LongDeserializer(),
                new JacksonJsonDeserializer<>(MessageEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, MessageEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, MessageEvent> factory = new ConcurrentKafkaListenerContainerFactory<Long, MessageEvent>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
