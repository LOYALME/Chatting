package com.example.sideproject;

public class UserDTO {
    String id;
    String password;
    String nickname;
    String token;
    String uid;

    public UserDTO() { }
    public UserDTO(String id, String password, String nickname, String token, String uid) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.token = token;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {

        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}



