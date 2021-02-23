package edu.coen390.studentgrades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import edu.coen390.studentgrades.Models.Course;
import edu.coen390.studentgrades.db.DBHelper;

/**
 * CourseDialogFragment CLASS
 * Description : Course Dialog Fragment Class
 *
 * @author Ahmed Ali
 * @author ID : 40102454
 * <p>
 * References :
 * COEN 390 - Tutorial EA & EC Videos & Source Codes
 * @author Tawfiq Jawhar
 * @author Pierre-Lucas Aubin-Fournier
 */

public class CourseDialogFragment extends DialogFragment {
    //============================ DialogFragment Data Members ============================
    private EditText courseTitleEditText;
    private EditText courseCodeEditText;
    private Button saveButton;
    private Button cancelButton;


    //============================ Inherited & UI Methods  ============================
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_course_fragment, container);

        courseTitleEditText = view.findViewById(R.id.courseTitleEditText);
        courseCodeEditText = view.findViewById(R.id.codeEditText);

        saveButton = view.findViewById(R.id.saveCourseButton);
        cancelButton = view.findViewById(R.id.cancelCourseButton);

        cancelButton.setOnClickListener(view12 -> Objects.requireNonNull(getDialog()).dismiss());


        saveButton.setOnClickListener(view1 -> {
            String courseTitle = courseTitleEditText.getText().toString();
            String courseCode = courseCodeEditText.getText().toString();

            Course course = new Course(-1, courseTitle, courseCode);


            if (!(courseTitle.equals("") || courseCode.equals(""))) {
                DBHelper dbHelper = new DBHelper(getActivity());
                dbHelper.createCourse(course);

                ((MainActivity) getActivity()).coursesView.clear();
                ((MainActivity) getActivity()).loadCourses();

                getDialog().dismiss();
            }


        });
        return view;
    }


}
