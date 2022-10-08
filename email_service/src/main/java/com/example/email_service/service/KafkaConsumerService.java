package com.example.email_service.service;

import java.io.IOException;
import java.time.Duration;

import javax.management.ListenerNotFoundException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.mail.MailException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.email_service.models.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumerService {

    private final String AuthTopic = "notificationEmail";
    
    @Autowired
    private MailService mailService;


    @Value("${spring.kafka.topics.groupId}")
    private String GroupId;
    
    @RetryableTopic(
        attempts = "1", 
        include = {ClassCastException.class}, 
        backoff = @Backoff(delay = 1000, multiplier = 2.0),
        includeNames = "java.lang.ClassCastException",
        dltStrategy = DltStrategy.FAIL_ON_ERROR,
        timeout = "5000"
        )
    @KafkaListener(
        topics = "notificationEmail", 
        groupId = "groupId",
        containerFactory = "kafkaListenerContainerFactory" 
    )
    void kafkaListener(ConsumerRecord<String, NotificationEmail> message) { 
        log.info("🥳 message consumed - key: {} , value: {}, topic: {}",
                message.key(),
                message.value(),
                message.topic());
        NotificationEmail payload = message.value();
        mailService.sendMail(payload);
    }
}
