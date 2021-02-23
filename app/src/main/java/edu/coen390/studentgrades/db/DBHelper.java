package edu.coen390.studentgrades.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.coen390.studentgrades.Models.Assignment;
import edu.coen390.studentgrades.Models.Course;

/**
 * DBHelper CONTROLLER CLASS
 * Description : DB & Table Creation methods + CRUD Methods + Controller Methods
 *
 * @author Ahmed Ali
 * @author ID : 40102454
 *
 * <p>
 * Reference for CRUD Methods:
 * http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * <p>
 * References :
 * COEN 390 - Tutorial EA & EC Videos & Source Codes
 * @author Tawfiq Jawhar
 * @author Pierre-Lucas Aubin-Fournier
 */
@SuppressWarnings("ALL")
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = Config.DATABASE_NAME;
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_COURSE_TABLE =
            "CREATE TABLE " + Config.TABLE_NAME_COURSE + " ("
                    + Config.COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Config.COLUMN_COURSE_TITLE + " TEXT NOT NULL, "
                    + Config.COLUMN_COURSE_CODE + " TEXT NOT NULL"
                    + ")";
    private static final String CREATE_ASSIGNMENT_TABLE =
            "CREATE TABLE " + Config.TABLE_NAME_ASSIGNMENT + " ("
                    + Config.COLUMN_ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Config.COLUMN_COURSE_ID + " TEXT NOT NULL, "
                    + Config.COLUMN_ASSIGNMENT_TITLE + " TEXT NOT NULL, "
                    + Config.COLUMN_ASSIGNMENT_GRADE + " TEXT NOT NULL"
                    + ")";
    private final Context context;


    //============================ Constructor ============================
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    //============================ CONTROLLER IMPLEMENTATIONS ============================
    @Override
    public void onCreate(SQLiteDatabase db) {
        setCreateCourseTable(db);
        setCreateAssignmentTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //============================TABLE CREATIONS ============================
    private void setCreateCourseTable(SQLiteDatabase db) {
        Log.d(TAG, "Table creation Query: " + CREATE_COURSE_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
        Log.d(TAG, "Course Table created Successfully");
    }

    private void setCreateAssignmentTable(SQLiteDatabase db) {
        Log.d(TAG, "Table creation Query: " + CREATE_ASSIGNMENT_TABLE);
        db.execSQL(CREATE_ASSIGNMENT_TABLE);
        Log.d(TAG, "Assignment Table created Successfully");
    }

    //============================ COURSE TABLE CRUD METHODS ============================

    /**
     * CREATE COURSE
     *
     * @param course
     * @return
     */
    public long createCourse(Course course) {
        long id = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues courseContentValues = new ContentValues();
        courseContentValues.put(Config.COLUMN_COURSE_TITLE, course.getCourseTitle());
        courseContentValues.put(Config.COLUMN_COURSE_CODE, course.getCourseCode());

        try {
            id = db.insertOrThrow(Config.TABLE_NAME_COURSE,
                    null, courseContentValues);
        } catch (SQLException e) {
            Log.d(TAG, "Exception: " + e.getMessage());
            Toast.makeText(context,
                    "Operation Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
        return id;
    }

    /**
     * READ COURSE
     *
     * @param pos
     * @return
     */
    public Course getCourse(int pos) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(Config.TABLE_NAME_COURSE, null, null,
                    null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToPosition(pos)) {
                    List<edu.coen390.studentgrades.Models.Course> courseList = new ArrayList<>();

                    int id = cursor.getInt(cursor.getColumnIndex(
                            Config.COLUMN_COURSE_ID));
                    String title = cursor.getString(cursor.getColumnIndex(
                            Config.COLUMN_COURSE_TITLE));
                    String code = cursor.getString(cursor.getColumnIndex(
                            Config.COLUMN_COURSE_CODE));
                    return new edu.coen390.studentgrades.Models.Course(id, title, code);
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }
        return null;
    }

    /**
     * READ ALL COURSES
     *
     * @return
     */
    public List<Course> getAllCourses() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(Config.TABLE_NAME_COURSE, null, null,
                    null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    List<Course> courseList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(
                                Config.COLUMN_COURSE_ID));
                        String title = cursor.getString(cursor.getColumnIndex(
                                Config.COLUMN_COURSE_TITLE));
                        String code = cursor.getString(cursor.getColumnIndex(
                                Config.COLUMN_COURSE_CODE));
                        courseList.add(new Course(id, title, code));
                    } while (cursor.moveToNext());

                    return courseList;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }


        return Collections.emptyList();
    }

    /**
     * UPDATE COURSE
     *
     * @param course
     * @return
     */
    public int updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Config.COLUMN_COURSE_TITLE, course.getCourseTitle());
        values.put(Config.COLUMN_COURSE_CODE, course.getCourseCode());

        // updating row
        return db.update(Config.TABLE_NAME_COURSE, values,
                Config.COLUMN_COURSE_ID + " = ?",
                new String[]{String.valueOf(course.getCourseID())});
    }

    /**
     * DELETE COURSE
     *
     * @param course_id
     */
    public void deleteCourse(long course_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteAssignments(course_id);
        db.delete(Config.TABLE_NAME_COURSE, Config.COLUMN_COURSE_ID + " = ?",
                new String[]{String.valueOf(course_id)});
    }


    //============================ ASSIGNMENT TABLE CRUD METHODS ============================

    /**
     * CREATE ASSIGNMENT
     *
     * @param assignment
     * @return
     */
    public long createAssignment(Assignment assignment) {
        long id = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues assignmentContentValues = new ContentValues();
        assignmentContentValues.put(Config.COLUMN_ASSIGNMENT_TITLE,
                assignment.getAssignmentTitle());
        assignmentContentValues.put(Config.COLUMN_COURSE_ID,
                assignment.getCourseID());
        assignmentContentValues.put(Config.COLUMN_ASSIGNMENT_GRADE,
                assignment.getGrade());

        try {
            id = db.insert(Config.TABLE_NAME_ASSIGNMENT,
                    null, assignmentContentValues);
        } catch (SQLException e) {
            Log.d(TAG, "Exception: " + e.getMessage());
            Toast.makeText(context,
                    "Operation Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
        return id;
    }

    /**
     * READ ASSIGNMENT
     *
     * @param pos
     * @return
     */
    public Assignment getAssignment(int pos) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(Config.TABLE_NAME_ASSIGNMENT, null, null,
                    null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToPosition(pos)) {

                    long a_id = cursor.getInt(cursor.getColumnIndex(
                            Config.COLUMN_ASSIGNMENT_ID));
                    long c_id = cursor.getInt(cursor.getColumnIndex(
                            Config.COLUMN_COURSE_ID));
                    String title = cursor.getString(cursor.getColumnIndex(
                            Config.COLUMN_ASSIGNMENT_TITLE));
                    double grade = cursor.getDouble(cursor.getColumnIndex(
                            Config.COLUMN_ASSIGNMENT_GRADE));
                    return new edu.coen390.studentgrades.Models.Assignment(a_id, c_id, title, grade);
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }
        return null;
    }

    /**
     * READ ALL ASSIGNMENTS
     */
    public List<Assignment> getAllAssignments() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(Config.TABLE_NAME_ASSIGNMENT, null, null,
                    null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    List<Assignment> assignmentList = new ArrayList<>();
                    do {
                        long a_id = cursor.getInt(cursor.getColumnIndex(
                                Config.COLUMN_ASSIGNMENT_ID));
                        long c_id = cursor.getInt(cursor.getColumnIndex(
                                Config.COLUMN_COURSE_ID));
                        String title = cursor.getString(cursor.getColumnIndex(
                                Config.COLUMN_ASSIGNMENT_TITLE));
                        double grade = cursor.getDouble(cursor.getColumnIndex(
                                Config.COLUMN_ASSIGNMENT_GRADE));
                        assignmentList.add(new Assignment(a_id, c_id, title, grade));
                    } while (cursor.moveToNext());

                    return assignmentList;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }


        return Collections.emptyList();
    }

    /**
     * READ ALL ASSIGNMENTS OF A COURSE
     *
     * @param c_id
     * @return
     */
    public List<Assignment> getCourseAssignments(long c_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Assignment> assignmentList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(Config.TABLE_NAME_ASSIGNMENT, null, null,
                    null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    do {
                        if (cursor.getInt(cursor.getColumnIndex(Config.COLUMN_COURSE_ID)) == c_id) {
                            long a_id = cursor.getInt(cursor.getColumnIndex(
                                    Config.COLUMN_ASSIGNMENT_ID));
                            long course_id = cursor.getInt(cursor.getColumnIndex(
                                    Config.COLUMN_COURSE_ID));
                            String title = cursor.getString(cursor.getColumnIndex(
                                    Config.COLUMN_ASSIGNMENT_TITLE));
                            double grade = cursor.getDouble(cursor.getColumnIndex(
                                    Config.COLUMN_ASSIGNMENT_GRADE));
                            assignmentList.add(new Assignment(a_id, course_id, title, grade));
                        }

                    } while (cursor.moveToNext());

                    return assignmentList;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }


        return Collections.emptyList();
    }


    /**
     * UPDATE ASSIGNMENT
     *
     * @param assignment
     * @return
     */
    public int updateAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Config.COLUMN_ASSIGNMENT_TITLE, assignment.getAssignmentTitle());
        values.put(Config.COLUMN_ASSIGNMENT_GRADE, assignment.getGrade());

        // updating row
        return db.update(Config.TABLE_NAME_ASSIGNMENT, values,
                Config.COLUMN_ASSIGNMENT_ID + " = ?",
                new String[]{String.valueOf(assignment.getCourseID())});
    }

    /**
     * DELETE ASSIGNMENT
     *
     * @param c_id
     */
    public void deleteAssignments(long c_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Config.TABLE_NAME_ASSIGNMENT,
                Config.COLUMN_COURSE_ID + " = ?",
                new String[]{String.valueOf(c_id)});
    }


    //============================ CONTROLLER METHODS ============================

    /**
     * GET ASSIGNMENT AVERAGE
     *
     * @param course_id
     * @return
     */
    public double getAssignmentsAvg(long course_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        double sum = 0.0;
        double count = 0.0;


        List<Assignment> assignmentList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(Config.TABLE_NAME_ASSIGNMENT, null, null,
                    null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    do {
                        if (cursor.getInt(cursor.getColumnIndex(
                                Config.COLUMN_COURSE_ID)) == course_id) {
                            long a_id = cursor.getInt(cursor.getColumnIndex(
                                    Config.COLUMN_ASSIGNMENT_ID));
                            long c_id = cursor.getInt(cursor.getColumnIndex(
                                    Config.COLUMN_COURSE_ID));
                            String title = cursor.getString(cursor.getColumnIndex(
                                    Config.COLUMN_ASSIGNMENT_TITLE));
                            double grade = cursor.getDouble(cursor.getColumnIndex(
                                    Config.COLUMN_ASSIGNMENT_GRADE));
                            assignmentList.add(new Assignment(a_id, c_id, title, grade));
                        }

                    } while (cursor.moveToNext());

                    for (int i = 0; i < assignmentList.size(); i++) {
                        sum += assignmentList.get(i).getGrade();
                        count++;
                    }

                    return sum / count;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }

        return 0.0;
    }


    /**
     * ClOSE DB
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
