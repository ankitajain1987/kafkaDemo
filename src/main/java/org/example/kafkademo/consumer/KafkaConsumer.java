package org.example.kafkademo.consumer;

import org.example.kafkademo.entity.Message;
import org.example.kafkademo.repository.KafkaMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class KafkaConsumer {
    KafkaMessageRepository kafkaMessageRepository;
    Logger logger = Logger.getLogger(KafkaConsumer.class.getName());

    @Autowired
    public KafkaConsumer(KafkaMessageRepository kafkaMessageRepository) {
        this.kafkaMessageRepository = kafkaMessageRepository;
    }

    @KafkaListener(topics = "example", groupId = "example")
        public void listen(@Payload String msg, @Header(KafkaHeaders.OFFSET) Integer offset,
                           @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic)
    {
        logger.info(("Processing topic = %s, partition = %d, offset = %d, " +
                "message = %s").formatted(topic, partition, offset, msg));
            Message message = new Message();
            message.setMessage(msg);
        logger.info("Initiating DB Call to save message");
        kafkaMessageRepository.save(message);
        }
    }
