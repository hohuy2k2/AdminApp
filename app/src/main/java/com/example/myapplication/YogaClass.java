package com.example.myapplication;

public class YogaClass {
    private int id;
    private String date;
    private int time;
    private int capacity;
    private int duration;
    private int price;
    private String description;

    // Constructor
    public YogaClass(int id, String date, int time, int capacity, int duration, int price, String description) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.capacity = capacity;
        this.duration = duration;
        this.price = price;
        this.description = description;
    }

    // Getters
    public int getId() { return id; }
    public String getDate() { return date; }
    public int getTime() { return time; }
    public int getCapacity() { return capacity; }
    public int getDuration() { return duration; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }
}
