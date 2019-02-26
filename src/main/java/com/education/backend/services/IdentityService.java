package com.education.backend.services;

import com.education.backend.resources.vos.SignupRequestVO;
import com.education.backend.services.objects.UserInfo;

public interface IdentityService {
    UserInfo loginWithPwd(String email, String password);

    UserInfo signupWithPwd(SignupRequestVO signupRequestVO);

    boolean findUser(String email);
}
