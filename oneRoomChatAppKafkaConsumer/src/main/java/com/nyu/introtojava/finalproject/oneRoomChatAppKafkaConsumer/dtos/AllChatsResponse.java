package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.dtos;

import java.util.Date;

public class AllChatsResponse {

    private Long msgId;
    private String messageText;
    private Long userId;
    private String username;
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

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

//    @Override
//    public String toString() {
//        return "AllChatsResponse [msgId=" + msgId + ", messageText=" + messageText + ", userId=" + userId
//                + ", username=" + username + ", msgDate=" + messageDate + "]";
//    }


    @Override
    public String toString() {
        return "AllChatsResponse{" +
                "msgId=" + msgId +
                ", messageText='" + messageText + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", messageDate=" + messageDate +
                '}';
    }
}
