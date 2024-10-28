package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassInstanceAdapter extends ArrayAdapter<ClassInstance> {
    private DatabaseHelper dbHelper;
    private int courseId;
    private boolean showDeleteButton;
    public ClassInstanceAdapter(Context context, ArrayList<ClassInstance> classInstances,int courseId,boolean showDeleteButton) {
        super(context, 0, classInstances);
        this.courseId = courseId;
        this.showDeleteButton = showDeleteButton;
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the current class instance
        ClassInstance classInstance = getItem(position);

        // Inflate view if not reused
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.class_item_layout, parent, false);
        }

        // Set class instance details
        TextView tvDate = convertView.findViewById(R.id.tvClassDate);
        TextView tvTime = convertView.findViewById(R.id.tvClassTime);
        TextView tvCapacity = convertView.findViewById(R.id.tvCapacity);
        TextView tvDuration = convertView.findViewById(R.id.tvDuration);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        TextView tvType = convertView.findViewById(R.id.tvType);
        TextView tvDescription = convertView.findViewById(R.id.tvDescription);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        Button btnAdd = convertView.findViewById(R.id.btnAdd);
        // Bind data from classInstance
        tvDate.setText("Date: " + classInstance.getDate());
        tvTime.setText("Time: " + classInstance.getTime());
        tvCapacity.setText("Capacity: " + classInstance.getCapacity());
        tvDuration.setText("Duration: " + classInstance.getDuration() + " min");
        tvPrice.setText("Price: $" + classInstance.getPrice());
        tvType.setText("Type: " + classInstance.getType());
        tvDescription.setText("Description: " + classInstance.getDescription());
        if (showDeleteButton) {
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.clearCourseIdForClass(classInstance.getId());
                    remove(classInstance);
                    notifyDataSetChanged();
                    Toast.makeText(getContext(), "Course ID removed for this class", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btnDelete.setVisibility(View.GONE); // Hide the delete button
        }
        if (!showDeleteButton) {
            btnAdd.setVisibility(View.VISIBLE);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.updateCourseIdForClass(classInstance.getId(), courseId); // Update course ID for class
                    Toast.makeText(getContext(), "Class added to the course", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btnAdd.setVisibility(View.GONE); // Hide the delete button
        }
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dbHelper.updateCourseIdForClass(classInstance.getId(), courseId); // Update course ID for class
//                Toast.makeText(getContext(), "Class added to the course", Toast.LENGTH_SHORT).show();
//            }
//        });

        return convertView;
    }
}

