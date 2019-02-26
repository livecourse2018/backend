package com.education.backend.resources.vos;

public class SignupRequestVO {
    private String firstName;
    private String lastName;
    private String displayName;
    private String email;
    private String password;

    public SignupRequestVO(){};

    public SignupRequestVO(String firstName, String lastName, String displayName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
