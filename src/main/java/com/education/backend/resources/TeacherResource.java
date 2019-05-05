package com.education.backend.resources;

import com.education.backend.resources.vos.TeacherRegistrationRequestVO;
import com.education.backend.services.ContentService;
import com.education.backend.services.TeacherService;
import com.education.backend.services.impl.ContentServiceImpl;
import com.education.backend.services.impl.TeacherServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/teacher")
public class TeacherResource {

    private static final Logger logger = LoggerFactory.getLogger(IdentityResource.class);
    ContentService contentService = new ContentServiceImpl();
    TeacherService teacherService = new TeacherServiceImpl();

    @Path("/registration")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response registerTeacher(String requestJsonStr) {
        logger.info(String.format("Received teacher registration request with json parameters: %s", requestJsonStr));
        String jsonResponse = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TeacherRegistrationRequestVO teacherRegistrationRequestVO = objectMapper.readValue(requestJsonStr, TeacherRegistrationRequestVO.class);
            boolean isTeacherRegistered = teacherService.findTeacher(teacherRegistrationRequestVO.getEmail());
            if (isTeacherRegistered) {
                logger.warn(String.format("Teacher '%s' is existed.", teacherRegistrationRequestVO.getEmail()));
                FailureResponse failureResponse = new FailureResponse(
                        Response.Status.BAD_REQUEST.getStatusCode(),
                        "BAD_REQUEST",
                        "Teacher with email '" + teacherRegistrationRequestVO.getEmail() + "' has been registered!");
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(objectMapper.writeValueAsString(failureResponse))
                        .build();
            }
            jsonResponse = objectMapper.writeValueAsString(teacherService.SignUpAsTeacher(teacherRegistrationRequestVO));
            logger.info(String.format("Teacher registration response: %s", jsonResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok().entity(jsonResponse).build();
    }

    @Path("/teachers")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getTeachers() {
        logger.info("Received get all teachers request");
        String jsonResponse = null;
        try {
            jsonResponse = new ObjectMapper().writeValueAsString(teacherService.getAllTeachers());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Response.ok().entity(jsonResponse).build();
    }

}
