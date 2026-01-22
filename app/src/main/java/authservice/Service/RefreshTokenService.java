package authservice.Service;

import authservice.Entities.RefreshToken;
import authservice.Entities.User;
import authservice.Repository.RefreshTokenRepo;
import authservice.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired RefreshTokenRepo refreshTokenRepo;

    @Autowired UserRepo userRepo;
//create refresh token by finding user by id and in to token and expire.
    public RefreshToken createRefreshToken(String userName){
        User user = userRepo.findByUsername(userName);
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .tokenExpire(Instant.now().plusMillis(600000)) // expiry of refresh token is last longer then jwt
                .build();
        return refreshTokenRepo.save(refreshToken);
    }
// Check expire //

    public RefreshToken verifyExpire(RefreshToken token){
// Condition < 0 : true if current time is more than token Expire Time(difference is neg)
        //false if there is some time left for Token expire (difference is poss)
        if (token.getTokenExpire().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(token);
            throw  new InvalidTokenException(token.getToken() + "Refresh token is expire please login again..");
        }
        // if not expire return it //
        return token;
    }

// Refresh token repository method called
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepo.findByToken(token);
    }

}
