package com.education.backend.services.objects;

public class UserInfo {
    private String email;
    private String displayName;

    public UserInfo(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
