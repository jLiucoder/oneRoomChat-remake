package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatsDto {

    private String messageText;
    private Integer userId;
    private String username;
    private Date msgDate;
}
