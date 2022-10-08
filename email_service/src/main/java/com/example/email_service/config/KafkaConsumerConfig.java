package com.example.email_service.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.email_service.models.NotificationEmail;
import org.apache.kafka.common.serialization.StringDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
 
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.topics.groupId}")
    private String groupId;
    /**
     * Consumer Config for Kafka
     * @return
     */
    @Bean
    public ConsumerFactory<String, NotificationEmail> consumerFactory() { 
        Map<String, Object> consumerConfig = new HashMap<String, Object>();

        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerConfig.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");

        
        // Error Handling for Deserialisers
        consumerConfig.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        consumerConfig.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());


        // Error handling for Deserialiser

        // Accepting Objects from non trusted servers
        JsonDeserializer<NotificationEmail> deserializer = new JsonDeserializer<>(NotificationEmail.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(
            consumerConfig, 
            new StringDeserializer(), 
            deserializer
        );
    }
    /**
     * A KafkaListenerContainerFactory implementation to build a ConcurrentMessageListenerContainer.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationEmail> kafkaListenerContainerFactory(){
        var kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String, NotificationEmail>();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        kafkaListenerContainerFactory.setCommonErrorHandler(null);


        return kafkaListenerContainerFactory;
    }
}
