package com.example.myapplication;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity {

    ListView courseListView;
    DatabaseHelper dbHelper;
    ArrayList<Course> courseList;
    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        courseListView = findViewById(R.id.courseListView);
        dbHelper = new DatabaseHelper(this);
        courseList = new ArrayList<>();

        loadCoursesFromDatabase();

        courseAdapter = new CourseAdapter(this, courseList);
        courseListView.setAdapter(courseAdapter);
    }

    private void loadCoursesFromDatabase() {
        Cursor cursor = dbHelper.getAllCourses();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                @SuppressLint("Range") String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
                @SuppressLint("Range") String comments = cursor.getString(cursor.getColumnIndex("comments"));

                // Create Course object and add to list
                courseList.add(new Course(id,date, teacher, comments));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}

