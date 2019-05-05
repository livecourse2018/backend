package com.education.backend.services;

import com.education.backend.resources.vos.TeacherRegistrationRequestVO;
import com.education.backend.services.objects.TeacherInfo;

import java.util.List;

public interface TeacherService {

    TeacherInfo SignUpAsTeacher(TeacherRegistrationRequestVO teacherRegistrationRequestVO);

    List<TeacherInfo> getAllTeachers();

    boolean findTeacher(String email);

}
