package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.services;

import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models.Users;

import java.util.List;
import java.util.Optional;



public interface UsersService {

	void addUser(Users user);

	Optional<Users> findUserById(long id);

	Users findUserByUserName(String userName);

}
