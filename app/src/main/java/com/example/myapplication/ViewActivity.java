package com.example.myapplication;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> classList;
   // ArrayAdapter<String> myAdapter;
    DatabaseHelper dbHelper;
     CustomAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        lv = findViewById(R.id.lv);
        dbHelper = new DatabaseHelper(this);
        classList = new ArrayList<>();
        myAdapter = new CustomAdapter(this, classList, dbHelper);


        // Thiết lập Adapter và ListView
      //  myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classList);
        lv.setAdapter(myAdapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        // Reload the data every time the activity resumes
        loadDataFromDatabase();
    }
    private void loadDataFromDatabase() {
        classList.clear();  // Clear the existing data

        try {
            Cursor cursor = dbHelper.getAllYogaClasses();  // Lấy tất cả lớp yoga
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndexOrThrow("Date"));
                    @SuppressLint("Range") int time = cursor.getInt(cursor.getColumnIndexOrThrow("Time"));
                    @SuppressLint("Range") int capacity = cursor.getInt(cursor.getColumnIndexOrThrow("Capacity"));
                    @SuppressLint("Range") int duration = cursor.getInt(cursor.getColumnIndexOrThrow("Duration"));
                    @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndexOrThrow("Price"));
                    @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndexOrThrow("Type"));
                    @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));

                    String classDetails =id+ " - Date: " + date + ", Time: " + time + ", Capacity: " + capacity +
                            ", Duration: " + duration + ", Price: " + price + ", Type: "+type+", Description: " + description;

                    classList.add(classDetails);
                }
                cursor.close();  // Đóng Cursor sau khi sử dụng
            }
        } catch (Exception e) {
            Log.e("ViewActivity", "Error while fetching data: " + e.getMessage());
        }

        myAdapter.notifyDataSetChanged();  // Notify the adapter to update the ListView
    }
}