package com.mdmuzzammilrashid.expensetracker.models;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UserModel {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String displayName;
    private ArrayList<String> tags;

}
