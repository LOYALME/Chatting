package com.example.sideproject;

public class ChatDTO {
    private String email;
    private String msg;
    private String nickName;

    public ChatDTO() {
    }

    public ChatDTO(String email, String msg, String nickName) {
        this.email = email;
        this.msg = msg;
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
