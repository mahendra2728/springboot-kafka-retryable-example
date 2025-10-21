package com.pm.kafka.service;

import com.pm.kafka.model.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Value("${kafka.user.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String,UserDetails> kafkaTemplate;

    public KafkaMessagePublisher(KafkaTemplate<String, UserDetails> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String publishMessageIntoKafka(UserDetails userDetails) {
        CompletableFuture<SendResult<String, UserDetails>> response = kafkaTemplate.send(kafkaTopic, userDetails);
        String result = "";
        while(!response.isDone()){
            result = "Message successfully published";
        }
        return result;
    }
}
