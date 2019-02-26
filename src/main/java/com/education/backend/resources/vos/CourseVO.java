package com.education.backend.resources.vos;

public class CourseVO {
    private String courseId;
    private String courseName;
    private String teacher;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String description;

    public CourseVO() {}

    public CourseVO(String courseId, String courseName, String teacher, String startDate, String endDate, String startTime, String endTime, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacher = teacher;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public CourseVO setCourseId(String courseId) {
        this.courseId = courseId;
        return this;
    }

    public CourseVO setCourseName(String courseName) {
        this.courseName = courseName;
        return this;
    }

    public CourseVO setTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }

    public CourseVO setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public CourseVO setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public CourseVO setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public CourseVO setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public CourseVO setDescription(String description) {
        this.description = description;
        return this;
    }
}