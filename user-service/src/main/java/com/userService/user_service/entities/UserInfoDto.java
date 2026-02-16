package com.userService.user_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true) // If some properties are not match then it will Ignore
public class UserInfoDto {

    @Id
    @JsonProperty("user-Id")
    @NonNull
    private String userId;

    @JsonProperty("first_name")
    @NonNull
    private String firstName;

    @JsonProperty("last_name")
    @NonNull
    private String lastName;

    @JsonProperty("phone_no")
    @NonNull
    private Long phoneNo;

    @JsonProperty("email_Id")
    @NonNull
    private String emailId;

    public UserInfo convertToEntity(){
        return  UserInfo.builder()
                .firstName(firstName)
                .lastName(lastName)
                .emailId(emailId)
                .phoneNo(phoneNo).build();
    }

}
