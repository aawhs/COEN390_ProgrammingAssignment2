package edu.coen390.studentgrades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.coen390.studentgrades.Models.Assignment;
import edu.coen390.studentgrades.Models.Course;
import edu.coen390.studentgrades.db.DBHelper;

/**
 * Main Activity CLASS
 * Description : Main Activity Class
 *
 * @author Ahmed Ali
 * @author ID : 40102454
 * <p>
 * References :
 * COEN 390 - Tutorial EA & EC Videos & Source Codes
 * @author Tawfiq Jawhar
 * @author Pierre-Lucas Aubin-Fournier
 */
public class MainActivity extends AppCompatActivity {

    //============================ Activity Data Members ============================
    //LOG TAG
    private final static String TAG = "MainActivity";
    //UI Data Members
    ArrayList<String> coursesView = new ArrayList<>();
    //DATABASE Data Members
    private DBHelper dbHelper;
    private List<Course> courses;
    private List<Assignment> assignments;
    private TextView totalAverageTextView;
    private ListView coursesListView;
    private FloatingActionButton courseFloatingActionButton;

    //Average Method Data Members
    private double sumOfAverage;
    private int count = 0;
    private double totalAverage;
    private String tAvgText;

    //============================ Inherited & UI Methods  ============================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI Instantiation & Initialization
        setupUI();
    }

    protected void setupUI() {
        //UI Initializations
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

        //List View Content
        loadCourses();
    }


    //============================ Activity Methods  ============================
    void loadCourses() {

        dbHelper = new DBHelper(this);
        courses = dbHelper.getAllCourses();

        getAverage();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, coursesView);
        coursesListView.setAdapter(adapter);
    }

    private void getAverage() {
        double avg;
        for (int i = 0; i < courses.size(); i++) {
            long id = courses.get(i).getCourseID();
            if (!coursesView.contains(courses.get(i).getCourseCode())) {
                avg = dbHelper.getAssignmentsAvg(id);
                if (avg > 0) {
                    coursesView.add(courses.get(i).toString() + "\nAssignments Average = " + avg);
                    sumOfAverage += avg;
                    count++;
                } else {
                    coursesView.add(courses.get(i).toString() + "\nAssignments Average = 0.0");
                }
            }
        }

        if (courses.size() != 0) {
            totalAverage = (sumOfAverage / count);
            tAvgText = "Average of All Assignments = " + totalAverage;
        } else {
            tAvgText = "Average of All Assignments = N/A";
        }
        totalAverageTextView.setText(tAvgText);
    }
}