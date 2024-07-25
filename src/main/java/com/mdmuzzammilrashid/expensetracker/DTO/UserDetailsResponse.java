package com.mdmuzzammilrashid.expensetracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetailsResponse {
    private String userId;
    private String username;
    private String email;
    private String displayName;
    private Boolean verified ;
    private String avatar;
    private String budget;

}
