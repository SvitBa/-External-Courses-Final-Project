package com.entity;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String login;
    private String email;

    private String passport;

    private String role;

    private String password;
    private int userRoleId;
    private boolean userStateActive;

    private User(String login, String email, String password, String passport) {
        this.id = 1;
        this.login = login;
        this.email = email;
        this.password = password;
        this.passport = passport;
        this.userRoleId = 1;
        getUserRole(userRoleId);
        this.userStateActive = true;
    }

    public User(int id, String login, String email, String passport, String password) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.passport = passport;
        this.userRoleId = 1;
        getUserRole(userRoleId);
        this.userStateActive = true;
    }

    public User(int id, String login, String email, String passport, String password, int userRoleId, boolean userStateActive) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.passport = passport;
        this.userRoleId = userRoleId;
        getUserRole(userRoleId);
        this.userStateActive = userStateActive;
    }

    public static User createUser(String login, String email, String password, String passport) {
        return new User(login, email, password, passport);
    }

    private void getUserRole(int userRoleId) {
        switch (userRoleId) {
            case 2:
                setRole("manager");
                break;
            case 3:
                setRole("admin");
                break;
            default: setRole("client");
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRole) {
        this.userRoleId = userRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isUserStateActive() {
        return userStateActive;
    }

    public void setUserStateActive(boolean userStateActive) {
        this.userStateActive = userStateActive;
    }

    public void setDocument(String passport) {
        this.passport = passport;
    }

    public String getPassport() {
        return passport;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", passport='" + passport + '\'' +
                ", role='" + role + '\'' +
                ", userRoleId=" + userRoleId +
                ", userStateActive=" + userStateActive +
                '}';
    }
}
