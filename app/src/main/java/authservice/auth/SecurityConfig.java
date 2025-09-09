package authservice.auth;

import authservice.Repository.UserRepo;
import authservice.Service.UserDetailsServiceImpl;
import authservice.eventProducer.UserInfoProducer;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@Data
@Builder
public class SecurityConfig {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserDetailsServiceImpl userDetailsServiceImpl;


    @Bean
    @Autowired
    public UserDetailsService userDetailsService(UserRepo userRepo , PasswordEncoder passwordEncoder , UserInfoProducer userInfoProducer){
        return new UserDetailsServiceImpl(userRepo , passwordEncoder, userInfoProducer);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http , JwtAuthFilter jwtAuthFilter) throws Exception{

        return http
                // cross site request  :
                .csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                // don't apply jwtAuth Filter on given request permitAll them without filter
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/v1/login" , "/auth/v1/refreshToken" , "/auth/v1/signup").permitAll()
                        // If any other request come then apply jwt Filter //
                        .anyRequest().authenticated()
                )
                //  Stateless : don't store given session
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
