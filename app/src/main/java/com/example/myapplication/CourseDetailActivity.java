package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CourseDetailActivity extends AppCompatActivity {

    private TextView tvCourseDate, tvTeacher, tvComments;
    private ListView lvClasses;
    private DatabaseHelper dbHelper;
    private int courseId;
    String courseDate;
 Button btnEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // Khởi tạo các thành phần giao diện

        tvCourseDate = findViewById(R.id.tvCourseDate);
        tvTeacher = findViewById(R.id.tvTeacher);
        tvComments = findViewById(R.id.tvComments);
        lvClasses = findViewById(R.id.lvClasses);
       // btnEdit = findViewById(R.id.btnEdit);
        dbHelper = new DatabaseHelper(this);
        courseId = getIntent().getIntExtra("COURSE_ID", -1);
        courseDate = getIntent().getStringExtra("COURSE_DATE");
        // Lấy courseId từ Intent truyền vào


        Intent intent = getIntent();
        if (intent != null) {
            courseId = intent.getIntExtra("course_id", -1);
        }

        if (courseId != -1) {
            // Lấy thông tin khóa học từ cơ sở dữ liệu và hiển thị
            Course course = dbHelper.getCourseById(courseId);
            if (course != null) {

                tvCourseDate.setText("Date: " + course.getDate());
                tvTeacher.setText("Teacher: " + course.getTeacher());
                tvComments.setText("Comments: " + course.getComments());
            }

            // Lấy danh sách các lớp học liên quan tới courseId
            ArrayList<ClassInstance> classInstances = dbHelper.getClassesByCourseId(courseId);

            // Gán danh sách lớp học vào adapter để hiển thị trong ListView
            ClassInstanceAdapter adapter = new ClassInstanceAdapter(this, classInstances,courseId,true);
            lvClasses.setAdapter(adapter);
        }


    }
}
