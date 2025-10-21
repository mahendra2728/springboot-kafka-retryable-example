package com.pm.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.kafka.model.UserDetails;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

public class UserDetailsDeserializer implements Deserializer<UserDetails> {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public UserDetails deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(data, UserDetails.class);
        } catch (Exception e) {
            throw new KafkaException("Error deserializing UserDetals", e);
        }
    }

    // Headers-aware overload (Kafka >= 2.0). Delegate to the byte[] variant.
    @Override
    public UserDetails deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }

    @Override
    public void close() {
        // No resources to close
    }
}
