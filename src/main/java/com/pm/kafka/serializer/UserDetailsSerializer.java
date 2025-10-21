package com.pm.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.kafka.model.UserDetails;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UserDetailsSerializer implements Serializer<UserDetails> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, UserDetails data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new KafkaException("Error serializing UserDetails", e);
        }
    }

    @Override
    public void close() {
    }
}
