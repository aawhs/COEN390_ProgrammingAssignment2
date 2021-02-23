package edu.coen390.studentgrades.Models;

import org.jetbrains.annotations.NotNull;

/**
 * Assignment Model CLASS
 * Description : Assignment Model implementation
 *
 * @author Ahmed Ali
 */
public class Assignment {
    //============================ Data Members ============================
    private long assID;
    private long courseID;
    private String assignmentTitle;
    private double grade;

    //============================ Constructor ============================
    public Assignment(long assID, long courseID, String assignmentTitle, double grade) {
        this.assID = assID;
        this.courseID = courseID;
        this.assignmentTitle = assignmentTitle;
        this.grade = grade;
    }


    //============================ Setters & Getters ============================
    public long getAssID() {
        return assID;
    }

    public void setAssID(long assID) {
        this.assID = assID;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    //============================ Inherited Methods  ============================
    @Override
    public @NotNull String toString() {
        return assignmentTitle + "\n" + grade + "%";
    }
}
