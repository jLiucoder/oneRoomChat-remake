package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.services;

import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.dao.ChatsRepository;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models.Chats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatsService {
	
	@Autowired
	ChatsRepository chatsRepository;

	@Override
	public List<Chats> findAllChats() {
		return  chatsRepository.findAllByOrderByMessageDateAsc();
	}

	@Override
	public void addChat(Chats chat) {
		chatsRepository.save(chat);
	}


}
