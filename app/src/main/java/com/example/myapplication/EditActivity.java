package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

   EditText edtDate;
    EditText edtTime;
    EditText edtCapacity;
    EditText edtDuration;
    EditText edtPrice;
    EditText edtDescription;
    private String courseId;
    DatabaseHelper dbHelper;
    RadioGroup radioGroupClassType;
    Button btnUpdate;
    RadioButton radioFlow, radioAerial, radioFamily; // Assuming these are your radio buttons for the types
    String selectedType;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        dbHelper = new DatabaseHelper(this);
        // Get the course ID passed from the previous activity
        courseId = getIntent().getStringExtra("courseId");
        selectedType =  getIntent().getStringExtra("type");
        Log.d("EditActivity", "Course ID: " + courseId);
        // Initialize your EditText fields
        radioGroupClassType=findViewById(R.id.radioGroupClassType);
        edtDate = findViewById(R.id.edtDate);
        edtTime = findViewById(R.id.edtTime);
        edtCapacity = findViewById(R.id.edtCapacity);
        edtDuration = findViewById(R.id.edtDuration);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        btnUpdate = findViewById(R.id.btnUpdate);
        radioFlow = findViewById(R.id.radioFlow);
        radioAerial = findViewById(R.id.radioAerial);
        radioFamily = findViewById(R.id.radioFamily);
      //
         dbHelper = new DatabaseHelper(this);
        // Load the course details into the EditText fields using courseId
        // (You should implement this part based on your database setup)

       loadCourseDetails(courseId);

btnUpdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String date = edtDate.getText().toString();
        String time = edtTime.getText().toString();
        String capacityStr = edtCapacity.getText().toString();
        String durationStr = edtDuration.getText().toString();
        String priceStr = edtPrice.getText().toString();

        String description = edtDescription.getText().toString();

        int timeI = Integer.parseInt(time);
        int CapI = Integer.parseInt(capacityStr);
        int DuraI = Integer.parseInt(durationStr);
        int PriceI = Integer.parseInt(priceStr);
        int type = radioGroupClassType.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(type);

        String selectedT = selectedRadioButton.getText().toString();
        boolean isUpdated = dbHelper.updateData(courseId, date, time, CapI, DuraI, PriceI ,selectedT, description);
        if (isUpdated) {
            Toast.makeText( EditActivity.this,"Class updated successfully", Toast.LENGTH_SHORT).show();

            finish();
        } else {
            Toast.makeText(EditActivity.this, "Failed to update course", Toast.LENGTH_SHORT).show();
        }
    }
});
    }

    @SuppressLint("Range")
    private void loadCourseDetails(String courseId) {
//

        Cursor cursor = dbHelper.getClassById(courseId);
        if (cursor != null && cursor.moveToFirst()) {
            edtDate.setText(cursor.getString(cursor.getColumnIndex("Date")));
            edtTime.setText(cursor.getString(cursor.getColumnIndex("Time")));
            edtCapacity.setText(cursor.getString(cursor.getColumnIndex("Capacity")));
            edtDuration.setText(cursor.getString(cursor.getColumnIndex("Duration")));
            edtPrice.setText(cursor.getString(cursor.getColumnIndex("Price")));
            edtDescription.setText(cursor.getString(cursor.getColumnIndex("Description")));
            if (selectedType != null) {
                switch (selectedType) {
                    case "Flow Yoga":
                        radioFlow.setChecked(true);
                        break;
                    case "Aerial Yoga":
                        radioAerial.setChecked(true);
                        break;
                    case "Family Yoga":
                        radioFamily.setChecked(true);
                        break;
                    default:
                        break;
                }

            }
            cursor.close();
        }
        else {
            Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
        }
    }





}
