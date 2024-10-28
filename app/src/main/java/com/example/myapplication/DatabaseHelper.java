package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "yoga_class.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_YOGA = "yoga";
    public static final String TABLE_COURSES = "courses";
    DatabaseReference CourseDb;
    DatabaseReference ClassDb;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        CourseDb = FirebaseDatabase.getInstance("https://mobile-1786-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("courses");
        ClassDb = FirebaseDatabase.getInstance("https://mobile-1786-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("classes");
    }
    public void syncDataCourseWithFirebase() {
        Cursor cursor = getAllCourses();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                @SuppressLint("Range") String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
                @SuppressLint("Range") String comments = cursor.getString(cursor.getColumnIndex("comments"));

                Course course = new Course(id, date, teacher, comments);
                CourseDb.child(String.valueOf(id)).setValue(course)
                        .addOnSuccessListener(aVoid -> Log.d("Firebase", "Course synced: " + id))
                        .addOnFailureListener(e -> Log.e("Firebase", "Failed to sync course: " + id, e));;
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
    public void syncClassWithFirebase() {
        Cursor cursor = getAllYogaClasses();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int courseId = cursor.getInt(cursor.getColumnIndex("course_id"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("Date"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("Time"));
                @SuppressLint("Range") int capacity = cursor.getInt(cursor.getColumnIndex("Capacity"));
                @SuppressLint("Range") int duration = cursor.getInt(cursor.getColumnIndex("Duration"));
                @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex("Price"));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex("Type"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("Description"));

                ClassInstance classInstance = new ClassInstance(id, courseId,date, time, capacity, duration, price, type, description);
                ClassDb.child(String.valueOf(id)).setValue(classInstance)
                        .addOnSuccessListener(aVoid -> Log.d("Firebase", "Course synced: " + id))
                        .addOnFailureListener(e -> Log.e("Firebase", "Failed to sync course: " + id, e));;;
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_COURSES + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "date TEXT,"
                + "teacher TEXT,"
                + "comments TEXT"
                + ")";

        String CREATE_TABLE = "CREATE TABLE " + TABLE_YOGA + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "course_id INTEGER ,"
                + "Date TEXT,"
                + "Time TEXT,"
                + "Capacity INTEGER,"
                + "Duration INTEGER,"
                + "Price INTEGER,"
                + "Type TEXT,"
                + "Description TEXT,"
                + "FOREIGN KEY(course_id) REFERENCES " + TABLE_COURSES + "(id) ON DELETE CASCADE)";


        try {
            // Tạo bảng nếu chưa tồn tại
            db.execSQL(CREATE_COURSES_TABLE);
            db.execSQL(CREATE_TABLE);

        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error creating table: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YOGA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        onCreate(db);
    }
    public boolean insertYogaClass(int courseId,String date, String time, int capacity, int duration, int price,String type, String description) {
        int a =-1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", date);
        contentValues.put("Time", time);
        contentValues.put("course_id", courseId);
        contentValues.put("Capacity", capacity);
        contentValues.put("Duration", duration);
        contentValues.put("Price", price);
        contentValues.put("Type", type);
        contentValues.put("Description", description);
        long result = db.insert(TABLE_YOGA, null, contentValues);
       syncClassWithFirebase();
        return result != -1;  // Trả về true nếu thêm thành công
    }
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_YOGA, "id = ?", new String[]{id});
        syncClassWithFirebase();
        return db.delete(TABLE_YOGA, "id = ?", new String[]{id});

    }
    public boolean updateData(String id, String date, String time, int capacity, int duration, int price,String type, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", date);

        contentValues.put("Time", time);
        contentValues.put("Capacity", capacity);
        contentValues.put("Duration", duration);
        contentValues.put("Price", price);
        contentValues.put("Type", type);
        contentValues.put("Description", description);
        int rowsAffected =  db.update(TABLE_YOGA, contentValues, "id = ?", new String[]{id});
        syncClassWithFirebase();
        return rowsAffected>0;
    }
    public Cursor getAllYogaClasses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_YOGA, null);
        if (cursor != null) {
            Log.d("Database", "Yoga Classes:");

            // Kiểm tra nếu cursor có dữ liệu
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("Date"));
                    @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("Time"));
                    @SuppressLint("Range") int capacity = cursor.getInt(cursor.getColumnIndex("Capacity"));
                    @SuppressLint("Range") int duration = cursor.getInt(cursor.getColumnIndex("Duration"));
                    @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex("Price"));
                    @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex("Type"));
                    @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("Description"));

                    // Ghi dữ liệu vào Logcat
                    Log.d("Database", "ID: " + id + ", Date: " + date + ", Time: " + time +
                            ", Capacity: " + capacity + ", Duration: " + duration +
                            ", Price: " + price + ", Type: " + type +
                            ", Description: " + description);

                } while (cursor.moveToNext());
            } else {
                Log.d("Database", "No yoga classes found.");
            }
        }
        return db.rawQuery("SELECT * FROM " + TABLE_YOGA, null);
    }
    public Cursor getClassById(String courseId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_YOGA + " WHERE " + "id" + " = ?";
        return db.rawQuery(query, new String[]{courseId});
    }
    public Course getCourseById(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COURSES, null, "id=?", new String[]{String.valueOf(courseId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Course course = new Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            cursor.close();
            return course;
        }
        return null;
    }
    //course
    public boolean insertCourse(String date,String teacher,String comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("teacher", teacher);
        contentValues.put("comments", comments);

        long result = db.insert(TABLE_COURSES, null, contentValues);
        syncDataCourseWithFirebase();
        return result != -1; // Return true if insertion was successful
    }
    public Cursor getYogaClassesForCourse(int courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_YOGA + " WHERE course_id = ?", new String[]{String.valueOf(courseId)});
    }
    public Cursor getAllCourses() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM " + TABLE_COURSES, null);
    }
    ////

    public ArrayList<ClassInstance> getClassesByCourseId(int courseId) {
        ArrayList<ClassInstance> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn để lấy tất cả các lớp theo courseId
        Cursor cursor = db.query(TABLE_YOGA, null, "course_id=?", new String[]{String.valueOf(courseId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Lấy tất cả các cột từ bảng TABLE_YOGA
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int courseID = cursor.getInt(cursor.getColumnIndexOrThrow("course_id"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("Date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("Time"));
                int capacity = cursor.getInt(cursor.getColumnIndexOrThrow("Capacity"));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow("Duration"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("Price"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("Type"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));

                // Tạo một đối tượng ClassInstance với tất cả các thông tin
                ClassInstance classInstance = new ClassInstance(id, courseID, date, time, capacity, duration, price, type, description);

                // Thêm vào danh sách classList
                classList.add(classInstance);
            } while (cursor.moveToNext());

            cursor.close(); // Đóng cursor sau khi sử dụng
        }

        return classList; // Trả về danh sách các lớp học
    }
    public void clearCourseIdForClass(int classId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Cập nhật giá trị course_id thành NULL
        ContentValues values = new ContentValues();
        values.putNull("course_id"); // Đặt course_id = null

        // Thực hiện cập nhật với điều kiện là id của lớp học
        db.update(TABLE_YOGA, values, "id=?", new String[]{String.valueOf(classId)});
        syncClassWithFirebase();
    }
    public ArrayList<ClassInstance> getClassesByDay(String day) {
        ArrayList<ClassInstance> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_YOGA, null, "date=?", new String[]{day}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ClassInstance classInstance = new ClassInstance(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                        cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getString(7), cursor.getString(8));
                classList.add(classInstance);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return classList;
    }
    public void updateCourseIdForClass(int classId, int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("course_id", courseId); // Cập nhật cột course_id

        // Cập nhật bản ghi với classId tương ứng
        int rowsAffected = db.update(TABLE_YOGA, values, "id=?", new String[]{String.valueOf(classId)});

        if (rowsAffected > 0) {
            // Cập nhật thành công
            syncDataCourseWithFirebase();
            syncClassWithFirebase();
            Log.d("DatabaseHelper", "Updated course_id for class id: " + classId);
        } else {
            // Không tìm thấy bản ghi để cập nhật
            Log.d("DatabaseHelper", "No class found with id: " + classId);
        }

        db.close(); // Đóng cơ sở dữ liệu
    }
    public void deleteCourse(int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Courses", "id = ?", new String[]{String.valueOf(courseId)});
        syncDataCourseWithFirebase();
        db.close();
    }
    public ArrayList<ClassInstance> getClassesByTeacher(String teacherName) {
        ArrayList<ClassInstance> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL query to select classes based on the teacher's name (case-insensitive)
        String selectQuery = "SELECT yoga.id, yoga.course_id, yoga.Date, yoga.Time, yoga.Capacity, yoga.Duration, yoga.Price, yoga.Type, yoga.Description, courses.teacher "
                + "FROM " + TABLE_YOGA + " yoga "
                + "INNER JOIN " + TABLE_COURSES + " courses ON yoga.course_id = courses.id "
                + "WHERE courses.teacher LIKE ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + teacherName + "%"});

        if (cursor.moveToFirst()) {
            do {
                // Create ClassInstance using the cursor and your constructor
                ClassInstance classInstance = new ClassInstance(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),            // id
                        cursor.getInt(cursor.getColumnIndexOrThrow("course_id")),     // courseId
                        cursor.getString(cursor.getColumnIndexOrThrow("Date")),       // date
                        cursor.getString(cursor.getColumnIndexOrThrow("Time")),       // time
                        cursor.getInt(cursor.getColumnIndexOrThrow("Capacity")),      // capacity
                        cursor.getInt(cursor.getColumnIndexOrThrow("Duration")),      // duration
                        cursor.getInt(cursor.getColumnIndexOrThrow("Price")),         // price
                        cursor.getString(cursor.getColumnIndexOrThrow("Type")),       // type
                        cursor.getString(cursor.getColumnIndexOrThrow("Description")) // description
                );

                classList.add(classInstance); // Add instance to list
            } while (cursor.moveToNext());
        }
        cursor.close();
        return classList;
    }


}
