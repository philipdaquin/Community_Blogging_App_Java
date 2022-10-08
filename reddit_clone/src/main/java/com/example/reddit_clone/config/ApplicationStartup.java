package com.example.reddit_clone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    
    @Autowired
    private CacheManager cacheManager;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        
        cacheManager.getCacheNames()
            .parallelStream()
            .forEach(System.out::println);
    }

    

}
