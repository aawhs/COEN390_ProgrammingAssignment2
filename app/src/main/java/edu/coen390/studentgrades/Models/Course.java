package edu.coen390.studentgrades.Models;

public class Course {
    /*************** Class Data Members ***************/
    private long courseID;
    private String courseTitle;
    private String courseCode;

    /*************** Constructor ***************/

    public Course(long CourseID, String CourseTitle, String CourseCode) {
        this.courseID = CourseID;
        this.courseTitle = CourseTitle;
        this.courseCode = CourseCode;
    }

    /*************** Setters ***************/

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }


    /*************** Getters ***************/
    public long getCourseID() {
        return courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    @Override
    public String toString() {
        return courseTitle + "\n" + courseCode;
    }


}
