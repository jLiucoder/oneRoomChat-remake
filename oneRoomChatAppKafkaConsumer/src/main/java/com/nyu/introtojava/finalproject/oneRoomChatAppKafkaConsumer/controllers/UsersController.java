package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.controllers;

import com.google.gson.Gson;


import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models.Users;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.services.UserServiceImpl;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private Gson gson;
	
	@GetMapping(path = "" , produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Users> findAllUser() {
		List<Users> allUsers = userService.findAllUsers();
		
		return allUsers;
	}
	
	@GetMapping("/{id}")
	public Optional<Users> findUserById(@PathVariable long id) {
		return userService.findUserById(id);
	}

	@GetMapping("/top")
	public List<Users> findTopUsers() {

		List<String>set = redisUtil.getTopUsers(5);
		List<Users>users = new ArrayList<>();
		for(String s:set){
			log.info("s: "+s);
			Users u = gson.fromJson(s,Users.class);
			users.add(u);
		}

		return users;
	}

	@PostMapping()
	public Users saveUser(@RequestBody Users user) {
		Optional<Users> u = userService.findUserById(user.getUserId());
		if(u.isEmpty()){
			return null;
		}
		String name = u.get().getUsername();
		Users realUser = userService.findUserByUserName(name);
		realUser.setFullName(user.getFullName());
		realUser.setEmail(user.getEmail());
		realUser.setDOB(user.getDOB());
		return userService.saveUser(realUser);
	}

//	@PostMapping("")
//	public  
	
}
