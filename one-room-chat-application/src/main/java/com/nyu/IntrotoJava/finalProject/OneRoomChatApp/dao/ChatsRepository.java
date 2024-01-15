package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.models.Chats;
import org.springframework.data.jpa.repository.Query;

public interface ChatsRepository extends JpaRepository<Chats, Long> {

	public List<Chats> findAllByOrderByMessageDateAsc();

	@Query(value = "SELECT * FROM chats WHERE message_text LIKE %?1%", nativeQuery = true)
	public List<Chats> findRelatedChatByWord(String word);
}
