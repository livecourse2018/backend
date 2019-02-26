package com.education.backend.services.impl;

import com.education.backend.db.DBClient;
import com.education.backend.db.DBDao;
import com.education.backend.db.model.User;
import com.education.backend.resources.vos.SignupRequestVO;
import com.education.backend.services.IdentityService;
import com.education.backend.services.objects.UserInfo;

public class IdentityServiceImpl implements IdentityService {
    DBClient dbClient = new DBDao();

    @Override
    public UserInfo loginWithPwd(String email, String password) {
        User user = dbClient.loginWithPassword(email, password);
        return user == null ? null : new UserInfo(user.getEmail(), user.getDisplayName());
    }

    @Override
    public UserInfo signupWithPwd(SignupRequestVO signupRequestVO) {
        boolean isUserCreated = dbClient.signupWithPassword(signupRequestVO);
        if (!isUserCreated) {
            return null;
        }
        User user = dbClient.findUser(signupRequestVO.getEmail());
        return user == null ? null : new UserInfo(user.getEmail(), user.getDisplayName());
    }

    @Override
    public boolean findUser(String email) {
        return dbClient.findUser(email) != null;
    }
}
