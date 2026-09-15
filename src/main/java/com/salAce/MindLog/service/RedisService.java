package com.salAce.MindLog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salAce.MindLog.entity.WeatherCacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate ;
    public <T> WeatherCacheResult get(String key, Class<T> entityClass) {

        long startTime = System.currentTimeMillis();
        long duration = 0;
        try {
            Object raw;
            raw = redisTemplate.opsForValue().get(key);
            duration = System.currentTimeMillis() - startTime;
            ObjectMapper mapper = new ObjectMapper();
            if (raw != null) {
                log.info("This response is generated from Redis, cache HIT");
                T data = mapper.readValue(raw.toString(), entityClass);
                return new WeatherCacheResult<>(data, duration, true);
            } else {
                log.info("Redis MISS for key: {} ({}ms)", key, duration);

                return new WeatherCacheResult<>(null, duration, false);
            }

        } catch (Exception e) {
            log.error("error in redis get method " + e.getMessage());
            return new WeatherCacheResult<>(null, duration, false);
        }


    }    public void set(String key, Object o , Long expiryTime){
        try{
            log.info("The response was not found in redis and is now stored in Redis");

            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(o) ;
            redisTemplate.opsForValue().set(key , jsonValue , expiryTime , TimeUnit.SECONDS) ;

        } catch(Exception e ){
            log.error("error in redis set method  " + e );

        }


    }

}
