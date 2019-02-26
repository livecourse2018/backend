package com.education.backend.resources.vos;

public class LoginRequestVO {
    private String email;
    private String password;

    public LoginRequestVO() {}

    public LoginRequestVO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
