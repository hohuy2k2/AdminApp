package com.example.myapplication;

public class Course {
    private int id;
    private String date;
    private String teacher;
    private String comments;

    public Course(int id, String date, String teacher, String comments) {
        this.id = id;
        this.date = date;
        this.teacher = teacher;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getComments() {
        return comments;
    }
}
