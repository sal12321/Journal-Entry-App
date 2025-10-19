package com.salAce.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salAce.journalApp.api.response.WeatherResponse;
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
    public <T> T get(String key, Class<T> entityClass){
        try{
            Object o;
            o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            if(o != null){
                log.info("This response is generated from Redis");
                return mapper.readValue(o.toString() , entityClass) ;
            }
            else {
                return null ;
            }

        } catch(Exception e ){
            log.error("error in redis get method " + e );
            return null ;
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
