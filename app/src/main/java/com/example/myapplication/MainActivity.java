package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

//import com.google.firebase.FirebaseApp;
public class MainActivity extends AppCompatActivity {
// khai bao cac bien giao dien
DatabaseHelper dbHelper;
Button btnAdd,btnView,btnToAddCourse,btnViewCourse,btnSearchClass;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        // anh xa id cho cac bien giao dien ( ket noi cac bien den cac bie no giao dien )
    btnAdd = findViewById(R.id.btnAdd);
    btnView = findViewById(R.id.btnView);
    btnToAddCourse = findViewById(R.id.btnToAddCourse);
    btnViewCourse = findViewById(R.id.btnViewCourse);
    btnSearchClass = findViewById(R.id.btnSearchClass);
dbHelper = new DatabaseHelper(this);
        // xu li tuong tac nguoi dung




    //    FirebaseDatabase database = FirebaseDatabase.getInstance();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // khai bao intent
                Intent myintent = new Intent(MainActivity.this, AddActivity.class);
                // khoi dong
               startActivity(myintent);
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity.this, ViewActivity.class);
                // khoi dong
                startActivity(myintent);
            }
        });
        btnToAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity.this, AddCourseActivity.class);
                // khoi dong
                startActivity(myintent);
            }
        });
btnViewCourse.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent myintent = new Intent(MainActivity.this, CourseListActivity.class);
        // khoi dong
        startActivity(myintent);
    }
});
btnSearchClass.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent myintent = new Intent(MainActivity.this, SearchClassActivity.class);
        // khoi dong
        startActivity(myintent);
    }
});





    // Check for network connectivity



    }
}