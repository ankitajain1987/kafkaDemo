package org.example.kafkademo.consumer;


import org.example.kafkademo.entity.Message;
import org.example.kafkademo.repository.KafkaMessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class KafkaConsumerTest {


    @InjectMocks
    KafkaConsumer kafkaNotificationConsumer;

    @Mock
    KafkaMessageRepository kafkaMessageRepository;

    @Test
    public void testListen() {
        kafkaNotificationConsumer.kafkaMessageRepository = kafkaMessageRepository;
        String message = "testMessage";
        Integer offset = 1;
        int partition = 10;
        String topic = "TestTopic123";
        kafkaNotificationConsumer.listen(message, offset, partition, topic);
        verify(kafkaMessageRepository).save(any(Message.class));
    }
}