package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.dao;

import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models.Chats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatsRepository extends JpaRepository<Chats, Long> {

	public List<Chats> findAllByOrderByMessageDateAsc();
}
