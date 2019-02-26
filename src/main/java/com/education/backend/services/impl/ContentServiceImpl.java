package com.education.backend.services.impl;

import com.education.backend.db.DBClient;
import com.education.backend.db.DBDao;
import com.education.backend.resources.vos.CourseVO;
import com.education.backend.services.ContentService;
import com.education.backend.db.model.CourseRegistration;

public class ContentServiceImpl implements ContentService {
    DBClient dbClient = new DBDao();

    @Override
    public boolean getUserCourseRegistrationStatus(String email, String courseId) {
        return dbClient.getUserCourseRegistrationStatus(email, courseId);
    }

    @Override
    public CourseRegistration getRegisteredCourses(String email) {
        return dbClient.getRegisteredCourses(email);
    }

    @Override
    public boolean registerCourse(String email, String courseId) {
        return dbClient.registerCourse(email, courseId);
    }

    @Override
    public boolean uploadCourse(CourseVO courseVO) {
        return dbClient.uploadCourse(courseVO);
    }
}
