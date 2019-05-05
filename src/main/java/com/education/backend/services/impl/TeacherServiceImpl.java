package com.education.backend.services.impl;

import com.education.backend.db.DBClient;
import com.education.backend.db.DBDao;
import com.education.backend.resources.vos.TeacherRegistrationRequestVO;
import com.education.backend.services.IdentityService;
import com.education.backend.services.TeacherService;
import com.education.backend.services.objects.TeacherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    DBClient dbClient = new DBDao();
    private static final Logger logger = LoggerFactory.getLogger(IdentityService.class);

    @Override
    public TeacherInfo SignUpAsTeacher(TeacherRegistrationRequestVO teacherRegistrationRequestVO) {
        logger.info(String.format("Trying to register as teacher with email '%s'", teacherRegistrationRequestVO.getEmail()));
        boolean isTeacherCreated = dbClient.signupAsTeacher(teacherRegistrationRequestVO);
        if (!isTeacherCreated) {
            logger.warn(String.format("Cannot create teacher '%s'", teacherRegistrationRequestVO.getEmail()));
            return null;
        }
        logger.info(String.format("Teacher '%s' has been created.", teacherRegistrationRequestVO.getEmail()));
        TeacherInfo teacherInfo = dbClient.findTeacher(teacherRegistrationRequestVO.getEmail());
        return teacherInfo == null ? null :
                new TeacherInfo(
                        teacherInfo.getEmail(),
                        teacherInfo.getFirstName(),
                        teacherInfo.getLastName(),
                        teacherInfo.getDisplayName(),
                        teacherInfo.getDescription());
    }

    @Override
    public List<TeacherInfo> getAllTeachers() {
        logger.info(String.format("Trying to get teacher list."));
        return dbClient.getTeachersList();
    }

    @Override
    public boolean findTeacher(String email) {
        logger.info(String.format("Trying to find teacher '%s'", email));
        return dbClient.findTeacher(email) != null;
    }
}
