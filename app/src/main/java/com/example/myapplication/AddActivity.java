package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

   Button btnBack,btnAddClass;
   TextView textLbDate;
   EditText edtDate,edtTime,edtCapacity,edtDuration,edtPrice,edtType,edtDes;

   RadioGroup rdoGroup;
   Spinner daySpinner;
    DatabaseHelper dbHelper;
    @SuppressLint("SQLiteString")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        //button
        btnBack = findViewById(R.id.btnBack);
        btnAddClass=findViewById(R.id.btnAddClass);
        //

        textLbDate = findViewById(R.id.textLbDate);
        //
        daySpinner = findViewById(R.id.daySpinner);
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daysOfWeek);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
        //

        String selectedDay = daySpinner.getSelectedItem().toString();
        edtDate = findViewById(R.id.edtDate);
        edtTime=findViewById(R.id.edtTime);
        edtCapacity=findViewById(R.id.edtCapacity);
        edtDuration=findViewById(R.id.edtDuration);
        edtPrice=findViewById(R.id.edtPrice);
        edtDes=findViewById(R.id.edtDescription);
        // radio
        rdoGroup=findViewById(R.id.rdoGroup);
        ///
        dbHelper = new DatabaseHelper(this);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDay = parent.getItemAtPosition(position).toString();
                edtDate.setText(selectedDay);
                // Perform action based on the selected day
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btnAddClass.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String selectedDay = daySpinner.getSelectedItem().toString();

            String time = edtTime.getText().toString().trim();
            String capacity = edtCapacity.getText().toString().trim();
            String duration = edtDuration.getText().toString().trim();
            String price = edtPrice.getText().toString().trim();
            String description = edtDes.getText().toString().trim();
            int type = rdoGroup.getCheckedRadioButtonId();
            if (selectedDay.isEmpty()) {
                edtDate.setError("Please Choose Day");
            } else if (time.isEmpty()) {
                edtTime.setError("Please enter time of course");
            } else if (capacity.isEmpty()) {
                edtCapacity.setError("Please enter capacity");
            } else if (duration.isEmpty()) {
                edtDuration.setError("Please enter duration");
            } else if (price.isEmpty()) {
                edtPrice.setError("Please enter the price");
            } else if (type == -1) {

                Toast.makeText(AddActivity.this, "Please select a type of yoga class", Toast.LENGTH_SHORT).show();
            } else {
                // Lấy loại lớp học được chọn
                RadioButton selectedRadioButton = findViewById(type);
                String selectedType = selectedRadioButton.getText().toString();
                // chuyen sang string

               int capacityI = Integer.parseInt(capacity);
                int durationI = Integer.parseInt(duration);
                int priceI = Integer.parseInt(price);

                boolean isInserted = dbHelper.insertYogaClass(-1,





                        selectedDay, time, capacityI, durationI, priceI,selectedType,description);
                if (isInserted) {
                    Toast.makeText(AddActivity.this, "Class added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddActivity.this, "Failed to add class", Toast.LENGTH_SHORT).show();
                }
//
            }
        }
    });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 thoat child
                 finish();
            }
        });






    }
}