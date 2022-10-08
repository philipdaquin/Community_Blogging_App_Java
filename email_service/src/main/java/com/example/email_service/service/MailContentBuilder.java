package com.example.email_service.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor
public class MailContentBuilder {
    private final TemplateEngine templateEngine;

    /*
     * Thymeleaf is a Java template engine for processing and creating 
     * HTML, XML, JavaScript, CSS and text 
     */
    String build(String message)  {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}
