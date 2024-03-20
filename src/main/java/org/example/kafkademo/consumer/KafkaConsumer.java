package org.example.kafkademo.consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.kafkademo.entity.Message;
import org.example.kafkademo.repository.KafkaMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    KafkaMessageRepository kafkaMessageRepository;
    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public KafkaConsumer(KafkaMessageRepository kafkaMessageRepository) {
        this.kafkaMessageRepository = kafkaMessageRepository;
    }
    KafkaConsumer() {
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @KafkaListener(topics = "example", groupId = "example")
        public void listen(ConsumerRecord<?, String> kafkaMsg, @Header(KafkaHeaders.OFFSET) Integer offset,
                           @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        long receivedTime = System.currentTimeMillis();
        String strMsg = kafkaMsg.value();
        logger.info("Kafka example received message='{}'", strMsg);

        logger.info("ConsumerRecord age={} seconds, partition={}, offset={}, {}={}, " +
                        "SerializedKeySize={}, SerializedValueSize={}, key ={}", (receivedTime - kafkaMsg.timestamp()) / 1000.0F,
                kafkaMsg.partition(), kafkaMsg.offset(), kafkaMsg.timestampType(), kafkaMsg.timestamp(),
                kafkaMsg.serializedKeySize(), kafkaMsg.serializedValueSize(), kafkaMsg.key());
        try {
            Message message = objectMapper.readValue(strMsg, Message.class);
            logger.info("Kafka example - Processed message content: {}", message);
            logger.info("Initiating DB Call to save message");
            kafkaMessageRepository.save(message);

        } catch (Exception e) {
            logger.error("Kafka example -  Exception! {} {}", e.getClass().getName(), e.getLocalizedMessage());
        }
    }


    }
