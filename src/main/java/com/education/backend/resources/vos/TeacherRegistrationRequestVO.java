package com.education.backend.resources.vos;

public class TeacherRegistrationRequestVO {
    private String firstName;
    private String lastName;
    private String displayName;
    private String email;
    private String password;
    private String description;

    public TeacherRegistrationRequestVO(){};

    public TeacherRegistrationRequestVO(String firstName, String lastName, String displayName, String email, String password, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
