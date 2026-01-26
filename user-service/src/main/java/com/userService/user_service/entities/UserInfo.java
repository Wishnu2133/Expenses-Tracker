package com.userService.user_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true) // If some properties are not match then it will Ignore
public class UserInfo {

    @Id
    @JsonProperty("user-Id")
    @NonNull
    private String userId;

    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

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

}
