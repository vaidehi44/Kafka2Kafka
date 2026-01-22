package com.demo.kafka2kafka.repository;

import com.demo.kafka2kafka.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
