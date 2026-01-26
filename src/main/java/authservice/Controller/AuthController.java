package authservice.Controller;

import authservice.Entities.RefreshToken;
import authservice.Response.JwtResponseDto;
import authservice.Service.JWTService;
import authservice.Service.RefreshTokenService;
import authservice.Service.UserDetailsServiceImpl;
import authservice.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// AuthController for When user Signup First Time
@RestController
public class AuthController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @PostMapping("/auth/v1/signup")
    public ResponseEntity<?> Signup(@RequestBody UserDto userDto){
        try {
            Boolean isSignUp = userDetailsServiceImpl.signUp(userDto);
            if (!isSignUp) {
                return new ResponseEntity<>("User Already Present", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDto.getUsername());
            String jwtToken = jwtService.GenerateToken(userDto.getUsername());
            return new ResponseEntity<>(JwtResponseDto.builder()
                    .accessToken(jwtToken)
                    .token(refreshToken.getToken()).build(), HttpStatus.OK);
        }catch (Exception ex){
            return  new ResponseEntity<>("Exception"+ ex.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
