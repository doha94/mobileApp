package com.example.assignment_todolist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class TodoItem {
    private long id;
    private String task;
    private boolean isCompleted;
    private String dateTime; // 추가

    public TodoItem(String task, boolean isCompleted) {
        this.task = task;
        this.isCompleted = isCompleted;
        this.dateTime = getCurrentDateTime(); // 새로 추가
    }
    public TodoItem() {
        // 빈 생성자 - DatabaseHelper에서 객체 생성 시 사용됨
    }
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    private String getCurrentDateTime() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
}
