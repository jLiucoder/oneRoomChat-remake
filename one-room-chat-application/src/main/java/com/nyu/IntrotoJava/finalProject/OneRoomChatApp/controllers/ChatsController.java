package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.controllers;


import java.util.*;

import com.google.gson.Gson;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.models.Users;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.models.Chats;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.response.AllChatsResponse;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services.ChatServiceImpl;

@Slf4j
@RestController
@RequestMapping("/chats")
public class ChatsController {
	
	@Autowired
	ChatServiceImpl chatService;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	Gson gson;
	
	@GetMapping(path = "")
	public ResponseEntity<Object> findAllUser() {
		List<AllChatsResponse> response = new ArrayList<AllChatsResponse>();

		String cacheKey = "allChats";
		List<String>cachedChats= redisUtil.getMessage(cacheKey);
//		cache hit

		if(cachedChats!=null && !cachedChats.isEmpty()){
			List<Chats> chatsStore = new ArrayList<>();
			for(String chat:cachedChats){
				AllChatsResponse tempChat = gson.fromJson(chat, AllChatsResponse.class);
				response.add(tempChat);
			}


			log.info("the chats are from cache");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else{

			List<Chats> allChats = chatService.findAllChats();

			for(Chats chat : allChats) {
				AllChatsResponse oldChat = new AllChatsResponse();
				oldChat.setMessageText(chat.getMessageText());
				oldChat.setMessageDate(chat.getMessageDate());
				oldChat.setMsgId(chat.getMsgId());
				oldChat.setUserId(chat.getUser().getUserId());
				oldChat.setUsername(chat.getUser().getUsername());
				response.add(oldChat);

//			cache the chats
				redisUtil.addMessage(cacheKey,gson.toJson(oldChat));
			}
			log.info("the chats are from database");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}


	}

	@GetMapping(path="/search")
	public ResponseEntity<Object> findRelatedChatByWord(@RequestParam String word) {
		List<AllChatsResponse> response = new ArrayList<>();
		log.info("word: " + word);
		List<Chats> allChats = chatService.findRelatedChatByWord(word);
		for(Chats chat : allChats) {
			AllChatsResponse oldChat = new AllChatsResponse();
			oldChat.setMessageText(chat.getMessageText());
			oldChat.setMessageDate(chat.getMessageDate());
			oldChat.setMsgId(chat.getMsgId());
			oldChat.setUserId(chat.getUser().getUserId());
			oldChat.setUsername(chat.getUser().getUsername());
			response.add(oldChat);
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
