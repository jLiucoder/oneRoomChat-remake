package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.utils;

import com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.constants.UserOperations;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class KafkaUnifiedUserPacket {

        private UserOperations userOperation;
        private String userJson;

}
