package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class    CourseAdapter extends BaseAdapter {
    private Context context;
    DatabaseHelper dbHelper;
    private ArrayList<Course> courseList;
    private LayoutInflater inflater;

    public CourseAdapter(Context context, ArrayList<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
        dbHelper = new DatabaseHelper(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.course_item_layout, parent, false);
        }

        // Get current course
        Course course = courseList.get(position);

        // Populate the course details
        TextView tvCourseDate = convertView.findViewById(R.id.tvCourseDate);
        TextView tvTeacher = convertView.findViewById(R.id.tvTeacher);
        TextView tvComments = convertView.findViewById(R.id.tvComments);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnViewDetail = convertView.findViewById(R.id.btnViewDetail);
        Button btnDeleteCourse = convertView.findViewById(R.id.btnDeleteCourse);

        tvCourseDate.setText(course.getDate());
        tvTeacher.setText(course.getTeacher());
        tvComments.setText(course.getComments());


        btnViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle view detail action
                Intent intent = new Intent(context, CourseDetailActivity.class);
                intent.putExtra("course_id", course.getId());
                context.startActivity(intent);
            }
        });
        btnEdit.setOnClickListener(v -> {

            Intent addIntent = new Intent(context, AddClassToCourseActivity.class);
            addIntent.putExtra("COURSE_ID", course.getId());
            addIntent.putExtra("COURSE_DATE", course.getDate());
            context.startActivity(addIntent);
        });
        btnDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the course from the database
                dbHelper.deleteCourse(course.getId());

                // Remove the course from the adapter and update the list view
                courseList.remove(course);
                notifyDataSetChanged();

                // Show a confirmation toast
                Toast.makeText(context, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

}
