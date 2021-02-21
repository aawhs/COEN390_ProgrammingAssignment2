package edu.coen390.studentgrades.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import edu.coen390.studentgrades.Models.*;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    private static final String DATABASE_NAME = Config.DATABASE_NAME;
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLES
        String CREATE_COURSE_TABLE = "CREATE TABLE" + Config.TABLE_NAME_COURSE + " ("
                + Config.COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_COURSE_TITLE + "TEXT NOT NULL, "
                + Config.COLUMN_COURSE_CODE + " TEXT NOT NULL"
                +")";

        String CREATE_ASSIGNMENT_TABLE = "CREATE TABLE" + Config.TABLE_NAME_ASSIGNMENT + " ("
                + Config.COLUMN_ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_COURSE_ID +     "TEXT NOT NULL"
                + Config.COLUMN_COURSE_TITLE +  "TEXT NOT NULL, "
                + Config.COLUMN_COURSE_CODE +   "TEXT NOT NULL"
                +")";

        Log.d(TAG, "Table creation Query: " + CREATE_COURSE_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
        Log.d(TAG, "Course Table created Successfully");

        Log.d(TAG, "Table creation Query: " + CREATE_ASSIGNMENT_TABLE);
        db.execSQL(CREATE_ASSIGNMENT_TABLE);
        Log.d(TAG, "Assignment Table created Successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertCourse(Course course){
        long id = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues courseContentValues = new ContentValues();
        courseContentValues.put(Config.COLUMN_COURSE_TITLE, course.getCourseTitle());
        courseContentValues.put(Config.COLUMN_COURSE_ID, course.getCourseID());
        courseContentValues.put(Config.COLUMN_COURSE_CODE, course.getCourseCode());

        db.insert(Config.TABLE_NAME_ASSIGNMENT,null,courseContentValues);
        return -1;
    }

    public long insertAssignment(Assignment assignment){
        long id = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues assignmentContentValues = new ContentValues();
        assignmentContentValues.put(Config.COLUMN_ASSIGNMENT_TITLE, assignment.getAssignmentTitle());
        assignmentContentValues.put(Config.COLUMN_ASSIGNMENT_ID, assignment.getAssID());
        assignmentContentValues.put(Config.COLUMN_COURSE_ID, assignment.getCourseID());
        assignmentContentValues.put(Config.COLUMN_ASSIGNMENT_GRADE, assignment.getGrade());

        db.insert(Config.TABLE_NAME_ASSIGNMENT,null,assignmentContentValues);
        return -1;
    }
}
