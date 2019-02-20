package com.example.restserver.model;

public class User {
    private String id;
    private String username;
    private String password;
    private boolean isDisable;
    private boolean isLocked;
    private boolean isExpired;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String id, String username, String password, boolean isDisable, boolean isLocked, boolean isExpired) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isDisable = isDisable;
        this.isLocked = isLocked;
        this.isExpired = isExpired;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDisable() {
        return isDisable;
    }

    public void setDisable(boolean disable) {
        isDisable = disable;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

}
