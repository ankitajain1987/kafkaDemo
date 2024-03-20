package org.example.kafkademo.repository;

import org.example.kafkademo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaMessageRepository extends JpaRepository<Message, String> {
}
