package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services;

import java.util.List;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.models.Chats;

public interface ChatsService {

	List<Chats> findAllChats();

	List<Chats> findRelatedChatByWord(String word);
}
