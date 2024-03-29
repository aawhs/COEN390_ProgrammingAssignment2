package edu.coen390.studentgrades.db;

/**
 * DBHelper CONFIG CLASS
 * Description : Configuration to be used with DBHelper class
 *
 * @author Ahmed Ali
 * @author ID : 40102454
 *
 */

public class Config {
    public static final String DATABASE_NAME = "student-db";

    public static final String TABLE_NAME_COURSE = "course";
    public static final String COLUMN_COURSE_ID = "_courseID";
    public static final String COLUMN_COURSE_TITLE = "name";
    public static final String COLUMN_COURSE_CODE = "code";

    public static final String TABLE_NAME_ASSIGNMENT = "assignment";
    public static final String COLUMN_ASSIGNMENT_ID = "_assID";
    public static final String COLUMN_ASSIGNMENT_TITLE = "name";
    public static final String COLUMN_ASSIGNMENT_GRADE = "grade";
}
