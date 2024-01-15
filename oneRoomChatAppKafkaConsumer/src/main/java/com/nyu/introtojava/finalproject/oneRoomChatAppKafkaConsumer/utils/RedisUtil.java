package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUtil {

    private static final long CACHE_TIMEOUT = 10;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void incrementUserMessageCount(String userId) {
        String key = "userMessageCounts";
        redisTemplate.opsForZSet().incrementScore(key, userId, 1);
    }

    public Set<String> getTopUsers(int topN) {
        String key = "userMessageCounts";
        // Retrieve the top N users
        return redisTemplate.opsForZSet().reverseRange(key, 0, topN - 1);
    }


    public void addMessage(String key, String value){

        redisTemplate.opsForList().rightPush(key,value);
        redisTemplate.expire(key, CACHE_TIMEOUT, TimeUnit.MINUTES);
    }

    public List<String> getMessage(String key){
//        redisTemplate.expire(key, CACHE_TIMEOUT, TimeUnit.MINUTES);
        return redisTemplate.opsForList().range(key,0,-1);

    }
}
