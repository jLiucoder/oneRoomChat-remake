package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.util;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.constants.UserOperations;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
public class KafkaUnifiedUserPacket {

    private UserOperations userOperation;
    private String userJson;

}
