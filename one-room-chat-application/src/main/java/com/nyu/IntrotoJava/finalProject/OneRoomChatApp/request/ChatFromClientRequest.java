package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.request;

import java.util.Date;

public class ChatFromClientRequest {

	private String messageText;
	private Long userId;
	private String username;
	private Date msgDate;
	
	
	
	public String getMessageText() {
		return messageText;
	}



	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}



	public Long getUserId() {
		return userId;
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



	public Date getMsgDate() {
		return msgDate;
	}



	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}



	@Override
	public String toString() {
		return "ChatFromClientRequest [messageText=" + messageText + ", userId=" + userId + ", username=" + username
				+ ", msgDate=" + msgDate + "]";
	}





}
