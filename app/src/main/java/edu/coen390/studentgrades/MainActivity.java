package edu.coen390.studentgrades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.coen390.studentgrades.Models.Assignment;
import edu.coen390.studentgrades.Models.Course;
import edu.coen390.studentgrades.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private DBHelper dbHelper;
    private List<Course> courses;
    private List<Assignment>assignments;
    ArrayList<String> coursesView = new ArrayList<>();
    private TextView totalAverageTextView;
    private ListView coursesListView;
    private FloatingActionButton courseFloatingActionButton;
    private double sumOfAverage;
    private double totalAverage;
    private String tAvgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        loadCourses();

    }

    protected void setupUI(){
        coursesListView = findViewById(R.id.courseListView);
        totalAverageTextView = findViewById(R.id.totalAverageTextView);
        courseFloatingActionButton = findViewById(R.id.addCourseFloatingActionButton);

        courseFloatingActionButton.setOnClickListener(view -> {
            CourseDialogFragment dialog = new CourseDialogFragment();
            dialog.show(getSupportFragmentManager(), "InsertCourse");
        });

        coursesListView.setClickable(true);
        coursesListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, AssignmentActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });
    }



    void loadCourses(){

        dbHelper = new DBHelper(this);
        courses = dbHelper.getAllCourses();


        double avg=0;
        for(int i = 0; i < courses.size(); i++) {
            long id = courses.get(i).getCourseID();

            if(!coursesView.contains(courses.get(i).getCourseCode())){
                avg = dbHelper.getAssignmentsAvg(id);

                if(avg > 0){
                    coursesView.add(courses.get(i).toString() + "\nAssignments Average = " + avg);
                    sumOfAverage += avg;
                }else{
                    coursesView.add(courses.get(i).toString() + "\nAssignments Average = 0.0" );
                }

            }

        }

        if(courses.size() != 0){
            totalAverage= (sumOfAverage/(courses.size()));
            tAvgText = "Average of All Assignments = "+ totalAverage;
        }else{
            tAvgText = "Average of All Assignments = N/A";
        }


        totalAverageTextView.setText(tAvgText);




        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, coursesView);
        coursesListView.setAdapter(adapter);
    }





}