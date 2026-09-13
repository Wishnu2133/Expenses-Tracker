package com.userService.user_service.deSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userService.user_service.entities.UserInfoDto;
import org.apache.kafka.common.serialization.Deserializer;
import java.util.Map;


public class UserDtoDeserializer implements Deserializer<UserInfoDto> {

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
        // intentionally left blank
    }

    @Override
    public UserInfoDto deserialize(String arg0, byte[] arg1) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfoDto user;
        try {
            user = objectMapper.readValue( arg1,UserInfoDto.class);
        }catch (Exception e){
            throw  new RuntimeException("Something went wrong during Deserialize" , e.getCause());
        }
        return user;
    }

    @Override
    public void close() {}
}
