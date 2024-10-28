package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class ClassAdapter extends ArrayAdapter<ClassInstance> {

    public ClassAdapter(Context context, ArrayList<ClassInstance> classes) {
        super(context, 0, classes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        ClassInstance classInstance = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.class_item_layout, parent, false);
        }

        // Lookup view for data population
        TextView tvClassDate = convertView.findViewById(R.id.tvClassDate);
        TextView tvClassTime = convertView.findViewById(R.id.tvClassTime);

        // Populate the data into the template view using the data object
        tvClassDate.setText("Date: " + classInstance.getDate());
        tvClassTime.setText("Time: " + classInstance.getTime());

        // Return the completed view to render on screen
        return convertView;
    }
}
