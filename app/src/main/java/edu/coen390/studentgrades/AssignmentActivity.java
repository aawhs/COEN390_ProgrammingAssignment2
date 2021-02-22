package edu.coen390.studentgrades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.coen390.studentgrades.Models.Assignment;
import edu.coen390.studentgrades.Models.Course;
import edu.coen390.studentgrades.db.DBHelper;

public class AssignmentActivity extends AppCompatActivity {
    private final static String TAG = "AssignmentActivity";

    private DBHelper dbHelper;
    private TextView courseTextView;
    private Course course;
    private List<Course> courseList;
    private List<Assignment> assignmentList;
    private ListView assignmentListView;

    private FloatingActionButton assignmnetFloatingActionButton;
    private Button deleteCourseButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        setupUI();
    }
    void setupUI(){
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

        loadAssignments();

        deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteCourse(course.getCourseID());
                Log.d(TAG,"Course Deleted Successfully");
                Intent intent = new Intent(AssignmentActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    void loadAssignments(){
        dbHelper = new DBHelper(this);
        assignmentList = dbHelper.getCourseAssignments(course.getCourseID());

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, assignmentList);
        assignmentListView.setAdapter(adapter);
    }



}