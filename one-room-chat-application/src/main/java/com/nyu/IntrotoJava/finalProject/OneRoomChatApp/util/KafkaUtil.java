package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util;

import com.google.gson.Gson;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.constants.UserOperations;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services.KafkaMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaUtil {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KafkaMessagingService kafkaMessagingService;
    @Autowired
    private Gson gson;

    public void addUserPacket(String topic, String userJson) {

//        KafkaUnifiedUserPacket kafkaUnifiedUserPacket = new KafkaUnifiedUserPacket(UserOperations.ADD_USER, userJson);
        kafkaMessagingService.sendMessage(topic, UserOperations.ADD_USER, userJson);
//        kafkaTemplate.send(topic, gson.toJson(kafkaUnifiedUserPacket));
    }

    public void saveUserPacket(String topic, String userJson) {
//        KafkaUnifiedUserPacket kafkaUnifiedUserPacket = new KafkaUnifiedUserPacket(UserOperations.SAVE_USER, userJson);
        kafkaMessagingService.sendMessage(topic, UserOperations.SAVE_USER, userJson);
//        kafkaTemplate.send(topic, gson.toJson(kafkaUnifiedUserPacket));
    }
}
