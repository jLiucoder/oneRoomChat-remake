package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "chats")
public class Chats {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="message_id")
	private Long msgId;

	@Column(name="message_text", nullable=false)
	private String messageText;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_users"))
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private Users user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="message_date", nullable=false)
	private Date messageDate;

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

//	@Override
//	public String toString() {
//		return "Chats [msgId=" + msgId + ", messageText=" + messageText + ", user=" + user + ", messageDate=" + messageDate
//				+ "]";
//	}


	@Override
	public String toString() {
		return "Chats{" +
				"msgId=" + msgId +
				", messageText='" + messageText + '\'' +
				", user=" + user +
				", messageDate=" + messageDate +
				'}';
	}
}

