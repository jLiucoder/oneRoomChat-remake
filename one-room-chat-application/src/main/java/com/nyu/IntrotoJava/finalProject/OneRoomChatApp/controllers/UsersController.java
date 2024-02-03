package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.controllers;

import java.util.*;
import java.util.Optional;

import com.google.gson.Gson;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util.KafkaUtil;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.models.Users;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services.UserServiceImpl;

import static com.nyu.IntrotoJava.finalProject.OneRoomChatApp.config.KafkaTopicConfig.USER_REQS_TOPIC;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private KafkaUtil kafkaUtil;
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
	public ResponseEntity saveUser(@RequestBody Users user) {
		Optional<Users> u = userService.findUserById(user.getUserId());
		if(u.isEmpty()){
			return null;
		}
		String name = u.get().getUsername();
		Users realUser = userService.findUserByUserName(name);
		realUser.setFullName(user.getFullName());
		realUser.setEmail(user.getEmail());
		realUser.setDOB(user.getDOB());

		try{
			kafkaUtil.saveUserPacket(USER_REQS_TOPIC,gson.toJson(realUser));
		}catch (Exception e){
			log.error("Error in saving user to kafka" + e.getMessage());
			return ResponseEntity.status(500).body("Error in sending user to kafka");
		}
		return ResponseEntity.status(200).body("User saving in process");

	}


//	@PostMapping("")
//	public  
	
}
