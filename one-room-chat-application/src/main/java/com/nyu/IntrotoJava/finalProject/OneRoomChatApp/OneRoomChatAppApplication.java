package com.nyu.IntrotoJava.finalProject.OneRoomChatApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class OneRoomChatAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneRoomChatAppApplication.class, args);
	}

}
