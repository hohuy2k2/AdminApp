package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> courseList;
    private DatabaseHelper dbHelper;

    public CustomAdapter(@NonNull Context context, ArrayList<String> list, DatabaseHelper dbHelper) {
        super(context, 0, list);
        this.context = context;
        this.courseList = list;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the data item for this position
        String courseDetails = getItem(position);
        // Split to extract course ID (assuming course ID is included)
        String[] detailsArray = courseDetails.split(", ");
        String courseId = detailsArray[0].split(" - ")[0];
        String type = detailsArray[5].split(": ")[1];

        // Lookup view for data population
        TextView txtCourseDetails = convertView.findViewById(R.id.txtCourseDetails);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        // Populate the data into the template view using the data object
        txtCourseDetails.setText(courseDetails);

        // Set up edit button
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("courseId", courseId); // Pass the course ID
                intent.putExtra("type", type);
//                ((ViewActivity) context).startActivityForResult(intent, 1);
                context.startActivity(intent);
            }
        });

        // Set up delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to delete the course from the database
                dbHelper.deleteData(courseId);
                // Optionally, update the list and notify the adapter
                courseList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
