package com.domain.model;

import lombok.Data;

@Data
public class User {

    private int id;
    private String login;
    private String email;
    private String passport;
    private String role;
    private String password;
    private int userRoleId;
    private boolean userStateActive;

}
