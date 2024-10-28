package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class AddCourseActivity extends AppCompatActivity{
     CalendarView calendarView;
     EditText edtTeacherName, edtComments,edtCourseDate;
     Button btnSaveCourse;
     DatabaseHelper dbHelper;
    String selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        calendarView = findViewById(R.id.calendarView);
        edtTeacherName = findViewById(R.id.edtTeacherName);
        edtComments = findViewById(R.id.edtComments);
        edtCourseDate = findViewById(R.id.edtCourseDate);
        btnSaveCourse = findViewById(R.id.btnSaveCourse);
        dbHelper = new DatabaseHelper(this);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Month is 0-indexed, so add 1 to it
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                edtCourseDate.setText(selectedDate);
            }
        });
        btnSaveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String teacherName = edtTeacherName.getText().toString().trim();
                String comments = edtComments.getText().toString().trim();
                String date =edtCourseDate.getText().toString().trim();
                // Validate inputs
                if (teacherName.isEmpty()) {
                    edtTeacherName.setError("Please enter teacher name");
                    return;
                }
                if (selectedDate == null) {
                    Toast.makeText(AddCourseActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Insert the course details into the database
                boolean isInserted = dbHelper.insertCourse(date, teacherName, comments);
                if (isInserted) {
                    Toast.makeText(AddCourseActivity.this, "Course added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                } else {
                    Toast.makeText(AddCourseActivity.this, "Failed to add course", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

