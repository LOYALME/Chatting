package com.example.sideproject;

public class ChattingBangListDTO {
    private String nickname;

    public ChattingBangListDTO() {
    }

    public ChattingBangListDTO(String username) {
        this.nickname = username;
    }

    public String getUsername() {
        return nickname;
    }

    public void setUsername(String username) {
        this.nickname = username;
    }
}
