package com.example.reddit_clone.config.redis;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.Jedis;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching
public class RedisConfig {


    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    /**
     * 
     * @param redisConnectionFactory
     * @return
     */
    @Bean
	public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
		var template = new RedisTemplate<String, Serializable>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

    /**
     * 
     * @param factory
     * @return
     */
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		var config = RedisCacheConfiguration.defaultCacheConfig();
		var redisCacheConfiguration = config
			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		
		var redisCacheManager = RedisCacheManager
            .builder(factory)
            .cacheDefaults(redisCacheConfiguration)
			.build();
		return redisCacheManager;
	}

    /**
     * 
     */
	@PostConstruct
	public void clearCache() {
		System.out.println("In Clear Cache");
		Jedis jedis = new Jedis("127.0.0.1", 6379, 5000);
		jedis.flushAll();
		jedis.close();
	}
}
