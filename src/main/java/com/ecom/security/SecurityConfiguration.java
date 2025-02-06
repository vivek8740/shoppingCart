package com.ecom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration // Marks this class as a configuration class for Spring Security
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Bean to encode passwords using BCrypt
    }

    @Bean
    public UserDetailsService userDetailService() {
        return new UserDetailsServiceImpl(); // Custom implementation of UserDetailsService
    }

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler; // Custom authentication success handler

    @Autowired
    @Lazy
    private AuthFailureHandlerImpl authFailureHandlerImpl;

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider(){
       DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
       authenticationProvider.setUserDetailsService(userDetailService()); // Setting custom user details service
       authenticationProvider.setPasswordEncoder(passwordEncoder()); // Setting password encoder
       return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity http) throws Exception{
        
        // Configuring security filters
        http.csrf( csrf -> csrf.disable()) // Disabling CSRF protection
            .cors(cors -> cors.disable()) // Disabling CORS
            .authorizeHttpRequests(request -> request.requestMatchers("/user/**").hasRole("USER") // Restricting access to /user/** for ROLE_USER
                                                     .requestMatchers("/admin/**").hasRole("ADMIN") // Restricting access to /admin/** for ROLE_ADMIN
                                                     .requestMatchers("/**").permitAll()) // Allowing access to all other endpoints
            
            .formLogin(form -> form.loginPage("/login") // Custom login page URL
                                    .loginProcessingUrl("/login") // URL to process login requests
                                    .failureHandler(authFailureHandlerImpl)
                                    .successHandler(authenticationSuccessHandler)) // Handling successful authentication
            
            .logout( logout -> logout.permitAll()); // Allowing all users to log out
        
        return http.build(); // Building the security filter chain
    }

}
