package com.mdmuzzammilrashid.expensetracker.Entity;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    private String username;
    private String email;
    private String password;
    private String displayName;

    private Boolean verified = false;
    private Number verificationOTP;
    private LocalDateTime verificationOTPExpiry;
    
    private Number forgetPasswordCode;
    private LocalDateTime forgetPasswordCodeExpiry;

    private String accessToken;
    private String refreshToken;

    
}
