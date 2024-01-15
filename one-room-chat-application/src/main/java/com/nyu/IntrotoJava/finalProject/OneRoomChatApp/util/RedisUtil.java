package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisUtil {

    private static final long CACHE_TIMEOUT = 10;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void incrementUserMessageCount(String userId) {
        String key = "userMessageCounts";
        redisTemplate.opsForZSet().incrementScore(key, userId, 1);
    }

    public List<String> getTopUsers(int topN) {
        String key = "userMessageCounts";
        // Retrieve the top N users
        log.info("topN: "+redisTemplate.opsForZSet().reverseRange(key, 0, topN - 1));
        return new ArrayList<>(redisTemplate.opsForZSet().reverseRange(key, 0, topN - 1));
//        return redisTemplate.opsForZSet().range(key, 0, topN - 1);
    }

    public void addMessage(String key, String value){
        redisTemplate.expire(key, CACHE_TIMEOUT, TimeUnit.MINUTES);
        redisTemplate.opsForList().rightPush(key,value);
    }

    public List<String> getMessage(String key){
        redisTemplate.expire(key, CACHE_TIMEOUT, TimeUnit.MINUTES);
        return redisTemplate.opsForList().range(key,0,-1);
    }
}

