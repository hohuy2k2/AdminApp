package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddClassToCourseActivity extends AppCompatActivity {
    private ListView listViewClasses;
    private ClassInstanceAdapter adapter;
    private ArrayList<ClassInstance> classList;
    private int courseId; // ID của course đang được chỉnh sửa
    private String courseDate; // Ngày của course

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_to_course);

        // Nhận ID và ngày của course từ Intent
        courseId = getIntent().getIntExtra("COURSE_ID", -1);
        courseDate = getIntent().getStringExtra("COURSE_DATE");

        listViewClasses = findViewById(R.id.listViewClasses);
        classList = new ArrayList<>();
        adapter = new ClassInstanceAdapter(this, classList, courseId,false); // Truyền courseId
        listViewClasses.setAdapter(adapter);

        // Lấy danh sách lớp học cùng ngày
        loadClassesWithSameDay(courseDate);
    }

    private void loadClassesWithSameDay(String day) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        classList.clear(); // Xóa danh sách hiện tại
        String dayOfWeek = getDayOfWeek(courseDate);

        // Giả sử bạn có phương thức trong DatabaseHelper để lấy lớp học theo ngày
        classList.addAll(dbHelper.getClassesByDay(dayOfWeek));
        adapter.notifyDataSetChanged();
    }
    private String getDayOfWeek(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = dateFormat.parse(dateStr);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault()); // "EEEE" for full day name
            return dayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }
}
