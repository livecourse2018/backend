package com.education.backend.services;

import com.education.backend.resources.vos.CourseVO;
import com.education.backend.db.model.CourseRegistration;

import java.util.List;

public interface ContentService {
    boolean getUserCourseRegistrationStatus(String email, String courseId);

    CourseRegistration getRegisteredCourses(String email);

    boolean registerCourse(String email, String courseId);

    boolean uploadCourse(CourseVO courseVO);

    List<CourseVO> getAllCourses();
}
