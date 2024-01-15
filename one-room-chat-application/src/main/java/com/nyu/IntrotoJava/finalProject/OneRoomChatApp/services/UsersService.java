package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services;

import java.util.*;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.models.Users;



public interface UsersService {

	Optional<Users> findUserById(long id);
	List<Users> findAllUsers();
	void addUser(Users user);

	Users login(Users user);
	Users findUserByUserName(String userName);

	Users saveUser(Users user);

}
