package com.nyu.IntrotoJava.finalProject.OneRoomChatApp;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class OneRoomChatAppApplicationTests {

    @Autowired
    private RedisUtil redisUtil;
	@Test
    void testString(){
        redisUtil.addMessage("room","zhangsan");
        redisUtil.addMessage("room","lisi");
        System.out.println(redisUtil.getMessage("room"));
    }



}
