package edu.coen390.studentgrades.Models;

public class Assignment {
    private long assID;
    private long courseID;
    private String assignmentTitle;
    private double grade;

    /*************** Constructor ***************/
    public Assignment(long assID, long courseID, String assignmentTitle, double grade) {
        this.assID = assID;
        this.courseID = courseID;
        this.assignmentTitle = assignmentTitle;
        this.grade = grade;
    }
    /*************** Setters ***************/
    public void setAssID(long assID) {
        this.assID = assID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
    /*************** Getters ***************/
    public long getAssID() {
        return assID;
    }

    public long getCourseID() {
        return courseID;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public double getGrade() {
        return grade;
    }
}
