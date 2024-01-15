package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.services;

import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models.Chats;

import java.util.List;

public interface ChatsService {

	List<Chats> findAllChats();

	void addChat(Chats chat);
}
