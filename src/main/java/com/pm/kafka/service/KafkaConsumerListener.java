package com.pm.kafka.service;

import com.pm.kafka.model.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);

    private static final String KAFKA_TOPIC = "user_topic";
    private static final String CONSUMER_GROUP_ID = "user-consumer-group";
    public static final String RETRY_ATTEMPT_COUNT = "4";
    public static final String DLT_TOPIC_SUFFIX = ".dlq-topic";
    public static final String AUTO_CREATION_TOPIC_FLAG = "true";

    @RetryableTopic(
            attempts = RETRY_ATTEMPT_COUNT, // Original attempt + 3 retries
            backoff = @Backoff(delay = 1000, multiplier = 2), // Exponential backoff: 1s, 2s, 4s
            dltTopicSuffix = DLT_TOPIC_SUFFIX, // Suffix for the Dead Letter Topic
            autoCreateTopics = AUTO_CREATION_TOPIC_FLAG, // Automatically create retry and DLT topics
            kafkaTemplate = "kafkaTemplate" // Name of your KafkaTemplate bean
    )
    @KafkaListener(topics = KAFKA_TOPIC, groupId = CONSUMER_GROUP_ID)
    public void listerUserMessage(UserDetails userDetails) {
       log.info("Received message in listerUserMessage : {} " , userDetails);
       if (userDetails.toString().contains("error")) {
            throw new RuntimeException("Simulating a transient error for: " + userDetails);
        }
        log.info("Successfully processed message: {}" , userDetails);
    }

    @DltHandler
    public void processDLQMessage(UserDetails userDetails) {
        log.info(" Received message in DLQ: {}" , userDetails);
        // Handle messages that failed all retries (e.g., log, alert, manual intervention)
    }
}
