package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models;

import jakarta.persistence.*;

import java.util.Date;



@Entity
@Table(name = "USERS")
public class Users {


	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name="username", nullable = false)
	private String username;

	@Column(name="user_fullname", nullable = false)
	private String fullName;

	@Column(name="user_email", nullable = false)
	private String email;

	@Column(name="user_DOB")
	private Date DOB;

	@Column(name="date_created",nullable = false )
	private Date dateCreated;

	@Column(name= "user_password", nullable = false)
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date DOB) {
		this.DOB = DOB;
	}



	public Long getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", username=" + username + ", fullName=" + fullName + ", dateCreated="
				+ dateCreated + ", password=" + password +"]";
	}

}

