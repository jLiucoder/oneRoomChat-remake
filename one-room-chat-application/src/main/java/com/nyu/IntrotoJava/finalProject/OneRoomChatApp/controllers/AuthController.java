package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.controllers;

import com.google.gson.Gson;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.models.Users;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.request.LoginRequest;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.request.RegisterUserRequest;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.response.LoginResponse;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services.UsersService;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util.JwtUtils;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.nyu.IntrotoJava.finalProject.OneRoomChatApp.config.KafkaTopicConfig.USER_REQS_TOPIC;

@Slf4j
@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private Gson gson;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody RegisterUserRequest registerUser) {
        Users newUser = new Users();
        newUser.setUsername(registerUser.getUsername());
        newUser.setFullName(registerUser.getFullName());
        newUser.setEmail(registerUser.getEmail());
        newUser.setDOB(registerUser.getDOB());
//        hash the password before transferring to kafka
        newUser.setPassword(DigestUtils.md5DigestAsHex(registerUser.getPassword().getBytes()));
        System.out.println(newUser.toString());
//        adding to kafka mq for async processing
        try {
        	kafkaTemplate.send(USER_REQS_TOPIC, gson.toJson(newUser));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Result.success("User registration in process"));
        } catch (Exception e) {
        	log.error("Error sending message to kafka");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error("Error sending message to kafka"));
        }

//        all these goes to second server
//        Users checkUserExist = usersService.findUserByUserName(newUser.getUsername());
//
//        if(checkUserExist != null){
//            return ResponseEntity.badRequest().body(Result.error("Username already exists"));
//        }
//        usersService.addUser(newUser);
//
//        return ResponseEntity.status(HttpStatus.OK).body(Result.success("User successfully registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginDetails) {

        log.info("Login request received for user: {}", loginDetails.toString());
    	LoginResponse loginResponse = new LoginResponse();

        Users tempUser = new Users();
        tempUser.setUsername(loginDetails.getUsername());
        tempUser.setPassword(loginDetails.getPassword());
        Users userMatched = usersService.login(tempUser);

        if(userMatched!= null){
//            generate token for the user
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", userMatched.getUserId());
            claims.put("username", userMatched.getUsername());
            claims.put("fullName", userMatched.getFullName());
            String token = JwtUtils.generateToken(claims);
//            claims.put("token", token);
            loginResponse.setUserId(userMatched.getUserId());
            loginResponse.setUsername(userMatched.getUsername());
            loginResponse.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);

        }
        return ResponseEntity.badRequest().body(Result.error("Login failed. Username or Password incorrect"));
    }
}
