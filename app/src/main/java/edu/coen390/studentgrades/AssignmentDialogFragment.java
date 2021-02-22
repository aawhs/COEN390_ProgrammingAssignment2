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

import edu.coen390.studentgrades.Models.Assignment;
import edu.coen390.studentgrades.Models.Course;
import edu.coen390.studentgrades.db.DBHelper;

public class AssignmentDialogFragment extends DialogFragment {
    private EditText assignmentTitleEditText;
    private EditText gradeEditText;
    private Button saveAssButton;
    private Button cancelAssButton;
    private Course course;

    public AssignmentDialogFragment(Course course) {
        this.course = course;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_assignment_fragment, container );

        assignmentTitleEditText =  view.findViewById(R.id.assignmentTitleEditText);
        gradeEditText = view.findViewById(R.id.gradeEditText);

        saveAssButton = view.findViewById(R.id.saveAssButton);
        cancelAssButton = view.findViewById(R.id.cancelAssButton);

        cancelAssButton.setOnClickListener(view12 -> Objects.requireNonNull(getDialog()).dismiss());


        saveAssButton.setOnClickListener(view1 -> {
            String assignmentTitle = assignmentTitleEditText.getText().toString();
            String grade = gradeEditText.getText().toString();

            Assignment assignment = new Assignment(-1, course.getCourseID(), assignmentTitle, Double.parseDouble(grade));


            if(!(assignmentTitle.equals("") || grade.equals("")))
            {
                DBHelper dbHelper = new DBHelper(getActivity());
                dbHelper.createAssignment(assignment);

                ((AssignmentActivity)getActivity()).loadAssignments();

                getDialog().dismiss();
            }


        });
        return view;
    }
}
