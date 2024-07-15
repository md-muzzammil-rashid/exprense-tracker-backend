package com.mdmuzzammilrashid.expensetracker.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    // @Bean
    // SecurityFilterChain securityFilterChain(HttpSecurity http){
    //     http
    //         .cors()
    //         .and()
    //         .csrf()
    //         .disable()
    //         .authorizeRequests()
    //         .antMatchers(["/api/v1/users/register"]).permitAll();
    // }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ 
        // http
            // .cors()
            // .and()
            // .csrf()
            // .disable()
            // .authorizeRequests()
            // .requestMatchers("/api/v1/users/register").permitAll().anyRequest().authenticated();
            // return http.build();

            http.csrf(customizer->customizer.disable());

        return http.build();
        }

    
}
