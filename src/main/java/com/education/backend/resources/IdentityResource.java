package com.education.backend.resources;

import com.education.backend.resources.vos.LoginRequestVO;
import com.education.backend.resources.vos.SignupRequestVO;
import com.education.backend.services.IdentityService;
import com.education.backend.services.impl.IdentityServiceImpl;
import com.education.backend.services.objects.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/identity")
public class IdentityResource {

    private static final Logger logger = LoggerFactory.getLogger(IdentityResource.class);
    IdentityService identityService = new IdentityServiceImpl();

    @Path("/login/pwd/user")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response login(String requestJsonStr) {
        logger.info(String.format("Received login request with json parameters: %s", requestJsonStr));
        String jsonResponse;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequestVO loginRequestVO = objectMapper.readValue(requestJsonStr, LoginRequestVO.class);
            UserInfo userInfo = identityService.loginWithPwd(loginRequestVO.getEmail(), loginRequestVO.getPassword());

            if (userInfo == null) {
                logger.warn(String.format("Cannot find user with email '%s', password '%s'", loginRequestVO.getEmail(), loginRequestVO.getPassword()));
                FailureResponse failureResponse = new FailureResponse(
                        Response.Status.UNAUTHORIZED.getStatusCode(),
                        "UNAUTHORIZED",
                        "Email or password is incorrect!");
                return Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity(objectMapper.writeValueAsString(failureResponse))
                        .build();
            }
            jsonResponse = objectMapper.writeValueAsString(userInfo);
            logger.info(String.format("Login response: %s", jsonResponse));
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok().entity(jsonResponse).build();
    }

    @Path("/signup/pwd/user")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response signup(String requestJsonStr) {
        logger.info(String.format("Received signup request with json parameters: %s", requestJsonStr));
        String jsonResponse = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SignupRequestVO signupRequestVO = objectMapper.readValue(requestJsonStr, SignupRequestVO.class);
            boolean isUserRegistered = identityService.findUser(signupRequestVO.getEmail());
            if (isUserRegistered) {
                logger.warn(String.format("User '%s' is existed.", signupRequestVO.getEmail()));
                FailureResponse failureResponse = new FailureResponse(
                        Response.Status.BAD_REQUEST.getStatusCode(),
                        "BAD_REQUEST",
                        "User with email '" + signupRequestVO.getEmail() + "' has been registered!");
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(objectMapper.writeValueAsString(failureResponse))
                        .build();
            }
            jsonResponse = objectMapper.writeValueAsString(identityService.signupWithPwd(signupRequestVO));
            logger.info(String.format("Signup response: %s", jsonResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok().entity(jsonResponse).build();
    }
}
