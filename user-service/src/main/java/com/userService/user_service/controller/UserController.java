package com.userService.user_service.controller;

import com.userService.user_service.entities.UserInfoDto;
import com.userService.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/v1")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUpdate")
    public ResponseEntity<UserInfoDto> createUpdate(@RequestBody UserInfoDto userInfoDto){
        try {

            UserInfoDto user = userService.createOrUpdate(userInfoDto);
            return new ResponseEntity<>(user , HttpStatus.OK);

        }catch (Exception e){
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getUser")
    public ResponseEntity<UserInfoDto> getUser(UserInfoDto userInfoDto){
        try {
            UserInfoDto user = userService.getUser(userInfoDto);
            return new ResponseEntity<>(user , HttpStatus.OK);
        }catch (Exception ex){
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
