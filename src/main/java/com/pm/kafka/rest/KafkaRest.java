package com.pm.kafka.rest;

import com.pm.kafka.model.UserDetails;
import com.pm.kafka.service.KafkaMessagePublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/kafka")
public class KafkaRest {

    private KafkaMessagePublisher kafkaMessagePublisher;

    public KafkaRest(KafkaMessagePublisher kafkaMessagePublisher) {
        this.kafkaMessagePublisher = kafkaMessagePublisher;
    }

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestBody UserDetails userDetails){
        String result = kafkaMessagePublisher.publishMessageIntoKafka(userDetails);
        return ResponseEntity.ok().body(result);
    }
}
