package com.nyu.introtojava.finalproject.oneRoomChatAppKafkaConsumer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String USER_REQS_TOPIC = "UserReqs";
    public static final String CHAT_REQS_TOPIC = "ChatReqs";

    @Bean
    public NewTopic UserTopic(){
        return TopicBuilder.name(USER_REQS_TOPIC).build();
    }

    @Bean
    public NewTopic ChatTopic(){
        return TopicBuilder.name(CHAT_REQS_TOPIC).build();
    }
}
