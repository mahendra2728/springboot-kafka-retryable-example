package com.pm.kafka.service;

import com.pm.kafka.model.UserDetails;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    Logger log = org.slf4j.LoggerFactory.getLogger(KafkaMessagePublisher.class);

    @Value("${kafka.user.topic}")
    private String kafkaTopic;

    private int count=0;

    private final KafkaTemplate<String,UserDetails> kafkaTemplate;

    public KafkaMessagePublisher(KafkaTemplate<String, UserDetails> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Retryable(
            value = {Exception.class},
            maxAttempts = 3 ,
            backoff = @org.springframework.retry.annotation.Backoff(delay = 2000),
            recover = "fallbackPublishMessage"
    )
    public String publishMessageIntoKafka(UserDetails userDetails) throws Exception {
        count++;
        log.info("Attempt count: {}", count);
        if(count<5){
            throw new Exception("Simulated transient error");
        }
        CompletableFuture<SendResult<String, UserDetails>> response = kafkaTemplate.send(kafkaTopic, userDetails);
        String result = "";
        while(!response.isDone()){
            result = "Message successfully published";
        }
        return result;
    }

    @Recover
    public String fallbackPublishMessage(Throwable t, UserDetails userDetails) {
        log.error("Failed to publish message for userId={} after retries. Error: {}", userDetails != null ? userDetails.getUserId() : "null", t.getMessage());
        return "Failed to publish message after retries";
    }
}
