package edu.coen390.studentgrades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.coen390.studentgrades.Models.Assignment;
import edu.coen390.studentgrades.Models.Course;
import edu.coen390.studentgrades.db.DBHelper;
/**
 * Assignment Activity CLASS
 * Description : Assignment Activity Class
 * @author Ahmed Ali
 * @author ID : 40102454
 *
 * References :
 * COEN 390 - Tutorial EA & EC Videos & Source Codes
 * @author Tawfiq Jawhar
 * @author Pierre-Lucas Aubin-Fournier
 */
public class AssignmentActivity extends AppCompatActivity {
    //============================ Activity Data Members ============================
    //LOG TAG
    private final static String TAG = "AssignmentActivity";

    //DATABASE Data Members
    private DBHelper dbHelper;
    private Course course;
    private List<Course> courseList;
    private List<Assignment> assignmentList;

    //UI Data Members
    private TextView courseTextView;
    private ListView assignmentListView;
    private FloatingActionButton assignmnetFloatingActionButton;
    private Button deleteCourseButton;


    //============================ Inherited & UI Methods  ============================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        setupUI();
    }

    void setupUI(){
        //UI Initializations
        courseTextView = findViewById(R.id.courseTextView);
        assignmentListView = findViewById(R.id.assignmentListView);
        assignmnetFloatingActionButton=findViewById(R.id.addAssignmentFloatingActionButton);
        deleteCourseButton=findViewById(R.id.deleteCourseButton);


        dbHelper = new DBHelper(this);
        courseList = dbHelper.getAllCourses();

        Intent intent = getIntent();
        course = courseList.get(intent.getIntExtra("position",0));


        course = dbHelper.getCourse(intent.getIntExtra("position",0));
        String courseNameDisplay = course.getCourseTitle() + " - " + course.getCourseCode();

        courseTextView.setText(courseNameDisplay);

        assignmnetFloatingActionButton.setOnClickListener(view -> {
            AssignmentDialogFragment dialog = new AssignmentDialogFragment(course);
            dialog.show(getSupportFragmentManager(), "Insert Assignment");
        });


        deleteCourseButton.setOnClickListener(v -> {
            dbHelper.deleteCourse(course.getCourseID());
            Log.d(TAG,"Course Deleted Successfully");
            Intent intent1 = new Intent(AssignmentActivity.this,MainActivity.class);
            startActivity(intent1);
        });

        //List View Content
        loadAssignments();
    }

    //============================ Activity Methods  ============================
    void loadAssignments(){
        dbHelper = new DBHelper(this);
        assignmentList = dbHelper.getCourseAssignments(course.getCourseID());

        ArrayAdapter<Assignment> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, assignmentList);
        assignmentListView.setAdapter(adapter);
    }



}