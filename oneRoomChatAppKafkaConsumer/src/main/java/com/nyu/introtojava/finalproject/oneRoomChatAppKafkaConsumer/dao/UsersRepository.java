package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.dao;


import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
    Users findByUsernameAndPassword(String userName, String password);
    Users findByUsername(String userName);

//	Users addUser(String userName);
	
}
