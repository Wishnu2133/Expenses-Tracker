package authservice.Controller;

import authservice.Entities.RefreshToken;
import authservice.Request.AuthRequestDto;
import authservice.Request.RefreshTokenRequestDto;
import authservice.Response.JwtResponseDto;
import authservice.Service.JWTService;
import authservice.Service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth/v1")
public class TokenController {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JWTService jwtService;

    public TokenController(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto){

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername() , authRequestDto.getPassword()));
            if (authentication.isAuthenticated()) {
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDto.getUsername());
                return new ResponseEntity<>(JwtResponseDto.builder()
                        .accessToken(jwtService.GenerateToken(authRequestDto.getUsername()))
                        .token(refreshToken.getToken())
                        .build(),HttpStatus.OK);
            } else {
            return  new ResponseEntity<>("Something went wrong Try After SomeTime " , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
         return refreshTokenService.findByToken(refreshTokenRequestDto.getToken())
                .map(refreshTokenService::verifyExpire)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.GenerateToken(user.getUsername());
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDto.getToken()).build();
                }).orElseThrow(() -> new RuntimeException("RefreshToken  is not in DB"));
    }
}


//  613556 8143