package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.services;

import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.dao.UsersRepository;
import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UsersService {

	@Autowired
	UsersRepository usersRepository;


	public Optional<Users> findUserById(long id) {
		// TODO Auto-generated method stub
		Optional<Users> user = usersRepository.findById(id);
		if (user.isEmpty()) {
			return Optional.empty();
		}
		else return user;
	}

	@Override
	public Users saveUser(Users user) {

		return usersRepository.save(user);
	}

	@Override
	public List<Users> findAllUsers() {
		return (List<Users>) usersRepository.findAll();
	}


	@Override
	public Users findUserByUserName(String userName) {
		return usersRepository.findByUsername(userName);
	}

	@Override
	public void addUser(Users user) {
		Users newUser = new Users();

		newUser.setUsername(user.getUsername());
		newUser.setFullName(user.getFullName());
		newUser.setDateCreated(new Date());
		newUser.setEmail(user.getEmail());
		newUser.setDOB(user.getDOB());
		newUser.setPassword(user.getPassword());
		usersRepository.save(newUser);		
	}



}
