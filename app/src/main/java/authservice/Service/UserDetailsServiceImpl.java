package authservice.Service;

import authservice.Entities.User;
import authservice.Repository.UserRepo;
import authservice.eventProducer.UserInfoProducer;
import authservice.models.UserDto;
import authservice.models.UserEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoProducer userInfoProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null){
            throw  new UsernameNotFoundException("Could not found User..");
        }
        return new CustomUserDetails(user);
    }
//
    public User userPresent(UserDto userDto){
        return userRepo.findByUsername(userDto.getUsername());
    }

    public Boolean signUp(UserDto userDto){ //method call when user try to Signup //
        // This condition is true when user is already present
        // nonNull() : return true if present , return false if not present
        if(Objects.nonNull(userPresent(userDto))){
            // We return false because user try to create new profile that Already Present
            return false;
        }

        String userId = UUID.randomUUID().toString();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepo.save(new User(userId , userDto.getUsername() , userDto.getPassword() , new HashSet<>()));
        userInfoProducer.sendEvent(userEventPublish(userDto , userId));
        return  true;
    }

    private UserEvent userEventPublish(UserDto userDto ,String userId){
        return UserEvent.builder()
                .userId(userId)
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .emailId(userDto.getEmailId())
                .phoneNo(userDto.getPhoneNo())
                .build();
    }
}
