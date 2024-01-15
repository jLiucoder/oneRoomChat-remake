package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.dao.ChatsRepository;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.models.Chats;

@Service
public class ChatServiceImpl implements ChatsService {
	
	@Autowired
	ChatsRepository chatsRepository;

	@Override
	public List<Chats> findAllChats() {
		return  chatsRepository.findAllByOrderByMessageDateAsc();
	}

	@Override
	public List<Chats> findRelatedChatByWord(String word) {
		return chatsRepository.findRelatedChatByWord(word);
	}

}
