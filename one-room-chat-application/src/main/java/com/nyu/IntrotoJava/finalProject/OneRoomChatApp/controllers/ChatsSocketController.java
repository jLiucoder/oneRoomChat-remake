package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.request.ChatFromClientRequest;

import lombok.extern.slf4j.Slf4j;

import static com.nyu.IntrotoJava.finalProject.OneRoomChatApp.config.KafkaTopicConfig.CHAT_REQS_TOPIC;

@Slf4j
@CrossOrigin(origins = { "*" })
@Controller
public class ChatsSocketController {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	Gson gson;

	 // Handles messages from /app/chat.
	@MessageMapping("/chat")
	// Sends the return value of this method to /topic/messages
	@SendTo("/topic/messages")
	public ChatFromClientRequest getMesssage(@RequestBody ChatFromClientRequest m) {
		log.info(m.toString());
		log.info(m.getMsgDate().toString());

		try {
			kafkaTemplate.send(CHAT_REQS_TOPIC, gson.toJson(m));
		} catch (Exception e) {
			log.error("Error sending message to kafka");
		}

		return m;
	}
}
