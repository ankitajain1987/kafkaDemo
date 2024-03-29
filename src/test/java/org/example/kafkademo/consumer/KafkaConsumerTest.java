package org.example.kafkademo.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.kafkademo.entity.Message;
import org.example.kafkademo.repository.KafkaMessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class KafkaConsumerTest {


    @InjectMocks
    KafkaConsumer kafkaNotificationConsumer;

    @Mock
    KafkaMessageRepository kafkaMessageRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testListen() throws Exception {
        kafkaNotificationConsumer.kafkaMessageRepository = kafkaMessageRepository;
        Message message = new Message(1, "testUser","testemail@test.com", "2819829129");
        Integer offset = 1;
        int partition = 10;
        String topic = "TestTopic123";
        kafkaNotificationConsumer.listen(buildConsumerRecord(message), offset, partition, topic);
        verify(kafkaMessageRepository).save(any(Message.class));
    }

    private ConsumerRecord<?, String> buildConsumerRecord(Message message) throws Exception {
        return new ConsumerRecord<>("TestTopic123", 0, 1, null,
                objectMapper.writeValueAsString(message));

    }
}