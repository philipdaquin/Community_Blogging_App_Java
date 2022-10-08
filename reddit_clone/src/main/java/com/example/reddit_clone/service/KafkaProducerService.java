package com.example.reddit_clone.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.reddit_clone.models.NotificationEmail;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, NotificationEmail> kafkaTemplate;
    
    /**
     * 
     * @param topicName
     * @param message
     */
    public void sendMessage(String topicName, NotificationEmail message) { 
        System.out.println("📦📦 KafkaProducerService.sendMessage()");
        final ProducerRecord<String, NotificationEmail> record = new ProducerRecord<>(topicName, message);

        kafkaTemplate.send(record);

    } 
}
