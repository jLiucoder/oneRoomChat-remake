package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.dao.UsersRepository;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.models.Users;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UsersService {

	@Autowired
	UsersRepository usersRepository;

	@Override
	public Optional<Users> findUserById(long id) {
		// TODO Auto-generated method stub
		Optional<Users> user = usersRepository.findById(id);
		if (user.isEmpty()) {
			return Optional.empty();
		}
		else return user;
	}
	@Override
	public Users findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		Users user = usersRepository.findByUsername(userName);
		return user;
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
	public void addUser(Users user) {
		Users newUser = new Users();

		newUser.setUsername(user.getUsername());
		newUser.setFullName(user.getFullName());
		newUser.setDateCreated(new Date());

//		md5 hash the password using apache commons library
		String password = user.getPassword();
		String hashed = DigestUtils.md5DigestAsHex(password.getBytes());

		newUser.setPassword(hashed);
		usersRepository.save(newUser);		
	}

	@Override
	public Users login(Users user) {
		String userName = user.getUsername();
		String password = user.getPassword();
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		return usersRepository.findByUsernameAndPassword(userName, password);
	}


}
