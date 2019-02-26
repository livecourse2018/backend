package com.education.backend.db.model;

import java.util.List;

public class CourseRegistration {
    private String email;
    private List<String> registeredCourses;

    public CourseRegistration(String email, List<String> registeredCourses) {
        this.email = email;
        this.registeredCourses = registeredCourses;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRegisteredCourses() {
        return registeredCourses;
    }
}
