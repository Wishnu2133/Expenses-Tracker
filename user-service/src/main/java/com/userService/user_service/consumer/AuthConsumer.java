package com.userService.user_service.consumer;

import com.userService.user_service.entities.UserInfo;
import com.userService.user_service.entities.UserInfoDto;
import com.userService.user_service.repository.UserRepository;
import com.userService.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthConsumer {

    @Autowired
    private UserService userService;

    @KafkaListener(topics = "${spring.kafka.topic.name}" , groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto eventData){
        if (eventData == null){
            System.out.println("nothing is there");
            return;
        }
        try{
            userService.createOrUpdate(eventData);
        }catch(Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
