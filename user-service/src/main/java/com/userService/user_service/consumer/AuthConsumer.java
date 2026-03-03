package com.userService.user_service.consumer;

import com.userService.user_service.entities.UserInfoDto;
import com.userService.user_service.repository.UserRepository;
import com.userService.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@Service
public class AuthConsumer {

    private static final Logger logger = Logger.getLogger(AuthConsumer.class.getName());

    private final UserService userService;

    public AuthConsumer(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}" , groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto eventData){
        if (eventData == null){
            System.out.println("nothing is there");
            return;
        }
        try{
            userService.createOrUpdate(eventData);
        }catch(Exception e){
            logger.config(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
