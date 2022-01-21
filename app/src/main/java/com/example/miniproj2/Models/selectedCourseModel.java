package com.example.miniproj2.Models;

public class selectedCourseModel
{
    String enrolledCourseName;
    String enrolledCourseDescription;
    int enrolledCourseImage;
    public selectedCourseModel(String enrolledCourseName, String enrolledCourseDescription, int enrolledCourseImage) {
        this.enrolledCourseName = enrolledCourseName;
        this.enrolledCourseDescription = enrolledCourseDescription;
        this.enrolledCourseImage = enrolledCourseImage;
    }

    public String getEnrolledCourseName() {
        return enrolledCourseName;
    }

    public void setEnrolledCourseName(String enrolledCourseName) {
        this.enrolledCourseName = enrolledCourseName;
    }

    public String getEnrolledCourseDescription() {
        return enrolledCourseDescription;
    }

    public void setEnrolledCourseDescription(String enrolledCourseDescription) {
        this.enrolledCourseDescription = enrolledCourseDescription;
    }

    public int getEnrolledCourseImage() {
        return enrolledCourseImage;
    }

    public void setEnrolledCourseImage(int enrolledCourseImage) {
        this.enrolledCourseImage = enrolledCourseImage;
    }
}
