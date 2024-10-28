package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SearchClassActivity extends AppCompatActivity {
    private EditText editSearchTeacher;
    private Button btnSearch;
    private ListView listViewSearchResults;
    private ClassInstanceAdapter adapter;
    private ArrayList<ClassInstance> searchResults;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_class);

        editSearchTeacher = findViewById(R.id.editSearchTeacher);
        btnSearch = findViewById(R.id.btnSearch);
        listViewSearchResults = findViewById(R.id.listViewSearchResults);
        searchResults = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        // Initialize adapter for displaying search results
        adapter = new ClassInstanceAdapter(this, searchResults, -1,true); // -1 for no course ID during search
        listViewSearchResults.setAdapter(adapter);

        // Search button click listener
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacherName = editSearchTeacher.getText().toString().trim();
                if (!TextUtils.isEmpty(teacherName)) {
                    // Call method to search for classes by teacher name
                    searchClassesByTeacher(teacherName);
                } else {
                    Toast.makeText(SearchClassActivity.this, "Please enter a teacher name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void searchClassesByTeacher(String teacherName) {
        // Clear previous results
        searchResults.clear();

        // Get matching classes from the database
        searchResults.addAll(dbHelper.getClassesByTeacher(teacherName));
if (searchResults.size()<=0)
{
    Toast.makeText(SearchClassActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
}
        // Notify the adapter of the changes
        adapter.notifyDataSetChanged();
    }
}
