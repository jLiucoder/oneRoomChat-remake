package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services;

import com.google.gson.Gson;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.constants.UserOperations;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util.KafkaUnifiedUserPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagingService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private Gson gson;

    /**
     * Sends a message to the specified Kafka topic asynchronously.
     * @param topic the Kafka topic to send the message to
     * @param userOperation the user operation (e.g., ADD_USER, SAVE_USER)
     * @param userJson the user data in JSON format
     */
    @Async
    public void sendMessage(String topic, UserOperations userOperation, String userJson) {
        KafkaUnifiedUserPacket packet = new KafkaUnifiedUserPacket(userOperation, userJson);
        String message = gson.toJson(packet);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("Error sending message to Kafka: " + ex.getMessage());
            }
        });

    }
}
