package com.example.reddit_clone.exceptions;

import org.springframework.mail.MailException;

public class NotificationException extends RuntimeException{

    public NotificationException(String message, MailException error) {
        super(message, error);
    }


}
