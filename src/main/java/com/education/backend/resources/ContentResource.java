package com.education.backend.resources;

import com.education.backend.resources.vos.CourseVO;
import com.education.backend.services.ContentService;
import com.education.backend.services.impl.ContentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.UUID;

@Path("/content")
public class ContentResource {

    private static final Logger logger = LoggerFactory.getLogger(IdentityResource.class);
    ContentService contentService = new ContentServiceImpl();

    @Path("/emails/{email}/courses/{courseId}/registration")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserCourseRegistrationStatus(@PathParam("email") String email,
                                                    @PathParam("courseId") String courseId) {
        logger.info(String.format("Received getUserCourseRegistrationStatus request for user '%s' and course '%s'", email, courseId));
        boolean isCourseRegistered = contentService.getUserCourseRegistrationStatus(email, courseId);
        return Response.ok().entity("{\"courseRegistered\":" + isCourseRegistered + "}").build();
    }

    @Path("/emails/{email}/courses/{courseId}/registration")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    public Response registerCourse(@PathParam("email") String email,
                                   @PathParam("courseId") String courseId) {
        logger.info(String.format("Received user register course request for user '%s' and course '%s'", email, courseId));
        boolean isCourseRegistered = contentService.registerCourse(email, courseId);
        return Response.ok().entity("{\"courseRegistered\":" + isCourseRegistered + "}").build();
    }

    @Path("/emails/{email}/courses/registration")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserRegisteredCourses(@PathParam("email") String email) {
        logger.info(String.format("Received getUserRegisteredCourses request for user '%s'", email));
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
        logger.info(String.format("Received upload course request with json parameters: %s", requestJsonStr));
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

    @Path("/courses")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getCourses() {
        logger.info("Received get all courses request");
        String jsonResponse = null;
        try {
            jsonResponse = new ObjectMapper().writeValueAsString(contentService.getAllCourses());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Response.ok().entity(jsonResponse).build();
    }

    @Path("/courses/{courseId}")
    @GET
    @Produces({ MediaType.TEXT_HTML })
    public Response getCoursePage() {
        logger.info("Received get course page request");
//        String jsonResponse = null;
//        try {
//            jsonResponse = new ObjectMapper().writeValueAsString(contentService.getAllCourses());
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        return Response.ok().entity(getCoursePageTemplate()).build();
    }

    private String getCoursePageTemplate() {
        return "<!DOCTYPE HTML>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <title>中华学堂</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <meta name=\"description\" content=\"\"/>\n" +
                "    <meta name=\"keywords\" content=\"\"/>\n" +
                "    <meta name=\"author\" content=\"\"/>\n" +
                "\n" +
                "    <!-- Facebook and Twitter integration -->\n" +
                "    <meta property=\"og:title\" content=\"\"/>\n" +
                "    <meta property=\"og:image\" content=\"\"/>\n" +
                "    <meta property=\"og:url\" content=\"\"/>\n" +
                "    <meta property=\"og:site_name\" content=\"\"/>\n" +
                "    <meta property=\"og:description\" content=\"\"/>\n" +
                "    <meta name=\"twitter:title\" content=\"\"/>\n" +
                "    <meta name=\"twitter:image\" content=\"\"/>\n" +
                "    <meta name=\"twitter:url\" content=\"\"/>\n" +
                "    <meta name=\"twitter:card\" content=\"\"/>\n" +
                "\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Rubik:300,400,500,700,900\" rel=\"stylesheet\">\n" +
                "\n" +
                "    <!-- Animate.css -->\n" +
                "    <link rel=\"stylesheet\" href=\"http://82.169.67.46/css/animate.css\">\n" +
                "    <!-- Icomoon Icon Fonts-->\n" +
                "    <link rel=\"stylesheet\" href=\"http://82.169.67.46/css/icomoon.css\">\n" +
                "    <!-- Bootstrap  -->\n" +
                "    <link rel=\"stylesheet\" href=\"http://82.169.67.46/css/bootstrap.css\">\n" +
                "\n" +
                "    <!-- Magnific Popup -->\n" +
                "    <link rel=\"stylesheet\" href=\"http://82.169.67.46/css/magnific-popup.css\">\n" +
                "\n" +
                "    <!-- Flexslider  -->\n" +
                "    <link rel=\"stylesheet\" href=\"http://82.169.67.46/css/flexslider.css\">\n" +
                "\n" +
                "    <!-- Owl Carousel -->\n" +
                "    <link rel=\"stylesheet\" href=\"http://82.169.67.46/css/owl.carousel.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"http://82.169.67.46/css/owl.theme.default.min.css\">\n" +
                "\n" +
                "    <!-- Flaticons  -->\n" +
                "    <link rel=\"stylesheet\" href=\"fonts/flaticon/font/flaticon.css\">\n" +
                "\n" +
                "    <!-- Theme style  -->\n" +
                "    <link rel=\"stylesheet\" href=\"http://82.169.67.46/css/style.css\">\n" +
                "\n" +
                "    <!-- Navigation style  -->\n" +
                "    <link rel=\"stylesheet\" href=\"http://82.169.67.46/css/navigation.css\">\n" +
                "\n" +
                "    <!-- Modernizr JS -->\n" +
                "    <script src=\"http://82.169.67.46/js/modernizr-2.6.2.min.js\"></script>\n" +
                "    <!-- FOR IE9 below -->\n" +
                "    <!--[if lt IE 9]>\n" +
                "    <script src=\"http://82.169.67.46/js/respond.min.js\"></script>\n" +
                "    <![endif]-->\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"colorlib-loader\"></div>\n" +
                "\n" +
                "<div id=\"page\">\n" +
                "    <div id=\"navigation\"></div>\n" +
                "    <aside id=\"colorlib-hero\">\n" +
                "        <div class=\"flexslider\">\n" +
                "            <ul class=\"slides\">\n" +
                "                <li style=\"background-image: url(images/img_bg_2.jpg);\">\n" +
                "                    <div class=\"overlay\"></div>\n" +
                "                    <div class=\"container-fluid\">\n" +
                "                        <div class=\"row\">\n" +
                "                            <div class=\"col-md-6 col-sm-12 col-md-offset-3 col-xs-12 slider-text\">\n" +
                "                                <div class=\"slider-text-inner text-center\">\n" +
                "                                    <h1>小学6年级数学 （人教版）</h1>\n" +
                "                                    <h2 class=\"breadcrumbs\"><span><a href=\"index.html\">主页</a></span> | <span>课程</span>\n" +
                "                                    </h2>\n" +
                "                                    <a href=\"classroom.html\">\n" +
                "                                        <button type=\"button\" class=\"btn btn-primary\">开 始 上 课</button>\n" +
                "                                    </a>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </aside>\n" +
                "\n" +
                "    <div id=\"colorlib-counter\" class=\"colorlib-counters\">\n" +
                "        <div class=\"container\">\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col-md-7\">\n" +
                "                    <div class=\"about-desc\">\n" +
                "                        <div class=\"about-img-1 animate-box\"\n" +
                "                             style=\"background-image: url(images/about-img-2.jpg);\"></div>\n" +
                "                        <!--<div class=\"about-img-2 animate-box\" style=\"background-image: url(images/about-img-1.jpg);\"></div>-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"col-md-5\">\n" +
                "                    <div class=\"row\">\n" +
                "                        <div class=\"col-md-12 colorlib-heading animate-box\">\n" +
                "                            <h1 class=\"heading-big\">老师介绍</h1>\n" +
                "                            <h2>老师介绍</h2>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"row\">\n" +
                "                        <div class=\"col-md-12 animate-box\">\n" +
                "                            <p><strong>北京市数学特级教师 宋启光 老师</strong></p>\n" +
                "                            <p>Even the all-powerful Pointing has no control about the blind texts it is an almost\n" +
                "                                unorthographic life One day however a small line of blind text by the name of Lorem\n" +
                "                                Ipsum decided to leave for the far World of Grammar.</p>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"colorlib-about\">\n" +
                "        <div class=\"container\">\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col-md-6\">\n" +
                "                    <h3>课程介绍</h3>\n" +
                "                    <p>On her way she met a copy. The copy warned the Little Blind Text, that where it came from it\n" +
                "                        would have been rewritten a thousand times and everything that was left from its origin would be\n" +
                "                        the word \"and\" and the Little Blind Text should turn around and return to its own, safe country.\n" +
                "                        But nothing the copy said could convince her and so it didn’t take long until a few insidious\n" +
                "                        Copy Writers ambushed her, made her drunk with Longe and Parole and dragged her into their\n" +
                "                        agency, where they abused her for their.</p>\n" +
                "                </div>\n" +
                "                <div class=\"col-md-6\">\n" +
                "                    <div class=\"fancy-collapse-panel\">\n" +
                "                        <div class=\"panel-group\" id=\"accordion\" role=\"tablist\" aria-multiselectable=\"true\">\n" +
                "                            <div class=\"panel panel-default\">\n" +
                "                                <div class=\"panel-heading\" role=\"tab\" id=\"headingOne\">\n" +
                "                                    <h4 class=\"panel-title\">\n" +
                "                                        <a data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapseOne\"\n" +
                "                                           aria-expanded=\"true\" aria-controls=\"collapseOne\">教学目标\n" +
                "                                        </a>\n" +
                "                                    </h4>\n" +
                "                                </div>\n" +
                "                                <div id=\"collapseOne\" class=\"panel-collapse collapse in\" role=\"tabpanel\"\n" +
                "                                     aria-labelledby=\"headingOne\">\n" +
                "                                    <div class=\"panel-body\">\n" +
                "                                        <div class=\"row\">\n" +
                "                                            <div class=\"col-md-6\">\n" +
                "                                                <p>Far far away, behind the word mountains, far from the countries\n" +
                "                                                    Vokalia and Consonantia, there live the blind texts. </p>\n" +
                "                                            </div>\n" +
                "                                            <div class=\"col-md-6\">\n" +
                "                                                <p>Separated they live in Bookmarksgrove right at the coast of the\n" +
                "                                                    Semantics, a large language ocean.</p>\n" +
                "                                            </div>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"panel panel-default\">\n" +
                "                                <div class=\"panel-heading\" role=\"tab\" id=\"headingTwo\">\n" +
                "                                    <h4 class=\"panel-title\">\n" +
                "                                        <a class=\"collapsed\" data-toggle=\"collapse\" data-parent=\"#accordion\"\n" +
                "                                           href=\"#collapseTwo\" aria-expanded=\"false\" aria-controls=\"collapseTwo\">教学安排\n" +
                "                                        </a>\n" +
                "                                    </h4>\n" +
                "                                </div>\n" +
                "                                <div id=\"collapseTwo\" class=\"panel-collapse collapse\" role=\"tabpanel\"\n" +
                "                                     aria-labelledby=\"headingTwo\">\n" +
                "                                    <div class=\"panel-body\">\n" +
                "                                        <p>Far far away, behind the word <strong>mountains</strong>, far from the\n" +
                "                                            countries Vokalia and Consonantia, there live the blind texts. Separated\n" +
                "                                            they live in Bookmarksgrove right at the coast of the Semantics, a large\n" +
                "                                            language ocean.</p>\n" +
                "                                        <ul>\n" +
                "                                            <li>Separated they live in Bookmarksgrove right</li>\n" +
                "                                            <li>Separated they live in Bookmarksgrove right</li>\n" +
                "                                        </ul>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"panel panel-default\">\n" +
                "                                <div class=\"panel-heading\" role=\"tab\" id=\"headingThree\">\n" +
                "                                    <h4 class=\"panel-title\">\n" +
                "                                        <a class=\"collapsed\" data-toggle=\"collapse\" data-parent=\"#accordion\"\n" +
                "                                           href=\"#collapseThree\" aria-expanded=\"false\" aria-controls=\"collapseThree\">学习资料\n" +
                "                                        </a>\n" +
                "                                    </h4>\n" +
                "                                </div>\n" +
                "                                <div id=\"collapseThree\" class=\"panel-collapse collapse\" role=\"tabpanel\"\n" +
                "                                     aria-labelledby=\"headingThree\">\n" +
                "                                    <div class=\"panel-body\">\n" +
                "                                        <p>Far far away, behind the word <strong>mountains</strong>, far from the\n" +
                "                                            countries Vokalia and Consonantia, there live the blind texts. Separated\n" +
                "                                            they live in Bookmarksgrove right at the coast of the Semantics, a large\n" +
                "                                            language ocean.</p>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <footer id=\"colorlib-footer\">\n" +
                "        <div class=\"container\">\n" +
                "            <div class=\"row row-pb-md\">\n" +
                "                <div class=\"col-md-3 colorlib-widget\">\n" +
                "                    <h4>联系方式</h4>\n" +
                "                    <ul class=\"colorlib-footer-links\">\n" +
                "                        <li>291 South 21th Street, <br> Suite 721 New York NY 10016</li>\n" +
                "                        <li><a href=\"tel://1234567920\"><i class=\"icon-phone\"></i> + 1235 2355 98</a></li>\n" +
                "                        <li><a href=\"mailto:info@yoursite.com\"><i class=\"icon-envelope\"></i> info@yoursite.com</a></li>\n" +
                "                        <li><a href=\"http://luxehotel.com\"><i class=\"icon-location4\"></i> yourwebsite.com</a></li>\n" +
                "                    </ul>\n" +
                "                </div>\n" +
                "                <div class=\"col-md-2 colorlib-widget\">\n" +
                "                    <h4>Programs</h4>\n" +
                "                    <p>\n" +
                "                    <ul class=\"colorlib-footer-links\">\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Diploma Degree</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> BS Degree</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Beginner</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Intermediate</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Advance</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Difficulty</a></li>\n" +
                "                    </ul>\n" +
                "                    </p>\n" +
                "                </div>\n" +
                "                <div class=\"col-md-2 colorlib-widget\">\n" +
                "                    <h4>Useful Links</h4>\n" +
                "                    <p>\n" +
                "                    <ul class=\"colorlib-footer-links\">\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> About Us</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Testimonials</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Courses</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Event</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> News</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Contact</a></li>\n" +
                "                    </ul>\n" +
                "                    </p>\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"col-md-2 colorlib-widget\">\n" +
                "                    <h4>Support</h4>\n" +
                "                    <p>\n" +
                "                    <ul class=\"colorlib-footer-links\">\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Documentation</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Forums</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Help &amp; Support</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Scholarship</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Student Transport</a></li>\n" +
                "                        <li><a href=\"#\"><i class=\"icon-check\"></i> Release Status</a></li>\n" +
                "                    </ul>\n" +
                "                    </p>\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"col-md-3 colorlib-widget\">\n" +
                "                    <h4>Recent Post</h4>\n" +
                "                    <div class=\"f-blog\">\n" +
                "                        <a href=\"blog.html\" class=\"blog-img\" style=\"background-image: url(images/blog-1.jpg);\">\n" +
                "                        </a>\n" +
                "                        <div class=\"desc\">\n" +
                "                            <h2><a href=\"blog.html\">Creating Mobile Apps</a></h2>\n" +
                "                            <p class=\"admin\"><span>18 April 2018</span></p>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"f-blog\">\n" +
                "                        <a href=\"blog.html\" class=\"blog-img\" style=\"background-image: url(images/blog-2.jpg);\">\n" +
                "                        </a>\n" +
                "                        <div class=\"desc\">\n" +
                "                            <h2><a href=\"blog.html\">Creating Mobile Apps</a></h2>\n" +
                "                            <p class=\"admin\"><span>18 April 2018</span></p>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"copy\">\n" +
                "            <div class=\"container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"col-md-12 text-center\">\n" +
                "                        <p>\n" +
                "                            <small class=\"block\">&copy;\n" +
                "                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->\n" +
                "                                Copyright &copy;<script>document.write(new Date().getFullYear());</script>\n" +
                "                                All rights reserved | This template is made with <i class=\"icon-heart\"\n" +
                "                                                                                    aria-hidden=\"true\"></i> by <a\n" +
                "                                        href=\"https://colorlib.com\" target=\"_blank\">Colorlib</a>\n" +
                "                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->\n" +
                "                            </small>\n" +
                "                            <br>\n" +
                "                            <small class=\"block\">Demo Images: <a href=\"http://unsplash.co/\" target=\"_blank\">Unsplash</a>,\n" +
                "                                <a href=\"http://pexels.com/\" target=\"_blank\">Pexels</a></small>\n" +
                "                        </p>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </footer>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"gototop js-top\">\n" +
                "    <a href=\"#\" class=\"js-gotop\"><i class=\"icon-arrow-up2\"></i></a>\n" +
                "</div>\n" +
                "\n" +
                "<!-- jQuery -->\n" +
                "<script src=\"http://82.169.67.46/js/jquery.min.js\"></script>\n" +
                "<!-- jQuery Easing -->\n" +
                "<script src=\"http://82.169.67.46/js/jquery.easing.1.3.js\"></script>\n" +
                "<!-- Bootstrap -->\n" +
                "<script src=\"http://82.169.67.46/js/bootstrap.min.js\"></script>\n" +
                "<!-- Waypoints -->\n" +
                "<script src=\"http://82.169.67.46/js/jquery.waypoints.min.js\"></script>\n" +
                "<!-- Stellar Parallax -->\n" +
                "<script src=\"http://82.169.67.46/js/jquery.stellar.min.js\"></script>\n" +
                "<!-- Flexslider -->\n" +
                "<script src=\"http://82.169.67.46/js/jquery.flexslider-min.js\"></script>\n" +
                "<!-- Owl carousel -->\n" +
                "<script src=\"http://82.169.67.46/js/owl.carousel.min.js\"></script>\n" +
                "<!-- Magnific Popup -->\n" +
                "<script src=\"http://82.169.67.46/js/jquery.magnific-popup.min.js\"></script>\n" +
                "<script src=\"http://82.169.67.46/js/magnific-popup-options.js\"></script>\n" +
                "<!-- Counters -->\n" +
                "<script src=\"http://82.169.67.46/js/jquery.countTo.js\"></script>\n" +
                "<!-- Main -->\n" +
                "<script src=\"http://82.169.67.46/js/main.js\"></script>\n" +
                "<!-- Load navigation section -->\n" +
                "<script>\n" +
                "    $(document).ready(function () {\n" +
                "        $(\"#navigation\").load(\"navigation.html\");\n" +
                "    });\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>\n" +
                "\n";
    }

}
