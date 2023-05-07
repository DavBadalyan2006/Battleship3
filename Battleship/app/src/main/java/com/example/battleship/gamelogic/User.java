package com.example.battleship.gamelogic;

public class User {

    private String uid;
    private String email;
    private boolean online;
    private String username;


    public User(String uid, String username, String email, boolean online) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.online = online;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}


