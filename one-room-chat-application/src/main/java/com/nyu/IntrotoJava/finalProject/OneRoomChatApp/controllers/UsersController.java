package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.controllers;

import java.util.*;
import java.util.Optional;

import com.fasterxml.jackson.databind.node.ObjectNode;
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
	@PostMapping("profile-picture")
	public Users saveUserProfilePicture(@RequestBody ObjectNode objectNode) {
		String userId = objectNode.get("userId").asText();
		String url = objectNode.get("url").asText();
		Users user = userService.findUserById(Long.parseLong(userId)).get();
		log.info("before setting url" + user.toString());
		user.setProfilePicture(url);
		log.info("after setting url" + user.toString());
		userService.saveUser(user);
		return user;
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
//		realUser.

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
