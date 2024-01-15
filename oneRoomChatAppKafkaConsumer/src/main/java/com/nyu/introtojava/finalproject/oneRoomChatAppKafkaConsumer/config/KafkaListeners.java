package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.config;

import com.google.gson.Gson;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.dtos.AllChatsResponse;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models.Chats;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models.Users;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.dtos.ChatsDto;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.services.ChatsService;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.services.UsersService;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.config.KafkaTopicConfig.CHAT_REQS_TOPIC;
import static com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.config.KafkaTopicConfig.USER_REQS_TOPIC;

@Slf4j
@Component
public class KafkaListeners {

    @Autowired
    private Gson gson;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ChatsService chatsService;
    @Autowired
    private RedisUtil redisUtil;

    @KafkaListener(topics = USER_REQS_TOPIC, groupId = "group")
    void UserListener(String data){
        System.out.println(data + " received by user listener");

        try{
            Users user = gson.fromJson(data, Users.class);
            usersService.addUser(user);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    @KafkaListener(topics = CHAT_REQS_TOPIC, groupId = "group")
    void ChatListener(String data){
        System.out.println(data + " received by chat listener");

        try{
            ChatsDto chatReceived = gson.fromJson(data, ChatsDto.class);
            log.info( "chatReceived "+ chatReceived.toString());
            Chats chat = new Chats();

//            username is unique, so we can use it to find the user
            chat.setUser(usersService.findUserByUserName(chatReceived.getUsername()));
            chat.setMessageText(chatReceived.getMessageText());
            chat.setMessageDate(chatReceived.getMsgDate());
            log.info("chat we want to store: "+chat.toString());
            chatsService.addChat(chat);
            log.info("chat save to cache: "+chat.toString());

            AllChatsResponse res = new AllChatsResponse();
            res.setMessageDate(chat.getMessageDate());
            res.setMessageText(chat.getMessageText());
            res.setMsgId(chat.getMsgId());
            res.setUserId(chat.getUser().getUserId());
            res.setUsername(chat.getUser().getUsername());
            redisUtil.incrementUserMessageCount(gson.toJson(chat.getUser()));
            redisUtil.addMessage("allChats",gson.toJson(res));
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
