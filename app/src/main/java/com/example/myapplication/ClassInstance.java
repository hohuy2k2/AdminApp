package com.example.myapplication;

public class ClassInstance {
    private int id;
    private int courseId;
    private String date;
    private String time;
    private int capacity;
    private int duration;
    private int price;
    private String type;
    private String description;

    // Constructor đầy đủ
    public ClassInstance(int id, int courseId, String date, String time, int capacity, int duration, int price, String type, String description) {
        this.id = id;
        this.courseId = courseId;
        this.date = date;
        this.time = time;
        this.capacity = capacity;
        this.duration = duration;
        this.price = price;
        this.type = type;
        this.description = description;
    }

    // Getter và Setter cho tất cả các trường
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ClassInstance{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", capacity=" + capacity +
                ", duration=" + duration +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

