package com.education.backend.resources;

import com.education.backend.resources.vos.CourseVO;
import com.education.backend.services.ContentService;
import com.education.backend.services.impl.ContentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.UUID;

@Path("/content")
public class ContentResource {

    ContentService contentService = new ContentServiceImpl();

    @Path("/emails/{email}/courses/{courseId}/registration")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserCourseRegistrationStatus(@PathParam("email") String email,
                                                    @PathParam("courseId") String courseId) {
        boolean isCourseRegistered = contentService.getUserCourseRegistrationStatus(email, courseId);
        return Response.ok().entity("{\"courseRegistered\":" + isCourseRegistered + "}").build();
    }

    @Path("/emails/{email}/courses/{courseId}/registration")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    public Response registerCourse(@PathParam("email") String email,
                                   @PathParam("courseId") String courseId) {
        boolean isCourseRegistered = contentService.registerCourse(email, courseId);
        return Response.ok().entity("{\"courseRegistered\":" + isCourseRegistered + "}").build();
    }

    @Path("/emails/{email}/courses/registration")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserRegisteredCourses(@PathParam("email") String email) {
        String jsonResponse = null;
        try {
            jsonResponse = new ObjectMapper().writeValueAsString(contentService.getRegisteredCourses(email));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Response.ok().entity(jsonResponse).build();
    }

    @Path("/courses/upload")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    public Response uploadCourse(String requestJsonStr) {
        String courseId = UUID.randomUUID().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        CourseVO courseVO = null;
        try {
            courseVO = objectMapper.readValue(requestJsonStr, CourseVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        courseVO.setCourseId(courseId);
        boolean isCourseUploaded = contentService.uploadCourse(courseVO);
        return Response.ok().entity("{\"courseRegistered\":" + isCourseUploaded + "}").build();
    }

}
