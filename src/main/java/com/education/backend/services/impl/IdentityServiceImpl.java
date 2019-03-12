package com.education.backend.services.impl;

import com.education.backend.db.DBClient;
import com.education.backend.db.DBDao;
import com.education.backend.db.model.User;
import com.education.backend.resources.vos.SignupRequestVO;
import com.education.backend.services.IdentityService;
import com.education.backend.services.objects.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdentityServiceImpl implements IdentityService {
    DBClient dbClient = new DBDao();
    private static final Logger logger = LoggerFactory.getLogger(IdentityService.class);

    @Override
    public UserInfo loginWithPwd(String email, String password) {
        logger.info(String.format("Trying to login with email '%s', password '%s'", email, password));
        User user = dbClient.loginWithPassword(email, password);
        return user == null ? null : new UserInfo(user.getEmail(), user.getDisplayName());
    }

    @Override
    public UserInfo signupWithPwd(SignupRequestVO signupRequestVO) {
        logger.info(String.format("Trying to signup with email '%s'", signupRequestVO.getEmail()));
        boolean isUserCreated = dbClient.signupWithPassword(signupRequestVO);
        if (!isUserCreated) {
            logger.warn(String.format("Cannot create user '%s'", signupRequestVO.getEmail()));
            return null;
        }
        logger.info(String.format("User '%s' has been created.", signupRequestVO.getEmail()));
        User user = dbClient.findUser(signupRequestVO.getEmail());
        return user == null ? null : new UserInfo(user.getEmail(), user.getDisplayName());
    }

    @Override
    public boolean findUser(String email) {
        logger.info(String.format("Trying to find user '%s'", email));
        return dbClient.findUser(email) != null;
    }
}
