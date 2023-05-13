package com.TimeNote.CourseService.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.time.Instant;

@Service 

public class KafkaProducer {
    @Value("${topic.name}")
    private String topicName;

    private final KafkaTemplate<String, Message> kafkaTemplate;

    KafkaProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void send(Message message,byte[] byteImage) {
        message.setTime(Instant.now());
        message.setByteArray(byteImage);
        kafkaTemplate.send(topicName, message);
    }
}
