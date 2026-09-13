package com.userService.user_service.service;

import com.userService.user_service.entities.UserInfo;
import com.userService.user_service.entities.UserInfoDto;
import com.userService.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserInfoDto createOrUpdate(UserInfoDto userInfoDto){
        UnaryOperator<UserInfo> updateUser = user -> {
            user.setUserId(userInfoDto.getUserId());
            user.setFirstName(userInfoDto.getFirstName());
            user.setLastName(userInfoDto.getLastName());
            user.setEmailId(userInfoDto.getEmailId());
            user.setPhoneNo(userInfoDto.getPhoneNo());
            return  userRepository.save(user);
        };

        Supplier<UserInfo> createUser = () -> {
            return userRepository.save(userInfoDto.convertToEntity());
        };

        UserInfo userInfo = userRepository.findByUserId(userInfoDto.getUserId())
                // if any user found it call map and pass that user to updateUser
                .map(updateUser)
                // if user not found it call create user method
                .orElseGet(createUser);
        return new UserInfoDto(
                userInfoDto.getUserId(),
                userInfoDto.getFirstName(),
                userInfoDto.getLastName(),
                userInfoDto.getPhoneNo(),
                userInfoDto.getEmailId()
        );
    }

    public UserInfoDto getUser(UserInfoDto userInfoDto) throws Exception {
        Optional<UserInfo> userOpt = userRepository.findByUserId(userInfoDto.getUserId());
        if(userOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        UserInfo userInfo = userOpt.get();
        return new UserInfoDto(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNo(),
                userInfo.getEmailId()
        );
    }
}
