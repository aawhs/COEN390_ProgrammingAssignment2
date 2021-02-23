package edu.coen390.studentgrades.Models;

import org.jetbrains.annotations.NotNull;

/**
 * Course Model CLASS
 * Description : Course Model implementation
 *
 * @author Ahmed Ali
 */
public class Course {
    //============================ Data Members ============================
    private long courseID;
    private String courseTitle;
    private String courseCode;

    //============================ Constructor ============================

    public Course(long CourseID, String CourseTitle, String CourseCode) {
        this.courseID = CourseID;
        this.courseTitle = CourseTitle;
        this.courseCode = CourseCode;
    }

    //============================  Setters & Getters ============================
    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    //============================ Inherited Methods ============================
    @Override
    public @NotNull String toString() {
        return courseTitle + "\n" + courseCode;
    }


}
