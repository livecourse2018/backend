package com.education.backend.db;

import com.education.backend.db.model.User;
import com.education.backend.resources.vos.CourseVO;
import com.education.backend.resources.vos.SignupRequestVO;
import com.education.backend.db.model.CourseRegistration;

public interface DBClient {
    User loginWithPassword(String email, String password);

    boolean signupWithPassword(SignupRequestVO signupRequestVO);

    CourseRegistration getRegisteredCourses(String email);

    boolean registerCourse(String email, String courseId);

    boolean uploadCourse(CourseVO courseVO);

    boolean getUserCourseRegistrationStatus(String email, String courseId);

    User findUser(String email);
}
