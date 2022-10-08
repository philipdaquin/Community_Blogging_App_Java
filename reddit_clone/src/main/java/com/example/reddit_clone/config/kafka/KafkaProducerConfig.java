package com.example.reddit_clone.config.kafka;

import java.util.HashMap;
import java.util.Map;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.example.reddit_clone.models.NotificationEmail;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapSevers;

    /**
     * 
     * @return ProducerFactory<String, NotificationEmail>
     */
    private ProducerFactory<String, NotificationEmail> producerFactory() {
        final var producer = new HashMap<String, Object>();
        producer.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapSevers);
        producer.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producer.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(producer);
    }

    /**
     * Kafka Producer 
     * @param producerFactory
     * @return
     */
    @Bean
    public KafkaTemplate<String, NotificationEmail> kafkaTemplate() { 
        return new KafkaTemplate<>(producerFactory()) ;       
    }
}
