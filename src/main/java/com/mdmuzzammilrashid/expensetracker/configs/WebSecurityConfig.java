package com.mdmuzzammilrashid.expensetracker.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mdmuzzammilrashid.expensetracker.services.UserDetailsServiceImplementation;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImplementation userDetailsService;

    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ 
            http.csrf(customizer->customizer.disable());

        return http.build();
    }

    // @Bean
    // @Override
    // public AuthenticationManagerBuilder authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    //     return configuration.getAuthenticationManager();
    // }

    @Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
    return configuration.getAuthenticationManager();
}



    
}
